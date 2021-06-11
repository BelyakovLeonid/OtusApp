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
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.core_ui.expandablelist.utils.addCircle
import com.github.belyakovleonid.core_ui.expandablelist.utils.distanceTo
import com.github.belyakovleonid.feature_statistics.presentation.StatisticsContract
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsPercentUiModel
import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.min

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

    private val drawingRectF = RectF()
    private val borderPath = Path()
    private val helperPath = Path()
    private val transformationMatrix = Matrix()

    private var center = PointF()
    private var radius = 0f
    private var internalRadius = 0f

    private val dividerWidth = context.dpToPx(DEFAULT_DIVIDER_WIDTH_DP)

    private var availableAngle = TOTAL_DEGREE + DEFAULT_START_DEGREE

    private val animator by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                val progress = animatedValue as Float
                availableAngle = TOTAL_DEGREE * progress + DEFAULT_START_DEGREE
                invalidate()
            }
            doOnCancel { availableAngle = TOTAL_DEGREE + DEFAULT_START_DEGREE }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minDim = min(measuredHeight, measuredWidth)
        setMeasuredDimension(minDim, minDim)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val minDim = min(w, h).toFloat()
        drawingRectF.set(0F, 0F, minDim, minDim)
        center.set(
            drawingRectF.centerX(),
            drawingRectF.centerY()
        )
        radius = minDim / 2
        internalRadius = minDim / 4


        recalculateView()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    override fun onDraw(canvas: Canvas) {
        clipPath(canvas)
        drawDiagram(canvas)
        paint.color = Color.BLACK
    }

    private fun clipPath(canvas: Canvas) {
        var occupiedAngle = DEFAULT_START_DEGREE
        data.forEach { model ->
            helperPath.addRect(
                center.x,
                center.y - dividerWidth / 2,
                center.x + radius,
                center.y + dividerWidth / 2,
                Path.Direction.CW
            )

            transformationMatrix.setRotate(occupiedAngle, center.x, center.y)
            helperPath.transform(transformationMatrix)

            borderPath.addPath(helperPath)
            helperPath.reset()

            occupiedAngle += model.percent * TOTAL_DEGREE
        }

        canvas.clipOutPath(borderPath)
    }

    private fun drawDiagram(canvas: Canvas) {
        internalData.forEach { model ->
            paint.color = model.color

            val isFullSector = model.startAngle + model.selfAngle <= availableAngle
            val angle = if (isFullSector) model.selfAngle else availableAngle - model.startAngle

            canvas.drawArc(
                drawingRectF,
                model.startAngle,
                angle,
                true,
                paint
            )

            if (!isFullSector) return
        }
    }

    fun setData(data: List<StatisticsPercentUiModel>) {
        this.data = data
        recalculateInternalData()
        recalculateView()
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
            occupiedAngle += selfAngle
            DiagramItem(
                rawItem = it,
                selfAngle = selfAngle,
                startAngle = occupiedAngle - selfAngle,
                endAngle = occupiedAngle,
                color = it.color
            )
        }
    }

    private fun recalculateView() {
        borderPath.reset()
        internalData.forEach { model ->
            helperPath.addRect(
                center.x,
                center.y - dividerWidth / 2,
                center.x + radius,
                center.y + dividerWidth / 2,
                Path.Direction.CW
            )

            transformationMatrix.setRotate(model.startAngle, center.x, center.y)
            helperPath.transform(transformationMatrix)

            borderPath.addPath(helperPath)
            helperPath.reset()
        }

        borderPath.addCircle(
            center,
            internalRadius,
            Path.Direction.CW
        )
    }

    var itemAnimator: ValueAnimator? = null

    fun animateItem(itemId: Long) {
        itemAnimator?.cancel()
        itemAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                val progress = (it.animatedValue as Float)
                // увеличить и уменьшить размеры конкретного элемента
//                internalData
            }
            start()
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
        private const val ANIMATION_DURATION = 800L
        private const val DEFAULT_DIVIDER_WIDTH_DP = 5
        private const val DEFAULT_START_DEGREE = -180F
        private const val TOTAL_DEGREE = 360F
    }

    private data class DiagramItem(
        val rawItem: StatisticsPercentUiModel,
        val selfAngle: Float,
        val startAngle: Float,
        val endAngle: Float,
        @ColorInt val color: Int
    )
}