package com.github.belyakovleonid.feature_weight_track.presentation.view.weightchart

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnCancel
import androidx.core.content.res.use
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.core.presentation.getWeightString
import com.github.belyakovleonid.core.presentation.measureTextHeight
import com.github.belyakovleonid.core_ui.expandablelist.PositionedText
import com.github.belyakovleonid.core_ui.expandablelist.utils.*
import com.github.belyakovleonid.feature_weight_track.R
import com.github.belyakovleonid.feature_weight_track.presentation.model.WeightTrackUiModel

class WeightChartView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    var onItemSelectListener: ((WeightTrackUiModel) -> Unit)? = null

    // точки на графике
    private var dataSet: WeightDataSet = WeightDataSet.EMPTY_SET
    private var chartData: List<WeightChartItem> = emptyList()
    private var chartSections: List<ChartSection> = emptyList()

    //оси графика
    private var horizontalAxisData: List<AxisChartItem> = emptyList()
    private var verticalAxisData: List<AxisChartItem> = emptyList()

    private var totalLength = 0f
    private var animatedLength = 0f

    private val clipOutPath = Path()
    private val pointsPath = Path()
    private val axisPath = Path()

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
    private val offsets = RectF()

    private var rowHeight = 0f
    private var scaleDivision = 0f
    private var scaleValue = 0f
    private var rowCount = 0
    private var verticalAxisCount = 0
    private var columnWidth = 0f

    private var weightAxisDelta = 0f
    private var weightAxisOffset = 0f

    private val gestureDetector = GestureDetector(context, ChartGestureDetector())

    private val animator by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ANIMATION_DURATION
            interpolator = LinearInterpolator()

            addUpdateListener {
                val progress = animatedValue as Float
                animatedLength = totalLength * progress
                invalidate()
            }
            doOnCancel { animatedLength = totalLength }
        }
    }

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

    fun setData(data: List<WeightTrackUiModel>, animate: Boolean) {
        dataSet = WeightDataSet(
            rawData = data,
            maxWeight = data.maxByOrNull { it.weight }?.weight ?: 0f,
            minWeight = data.minByOrNull { it.weight }?.weight ?: 0f
        )
        recalculateView()

        if (animate) {
            animator?.cancel()
            animator.start()
        } else {
            invalidate()
        }
    }

    private fun drawChartSkelet(canvas: Canvas) {
        axisPath.reset()

        // отрисовка подписей по вертикальной оси + отрисовка горизонтальных пунктирных линий
        verticalAxisData.forEach {
            canvas.drawPositionedText(it.text, textPaint)
            axisPath.moveTo(it.start)
            axisPath.lineTo(it.end)
        }
        canvas.drawPath(axisPath, skeletonPaint)

        //отрисовка подписей по горизонтальной оси
        horizontalAxisData.forEach {
            canvas.drawPositionedText(it.text, textPaint)
        }
    }

    private fun drawChartItems(canvas: Canvas) {
        if (chartData.isEmpty()) return

        pointsPath.reset()
        pointsPath.moveTo(chartData.first().coordinates)

        var occupiedLength = 0f
        chartData.forEachIndexed { i, item ->
            val section = chartSections[i]

            if (occupiedLength + section.length > animatedLength) {
                drawPartChartSection(canvas, section, occupiedLength)
                return
            } else {
                drawFullChartSection(canvas, item)
                occupiedLength += section.length
            }
        }

        canvas.drawPath(pointsPath, chartPaint)
    }

    private fun drawPartChartSection(canvas: Canvas, section: ChartSection, occupiedLength: Float) {
        // сюда попадаем только в случае, если вью анимируется
        val partProgress = (animatedLength - occupiedLength) / section.length

        pointsPath.lineTo(
            section.start.x + partProgress * (section.end.x - section.start.x),
            section.start.y + partProgress * (section.end.y - section.start.y)
        )
        canvas.drawPath(pointsPath, chartPaint)
    }

    private fun drawFullChartSection(canvas: Canvas, item: WeightChartItem) {
        clipOutPath.reset()
        clipOutPath.addCircle(item.coordinates, radius * 2 / 3, Path.Direction.CW)
        canvas.clipOutPath(clipOutPath)
        canvas.drawCircle(item.coordinates, radius, pointsPaint)
        pointsPath.lineTo(item.coordinates)
    }

    private fun drawSelectedItems(canvas: Canvas) {
        canvas.restore()
        chartData.forEach {
            if (it.isSelected) {
                canvas.drawCircle(it.coordinates, radius * 2, selectedPaint)
                canvas.drawCircle(it.coordinates, radius * 2, chartPaint)
            }
        }
    }

    private fun drawItemsLabels(canvas: Canvas) {
        chartData.forEach {
            canvas.drawPositionedText(it.label, labelTextPaint)
        }
    }

    private fun recalculateView() {
        calculateChartOffset()
        calculateScale()
        calculateChartData()
        calculateAxisData()
    }

    private fun calculateChartOffset() {
        val doubleTextPadding = 2 * chartAxisTextPadding
        val maxWeightText = getWeightString(dataSet.maxWeight)

        offsets.set(
            2 * chartAxisTextPadding + radius + textPaint.measureText(maxWeightText),
            radius + doubleTextPadding + textPaint.measureTextHeight(maxWeightText),
            radius + doubleTextPadding,
            doubleTextPadding + radius + textPaint.textSize,
        )
    }

    private fun calculateScale() {
        val weightDelta = dataSet.maxWeight - dataSet.minWeight
        val availableHeight = height - offsets.bottom - offsets.top
        scaleValue = availableHeight / weightDelta

        scaleDivision = getScaleDivisionByWeightDelta(weightDelta)

        val weightReminder = dataSet.minWeight.rem(scaleDivision)
        weightAxisDelta = if (weightReminder == 0f) 0f else scaleDivision - weightReminder
        weightAxisOffset = weightAxisDelta * scaleValue

        rowCount = ((weightDelta - weightAxisDelta) / scaleDivision).toInt()
        rowHeight = scaleDivision * scaleValue
        verticalAxisCount = rowCount + 1

        columnWidth = (width - offsets.left - offsets.right) / (dataSet.lastIndex)
    }

    private fun getScaleDivisionByWeightDelta(weightDelta: Float) = when {
        weightDelta < 10 -> SCALE_DIVISION_SMALL
        weightDelta < 30 -> SCALE_DIVISION_MEDIUM
        weightDelta < 50 -> SCALE_DIVISION_LARGE
        else -> SCALE_DIVISION_XLARGE
    }


    private fun calculateChartData() {
        chartData = dataSet.rawData.mapIndexed { index, item ->
            val x = offsets.left + index * columnWidth
            val y = height - offsets.bottom - (item.weight - dataSet.minWeight) * scaleValue

            WeightChartItem(
                rawItem = item,
                isSelected = item.isSelected,
                coordinates = PointF(x, y),
                label = PositionedText(
                    text = item.weightText,
                    coordinates = PointF(
                        x - textPaint.measureText(item.weightText) / 2,
                        y - radius - chartAxisTextPadding
                    )
                ),
            )
        }

        totalLength = 0f
        chartSections = chartData.mapIndexed { index, item ->
            val previous = chartData.getOrNull(index - 1) ?: item
            val sectionLength = item.coordinates.distanceTo(previous.coordinates)
            totalLength += sectionLength
            ChartSection(
                start = previous.coordinates,
                end = item.coordinates,
                length = sectionLength
            )
        }
    }

    private fun calculateAxisData() {
        verticalAxisData = List(rowCount + 1) { i ->
            val text = getWeightString(i * scaleDivision + weightAxisDelta + dataSet.minWeight)
            val textHeight = textPaint.measureTextHeight(text)
            val occupiedHeight = height - offsets.bottom - weightAxisOffset - i * rowHeight
            AxisChartItem(
                start = PointF(offsets.left, occupiedHeight),
                end = PointF(width.toFloat() - offsets.right, occupiedHeight),
                text = PositionedText(
                    text = text,
                    coordinates = PointF(chartAxisTextPadding, occupiedHeight + textHeight / 2)
                )
            )
        }

        horizontalAxisData = chartData.map {
            val text = it.rawItem.dateText
            AxisChartItem(
                text = PositionedText(
                    text = text,
                    coordinates = PointF(
                        it.label.coordinates.x - textPaint.measureText(text) / 2,
                        height - chartAxisTextPadding
                    )
                ),
            )
        }
    }

    inner class ChartGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            val nearestItem = chartData.minByOrNull {
                getDistanceToTap(it, e)
            } ?: return false

            if (getDistanceToTap(nearestItem, e) <= tapRadius) {
                onItemSelectListener?.invoke(nearestItem.rawItem)
            }
            return super.onSingleTapConfirmed(e)
        }

        private fun getDistanceToTap(item: WeightChartItem, e: MotionEvent): Float {
            return item.coordinates.distanceTo(e.x, e.y)
        }
    }

    companion object {
        private const val ANIMATION_DURATION = 2000L

        private const val DEFAULT_AXIS_PADDING_DP = 8
        private const val DEFAULT_SKELETON_STROKE_WIDTH_DP = 1
        private const val DEFAULT_SKELETON_STROKE_ON_DP = 3F
        private const val DEFAULT_SKELETON_STROKE_OFF_DP = 4F
        private const val DEFAULT_TEXT_SP = 12
        private const val DEFAULT_RADIUS_DP = 6

        private const val SCALE_DIVISION_SMALL = 1f
        private const val SCALE_DIVISION_MEDIUM = 5f
        private const val SCALE_DIVISION_LARGE = 10f
        private const val SCALE_DIVISION_XLARGE = 2f
    }
}