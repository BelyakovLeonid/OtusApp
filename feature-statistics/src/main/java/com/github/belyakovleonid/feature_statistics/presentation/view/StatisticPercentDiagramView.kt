package com.github.belyakovleonid.feature_statistics.presentation.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.animation.doOnCancel
import com.github.belyakovleonid.core.presentation.addProgressListener
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.core.presentation.gradsToRadians
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
    private val animatedExpandedDrawingRectF = RectF()
    private val animatedCollapsingDrawingRectF = RectF()

    private var center = PointF()
    private var radius = 0f
    private var internalRadius = 0f
    private var offset = 0f

    private var animatedClipAngle = 0f
    private var animatedExpandedItemId: Long? = null
    private var animatedCollapsingItemId: Long? = null

    private var itemExpandAnimator: ValueAnimator? = null
    private var itemCollapseAnimator: ValueAnimator? = null
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
    }

    private val animatorsMap = HashMap<Long, RectF>()
    private val animatorsExpandedMap = HashMap<Long, ValueAnimator>()
    private val animatorsCollapsingMap = HashMap<Long, ValueAnimator>()

    fun animateItem(itemId: Long, expand: Boolean) {
        val currentExpanded = animatorsExpandedMap[itemId]
        val currentCollapsing = animatorsCollapsingMap[itemId]

        if (expand) {
            val time = if (currentCollapsing?.isRunning == true) {
                currentCollapsing.currentPlayTime
            } else {
                EXPAND_COLLAPSE_ANIMATION_DURATION
            }
            currentCollapsing?.cancel()
            if (currentExpanded == null) {
                animatorsMap[itemId] = RectF()
                animatorsExpandedMap[itemId] = ValueAnimator.ofFloat(0f, 1f).apply {
                    duration = EXPAND_COLLAPSE_ANIMATION_DURATION
                    addProgressListener<Float> { progress ->
                        val animatedOffset = offset * (1 - progress)
                        animatorsMap[itemId]?.set(
                            animatedOffset,
                            animatedOffset,
                            width - animatedOffset,
                            height - animatedOffset
                        )
                        invalidate()
                    }
                    currentPlayTime = EXPAND_COLLAPSE_ANIMATION_DURATION - time
                    start()
                }
            } else {
                if (!currentExpanded.isRunning) {
                    currentExpanded.cancel()
                    animatorsExpandedMap[itemId]?.currentPlayTime =
                        EXPAND_COLLAPSE_ANIMATION_DURATION - time
                    animatorsExpandedMap[itemId]?.start()
                }
            }
        } else {
            val time = if (currentExpanded?.isRunning == true) {
                currentExpanded.currentPlayTime
            } else {
                EXPAND_COLLAPSE_ANIMATION_DURATION
            }
            currentExpanded?.cancel()

            Log.d("MyTag", "coll time = $time")
            if (currentCollapsing == null) {
                animatorsMap[itemId] = RectF()
                animatorsCollapsingMap[itemId] = ValueAnimator.ofFloat(0f, 1f).apply {
                    duration = EXPAND_COLLAPSE_ANIMATION_DURATION
                    addProgressListener<Float> { progress ->
                        val animatedOffset = offset * progress

                        Log.d("MyTag", "coll progress = $progress")
                        animatorsMap[itemId]?.set(
                            animatedOffset,
                            animatedOffset,
                            width - animatedOffset,
                            height - animatedOffset
                        )
                        invalidate()
                    }
                    currentPlayTime = EXPAND_COLLAPSE_ANIMATION_DURATION - time
                    start()
                }
            } else {
                if (!currentCollapsing.isRunning) {
                    currentCollapsing.cancel()
                    animatorsCollapsingMap[itemId]?.currentPlayTime =
                        EXPAND_COLLAPSE_ANIMATION_DURATION - time
                    animatorsCollapsingMap[itemId]?.start()
                }
            }
        }
    }

    inner class DiagramGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            val clickedItem = internalData.find { item ->
                item.containsClick(e)
            } ?: return false

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
                    (clickAngle / PI * 180) in startAngle..endAngle
        }
    }

    companion object {
        private const val INITIAL_ANIMATION_DURATION = 800L
        private const val EXPAND_COLLAPSE_ANIMATION_DURATION = 400L
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