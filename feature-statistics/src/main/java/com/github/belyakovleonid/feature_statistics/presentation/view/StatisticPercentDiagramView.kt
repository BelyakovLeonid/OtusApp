package com.github.belyakovleonid.feature_statistics.presentation.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import com.github.belyakovleonid.core.presentation.addProgressListener
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.core.presentation.gradsToRadians
import com.github.belyakovleonid.core.presentation.radiansToGrads
import com.github.belyakovleonid.core_ui.expandablelist.utils.distanceTo
import com.github.belyakovleonid.core_ui.expandablelist.utils.drawCircle
import com.github.belyakovleonid.feature_statistics.presentation.StatisticsContract
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsPercentUiModel
import kotlin.math.*

class StatisticPercentDiagramView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    var onItemClickListener: ((StatisticsContract.Event) -> Unit)? = null

    private val gestureDetector = GestureDetector(context, DiagramGestureDetector())

    private var data: List<StatisticsPercentUiModel> = emptyList()
    private var internalData: List<DiagramItem> = emptyList()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    private val eraserPaint: Paint = Paint().apply {
        strokeWidth = context.dpToPx(DEFAULT_DIVIDER_WIDTH_DP)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    private val drawingRectF = RectF()

    private var center = PointF()
    private var radius = 0f
    private var internalRadius = 0f
    private var offset = 0f

    private var animatorsMap = emptyMap<Long, RectF>()
    private val animatorsExpandedMap = HashMap<Long, ValueAnimator>()
    private val animatorsCollapsingMap = HashMap<Long, ValueAnimator>()

    private var animatedClipAngle = 0f
    private val animator by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = INITIAL_ANIMATION_DURATION
            addProgressListener<Float> { progress ->
                animatedClipAngle = TOTAL_DEGREE * (progress - 1)
                invalidate()
            }
            doOnCancel { animatedClipAngle = 0f }
        }
    }

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minDim = min(measuredHeight, measuredWidth)
        setMeasuredDimension(minDim, minDim)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val minDim = min(w, h).toFloat()
        offset = minDim * 0.05f
        drawingRectF.set(offset, offset, minDim - offset, minDim - offset)
        center.set(drawingRectF.centerX(), drawingRectF.centerY())
        radius = minDim / 2
        internalRadius = minDim / 4

        recalculateInternalData()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorsExpandedMap.forEach { (_, animator) -> animator.cancel() }
        animatorsCollapsingMap.forEach { (_, animator) -> animator.cancel() }
    }

    override fun onDraw(canvas: Canvas) {
        drawDiagram(canvas)
        clipPath(canvas)
        clipAnimatedProgress(canvas)
    }

    private fun drawDiagram(canvas: Canvas) {
        // рисуем сектора диаграмы
        internalData.forEach { model ->
            paint.color = model.color

            canvas.drawArc(
                animatorsMap[model.rawItem.id] ?: drawingRectF,
                model.startAngle,
                model.selfAngle,
                true,
                paint
            )
        }
    }

    private fun clipPath(canvas: Canvas) {
        // рисуем разделители между секторами
        internalData.forEach { model ->
            canvas.drawLine(
                center.x,
                center.y,
                model.separatorEndX,
                model.separatorEndY,
                eraserPaint
            )
        }

        // вырезаем центр диаграммы
        canvas.drawCircle(center, internalRadius, eraserPaint)
    }

    private fun clipAnimatedProgress(canvas: Canvas) {
        // затираем часть диаграмы в соответствии с прогрессом анимации
        canvas.drawArc(
            drawingRectF,
            DEFAULT_START_DEGREE,
            animatedClipAngle,
            true,
            eraserPaint
        )
    }

    fun setData(data: List<StatisticsPercentUiModel>) {
        this.data = data
        recalculateInternalData()
        invalidate()
    }

    fun animateData() {
        animator?.cancel()
        animator?.start()
    }

    private fun recalculateInternalData() {
        var occupiedAngle = DEFAULT_START_DEGREE
        internalData = data.map {
            val selfAngle = it.percent * TOTAL_DEGREE
            val startAngle = occupiedAngle
            occupiedAngle += selfAngle
            DiagramItem(
                rawItem = it,
                selfAngle = selfAngle,
                startAngle = startAngle,
                endAngle = occupiedAngle,
                separatorEndX = center.x + radius * cos(gradsToRadians(startAngle)),
                separatorEndY = center.y + radius * sin(gradsToRadians(startAngle)),
                color = it.color
            )
        }
        animatorsMap = data.map { it.id to RectF(drawingRectF) }.toMap()
    }

    fun animateItem(itemId: Long, expand: Boolean) {
        val itemExpandedAnim = animatorsExpandedMap[itemId]
        val itemCollapsingAnim = animatorsCollapsingMap[itemId]
        val oppositeAnim = if (expand) itemCollapsingAnim else itemExpandedAnim
        val startTime = getInverseTimeAnim(oppositeAnim)
        itemExpandedAnim?.cancel()
        itemCollapsingAnim?.cancel()

        when {
            expand && itemExpandedAnim == null -> {
                createAndStartAnimator(animatorsExpandedMap, itemId, startTime, true)
            }
            !expand && itemCollapsingAnim == null -> {
                createAndStartAnimator(animatorsCollapsingMap, itemId, startTime, false)
            }
            expand -> itemExpandedAnim?.currentPlayTime = startTime
            else -> itemCollapsingAnim?.currentPlayTime = startTime
        }
    }

    private fun createAndStartAnimator(
        animMap: MutableMap<Long, ValueAnimator>,
        itemId: Long,
        startTime: Long,
        expand: Boolean
    ) {
        animMap[itemId] = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ITEM_ANIMATION_DURATION
            doOnEnd { animMap.remove(itemId) }
            addProgressListener<Float> { progress ->
                val animatedOffset = if (expand) offset * (1 - progress) else offset * progress
                animatorsMap[itemId]?.set(
                    animatedOffset,
                    animatedOffset,
                    width - animatedOffset,
                    height - animatedOffset
                )
                invalidate()
            }
            currentPlayTime = startTime
            start()
        }
    }

    private fun getInverseTimeAnim(animator: ValueAnimator?): Long {
        val passedTime = animator?.currentPlayTime ?: ITEM_ANIMATION_DURATION
        return ITEM_ANIMATION_DURATION - passedTime
    }

    inner class DiagramGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            val clickedItem = internalData.find { it.containsClick(e) } ?: return false
            onItemClickListener?.invoke(StatisticsContract.Event.ItemClicked(clickedItem.rawItem))
            return super.onSingleTapConfirmed(e)
        }

        private fun DiagramItem.containsClick(e: MotionEvent): Boolean {
            val clickR = center.distanceTo(e.x, e.y)
            var clickAngle: Float = asin((e.y - center.y) / clickR)

            clickAngle = when {
                e.x > center.x && e.y > center.y -> clickAngle
                e.x > center.x && e.y < center.y -> clickAngle
                e.x < center.x && e.y > center.y -> PI.toFloat() - clickAngle
                else -> -PI.toFloat() - clickAngle
            }

            return clickR in internalRadius..radius &&
                    radiansToGrads(clickAngle) in startAngle..endAngle
        }
    }

    companion object {
        private const val INITIAL_ANIMATION_DURATION = 800L
        private const val ITEM_ANIMATION_DURATION = 400L
        private const val DEFAULT_DIVIDER_WIDTH_DP = 5
        private const val DEFAULT_START_DEGREE = -180F
        private const val TOTAL_DEGREE = 360F
    }

    private data class DiagramItem(
        val rawItem: StatisticsPercentUiModel,
        val selfAngle: Float,
        val startAngle: Float,
        val endAngle: Float,
        val separatorEndX: Float,
        val separatorEndY: Float,
        @ColorInt val color: Int
    )
}