package com.github.belyakovleonid.feature_statistics.presentation.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.animation.doOnEnd
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.feature_statistics.presentation.StatisticsContract
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsPercentUiModel
import kotlin.math.min

class StatisticsPercentView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    var onItemClickListener: ((StatisticsContract.Event) -> Unit)? = null

    private var data: List<StatisticsPercentUiModel> = emptyList()
    private var internalData: List<PercentItem> = emptyList()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private var cornerRadius = 0f
    private val borderRect = RectF()
    private val borderPath = Path()
    private var animatedPath = Path()

    private val dividerWidth = context.dpToPx(DEFAULT_DIVIDER_DP)

    private val gestureDetector = GestureDetector(context, PercentGestureDetector())

    private val animator by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                val progress = animatedValue as Float
                animatedPath.reset()
                animatedPath.addRoundRect(
                    RectF(0f, 0f, width * progress, height.toFloat()),
                    cornerRadius,
                    cornerRadius,
                    Path.Direction.CW
                )

                invalidate()
            }
            doOnEnd { animatedPath = borderPath }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = context.dpToPx(DEFAULT_HEIGHT_DP) + paddingTop + paddingBottom

        setMeasuredDimension(
            widthMeasureSpec,
            measureDimension(desiredHeight.toInt(), heightMeasureSpec)
        )
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        return when (specMode) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> min(desiredSize, specSize)
            else -> desiredSize
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        borderRect.bottom = h.toFloat()
        borderRect.right = w.toFloat()
        cornerRadius = h / 2f
        borderPath.reset()
        borderPath.addRoundRect(borderRect, cornerRadius, cornerRadius, Path.Direction.CW)
        animatedPath = borderPath
        recalculateView()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.clipPath(animatedPath)
        internalData.forEach {
            paint.color = it.color
            canvas.drawRect(it.coordinates, paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    fun setData(data: List<StatisticsPercentUiModel>) {
        this.data = data
        recalculateView()
        invalidate()
    }

    private fun recalculateView() {
        var occupiedSpace = 0f

        internalData = data.mapIndexed { index, model ->
            val itemWidth = width * model.percent
            occupiedSpace += itemWidth

            PercentItem(
                rawItem = model,
                color = model.color,
                coordinates = RectF(
                    occupiedSpace - itemWidth,
                    0f,
                    occupiedSpace - if (index != data.lastIndex) dividerWidth else 0f,
                    height.toFloat(),
                )
            )
        }
    }

    fun animateData() {
        animator?.cancel()
        animator?.start()
    }

    fun animateItem(itemId: Long) {

    }

    inner class PercentGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            val clickedItem = internalData.find { item ->
                item.containsClick(e)
            } ?: return false

            onItemClickListener?.invoke(StatisticsContract.Event.ItemClicked(clickedItem.rawItem))
            return super.onSingleTapConfirmed(e)
        }

        private fun PercentItem.containsClick(e: MotionEvent): Boolean {
            return coordinates.contains(e.x, e.y)
        }
    }

    companion object {
        private const val ANIMATION_DURATION = 800L
        private const val DEFAULT_HEIGHT_DP = 20
        private const val DEFAULT_DIVIDER_DP = 1
    }

    private data class PercentItem(
        val rawItem: StatisticsPercentUiModel,
        val coordinates: RectF,
        @ColorInt val color: Int
    )
}