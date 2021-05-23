package com.github.belyakovleonid.feature_statistics.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.use
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.core.presentation.getWeightString
import com.github.belyakovleonid.core.presentation.measureTextHeight
import com.github.belyakovleonid.feature_statistics.R
import com.github.belyakovleonid.feature_statistics.presentation.model.WeightTrackUiModel
import kotlin.math.pow
import kotlin.math.sqrt

class WeightChartView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    var onItemSelectListener: ((WeightTrackUiModel) -> Unit)? = null

    // точки на графике
    private var data: List<WeightTrackUiModel> = emptyList()
    private var chartData: List<WeightChartItem> = emptyList()

    //оси графика
    private var horizontalAxisData: List<AxisChartItem> = emptyList()
    private var verticalAxisData: List<AxisChartItem> = emptyList()

    private val clipOutPath = Path()
    private val pointsPath = Path()
    private val skeletonPath = Path()

    private val radius = context.dpToPx(DEFAULT_RADIUS_DP)
    private val tapRadius = radius * 4

    private val skeletonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = context.dpToPx(DEFAULT_SKELETON_STROKE_WIDTH_DP)
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(
            floatArrayOf(DEFAULT_SKELETON_STROKE_ON_DP, DEFAULT_SKELETON_STROKE_OFF_DP),
            0f
        )
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = context.dpToPx(DEFAULT_TEXT_SP)
    }

    private val labelTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textSize = context.dpToPx(DEFAULT_TEXT_SP)
    }

    private val pointsPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    private val chartPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = radius / 3
        style = Paint.Style.STROKE
    }

    private val selectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = radius / 3
        style = Paint.Style.FILL_AND_STROKE
    }

    private var chartAxisTextPadding = context.dpToPx(DEFAULT_AXIS_PADDING_DP)
    private var chartOffsetTop = 0f
    private var chartOffsetBottom = 0f
    private var chartOffsetLeft = 0f
    private var chartOffsetRight = 0f

    private val rowHeight = context.dpToPx(DEFAULT_SKELETON_CELL_HEIGHT_DP)
    private var scaleDivision = 0f
    private var scaleValue = 0f
    private var rowCount = 0
    private var verticalAxisCount = 0
    private var columnWidth = 0f

    private val gestureDetector = GestureDetector(context, ChartGestureDetector())

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.WeightChartView).use { a ->
            val chartColor = a.getColor(R.styleable.WeightChartView_wcv_chart_color, Color.BLACK)
            textPaint.color = a.getColor(
                R.styleable.WeightChartView_wcv_text_color,
                Color.BLACK
            )
            labelTextPaint.color = a.getColor(
                R.styleable.WeightChartView_wcv_text_color,
                Color.BLACK
            )
            selectedPaint.color = a.getColor(
                R.styleable.WeightChartView_wcv_selected_color,
                Color.BLACK
            )
            skeletonPaint.color = a.getColor(
                R.styleable.WeightChartView_wcv_skeleton_color,
                context.getColor(R.color.skeleton_color)
            )
            pointsPaint.color = chartColor
            chartPaint.color = chartColor
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateView()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        drawChartSkelet(canvas)
        drawChartItems(canvas)
        drawSelectedItems(canvas)
        drawItemsLabels(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    fun setData(data: List<WeightTrackUiModel>) {
        this.data = data
        recalculateView()
        invalidate()
    }


    private fun drawChartSkelet(canvas: Canvas) {
        skeletonPath.reset()

        // отрисовка подписей по вертикальной оси + отрисовка горизонтальных пунктирных линий
        verticalAxisData.forEach {
            canvas.drawText(it.text, it.textX, it.textY, textPaint)
            skeletonPath.moveTo(it.startX, it.startY)
            skeletonPath.lineTo(it.endX, it.endY)
        }
        canvas.drawPath(skeletonPath, skeletonPaint)

        //отрисовка подписей по горизонтальной оси
        horizontalAxisData.forEach {
            canvas.drawText(it.text, it.textX, it.textY, textPaint)
        }
    }

    private fun drawChartItems(canvas: Canvas) {
        if (chartData.isEmpty()) return

        pointsPath.reset()
        pointsPath.moveTo(chartData.first().x, chartData.first().y)
        chartData.forEach {
            clipOutPath.reset()
            clipOutPath.addCircle(it.x, it.y, radius * 2 / 3, Path.Direction.CW)
            canvas.clipOutPath(clipOutPath)
            canvas.drawCircle(it.x, it.y, radius, pointsPaint)
            pointsPath.lineTo(it.x, it.y)
        }

        canvas.drawPath(pointsPath, chartPaint)
    }

    private fun drawSelectedItems(canvas: Canvas) {
        canvas.restore()
        chartData
            .filter { it.isSelected }
            .forEach {
                canvas.drawCircle(it.x, it.y, radius * 2, selectedPaint)
                canvas.drawCircle(it.x, it.y, radius * 2, chartPaint)
            }
    }

    private fun drawItemsLabels(canvas: Canvas) {
        chartData.forEach {
            canvas.drawText(it.labelText, it.labelX, it.labelY, labelTextPaint)
        }
    }

    private fun recalculateView() {
        calculateChartOffset()
        calculateScaleDivision()
        calculateChartData()
        calculateAxisData()
    }

    private fun calculateChartOffset() {
        val doubleTextPadding = 2 * chartAxisTextPadding

        val bottomText = data.firstOrNull()?.dateText ?: ""
        chartOffsetBottom = doubleTextPadding + radius + textPaint.measureTextHeight(bottomText)

        chartOffsetRight = radius + doubleTextPadding
        chartOffsetTop = radius + doubleTextPadding

        val maxWeightItem = data.maxByOrNull { it.weight }
        chartOffsetLeft = 2 * chartAxisTextPadding + radius + textPaint.measureText(
            maxWeightItem?.weightText ?: ""
        )
    }

    private fun calculateScaleDivision() {
        rowCount = ((height - chartOffsetBottom - chartOffsetTop) / rowHeight).toInt()
        verticalAxisCount = rowCount + 1

        columnWidth = (width - chartOffsetLeft - chartOffsetRight) / (data.size - 1)

        val maxWeight = data.maxByOrNull { it.weight }?.weight ?: 0f
        val minWeight = data.minByOrNull { it.weight }?.weight ?: 0f
        scaleDivision = (maxWeight - minWeight) / rowCount
        scaleValue = rowHeight / scaleDivision
    }

    private fun calculateChartData() {
        val minWeight = data.minByOrNull { it.weight }?.weight ?: 0f
        chartData = data.mapIndexed { index, item ->
            val x = chartOffsetLeft + index * columnWidth
            val y = height - chartOffsetBottom - (item.weight - minWeight) * scaleValue

            WeightChartItem(
                rawItem = item,
                isSelected = item.isSelected,
                x = x,
                y = y,
                labelText = item.weightText,
                labelX = x - textPaint.measureText(item.weightText) / 2,
                labelY = y - radius - chartAxisTextPadding
            )
        }
    }

    private fun calculateAxisData() {
        val minWeight = data.minByOrNull { it.weight }?.weight ?: 0f
        verticalAxisData = List(rowCount + 1) { i ->
            val text = getWeightString(i * scaleDivision + minWeight)
            val textHeight = textPaint.measureTextHeight(text)
            AxisChartItem(
                text = text,
                textX = chartAxisTextPadding,
                textY = height - chartOffsetBottom - i * rowHeight + textHeight / 2,
                startX = chartOffsetLeft,
                startY = height - chartOffsetBottom - i * rowHeight,
                endX = width.toFloat() - chartOffsetRight,
                endY = height - chartOffsetBottom - i * rowHeight
            )
        }

        horizontalAxisData = chartData.map {
            val text = it.rawItem.dateText
            AxisChartItem(
                text = text,
                textX = it.labelX - textPaint.measureText(text) / 2,
                textY = height - chartAxisTextPadding,
            )
        }
    }

    inner class ChartGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            val nearestItem = chartData.minByOrNull {
                getDistanceToTap(it.x, it.y, e)
            } ?: return false

            if (getDistanceToTap(nearestItem.x, nearestItem.y, e) <= tapRadius) {
                onItemSelectListener?.invoke(nearestItem.rawItem)
            }
            return super.onSingleTapConfirmed(e)
        }

        private fun getDistanceToTap(x: Float, y: Float, e: MotionEvent): Float {
            return sqrt((x - e.x).pow(2) + (y - e.y).pow(2))
        }
    }

    companion object {
        private const val DEFAULT_AXIS_PADDING_DP = 8
        private const val DEFAULT_SKELETON_STROKE_WIDTH_DP = 1
        private const val DEFAULT_SKELETON_STROKE_ON_DP = 3F
        private const val DEFAULT_SKELETON_STROKE_OFF_DP = 4F
        private const val DEFAULT_SKELETON_CELL_HEIGHT_DP = 70
        private const val DEFAULT_TEXT_SP = 12
        private const val DEFAULT_RADIUS_DP = 6
    }
}

private data class WeightChartItem(
    val rawItem: WeightTrackUiModel,
    val isSelected: Boolean,
    val x: Float,
    val y: Float,
    val labelText: String,
    val labelX: Float,
    val labelY: Float,
)

private data class AxisChartItem(
    val text: String,
    val textX: Float,
    val textY: Float,
    val startX: Float = 0f,
    val startY: Float = 0f,
    val endX: Float = 0f,
    val endY: Float = 0f
)