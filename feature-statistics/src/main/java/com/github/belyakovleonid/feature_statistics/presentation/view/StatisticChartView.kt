package com.github.belyakovleonid.feature_statistics.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.core.presentation.measureTextHeight
import com.github.belyakovleonid.feature_statistics.R
import com.github.belyakovleonid.feature_statistics.presentation.model.WeightTrackUiModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter
import kotlin.math.pow
import kotlin.math.sqrt

class StatisticChartView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    var onItemSelectListener: ((WeightTrackUiModel) -> Unit)? = null

    // точки на графике
    private var data: List<WeightTrackUiModel> = emptyList()
    private var chartData: List<WeightChartItem> = emptyList()

    private val weightFormatter = DecimalFormat(WEIGHT_FORMATTER_PATTERN)
    private val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN)

    private val skeletonPath = Path()
    private val skeletonColor = context.getColor(R.color.skeleton_color)
    private val skeletonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = context.dpToPx(DEFAULT_SKELETON_STROKE_WIDTH_DP)
        style = Paint.Style.STROKE
        color = skeletonColor
        pathEffect = DashPathEffect(
            floatArrayOf(DEFAULT_SKELETON_STROKE_ON_DP, DEFAULT_SKELETON_STROKE_OFF_DP),
            0f
        )
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = context.dpToPx(DEFAULT_SKELETON_TEXT_SP)
    }

    private val radius = context.dpToPx(DEFAULT_RADIUS_DP)
    private val tapRadius = radius * 4
    private val pointsPath = Path()
    private val pointsPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.CYAN
        strokeWidth = radius / 3
        style = Paint.Style.STROKE
    }
    private val selectedPointsPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
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
    private var columnWidth = 0f

    private val gestureDetector = GestureDetector(context, ChartGestureDetector())

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateView()
    }

    private fun recalculateView() {
        calculateChartOffset()
        calculateScaleDivision()
        calculateChartData()
    }

    private fun calculateChartData() {
        chartData = data.mapIndexed { index, item ->
            WeightChartItem(
                rawItem = item,
                isSelected = item.isSelected,
                x = chartOffsetLeft + index * columnWidth,
                y = height - chartOffsetBottom - (item.weight * scaleValue),
                xText = getStringByDate(item.date),
                yText = getStringByWeight(item.weight)
            )
        }
    }

    private fun calculateChartOffset() {
        val doubleTextPadding = 2 * chartAxisTextPadding

        val bottomText = data.firstOrNull()?.let { getStringByDate(it.date) } ?: ""
        chartOffsetBottom = doubleTextPadding + textPaint.measureTextHeight(bottomText)

        chartOffsetRight = radius + doubleTextPadding
        chartOffsetTop = radius + doubleTextPadding

        val maxWeight = data.maxByOrNull { it.weight }?.weight ?: 0f
        chartOffsetLeft = 2 * chartAxisTextPadding + radius + textPaint.measureText(
            getStringByWeight(maxWeight)
        )
    }

    private fun calculateScaleDivision() {
        rowCount = ((height - chartOffsetBottom - chartOffsetTop) / rowHeight).toInt()
        columnWidth = (width - chartOffsetLeft - chartOffsetRight) / (data.size - 1)

        val maxWeight = data.maxByOrNull { it.weight }?.weight ?: 0f
        scaleDivision = maxWeight / rowCount
        scaleValue = rowHeight / scaleDivision
    }

    override fun onDraw(canvas: Canvas) {
        drawChartSkelet(canvas)
        drawDataPoints(canvas)
        drawSelectedDataPoints(canvas)
    }

    private fun drawSelectedDataPoints(canvas: Canvas) {
        chartData
            .filter { it.isSelected }
            .forEach {
                canvas.drawCircle(it.x, it.y, radius * 4, selectedPointsPaint)
            }
    }

    private fun drawChartSkelet(canvas: Canvas) {
        skeletonPath.reset()

        // отрисовка подписей по вертикальной оси + отрисовка горизонтальных пунктирных линий
        for (i in 0..rowCount) {
            canvas.drawText(
                getStringByWeight(i * scaleDivision),
                chartAxisTextPadding,
                height - chartOffsetBottom - i * rowHeight,
                textPaint
            )
            skeletonPath.moveTo(
                chartOffsetLeft,
                height - chartOffsetBottom - i * rowHeight
            )
            skeletonPath.lineTo(
                width.toFloat() - chartOffsetRight,
                height - chartOffsetBottom - i * rowHeight
            )
        }

        //отрисовка подписей по горизонтальной оси
        //todo сейчас рисуется от начала точки, а нужно чтобы текст был по центру
        chartData.forEach {
            canvas.drawText(
                it.xText,
                it.x - textPaint.measureText(it.xText) / 2,
                height - chartAxisTextPadding,
                textPaint
            )
        }

        canvas.drawPath(skeletonPath, skeletonPaint)
    }

    private fun drawDataPoints(canvas: Canvas) {
        if (chartData.isEmpty()) return

        pointsPath.reset()
        pointsPath.moveTo(chartData.first().x, chartData.first().y)
        chartData.forEach {
            canvas.drawCircle(it.x, it.y, radius, pointsPaint)
            pointsPath.lineTo(it.x, it.y)
        }

        canvas.drawPath(pointsPath, pointsPaint)

        chartData.forEach {
            canvas.drawText(
                it.yText,
                it.x - textPaint.measureText(it.yText) / 2,
                it.y - radius - chartAxisTextPadding,
                textPaint
            )
        }
    }

    private fun getStringByWeight(weight: Float): String {
        return weightFormatter.format(weight)
    }

    private fun getStringByDate(date: LocalDate): String {
        return dateFormatter.format(date.toJavaLocalDate())
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
        private const val DEFAULT_SKELETON_TEXT_SP = 12
        private const val DEFAULT_RADIUS_DP = 8
        private const val WEIGHT_FORMATTER_PATTERN = "#.#"
        private const val DATE_FORMATTER_PATTERN = "dd-MM"
    }
}

private data class WeightChartItem(
    val rawItem: WeightTrackUiModel,
    val isSelected: Boolean,
    val x: Float,
    val y: Float,
    val xText: String,
    val yText: String
)