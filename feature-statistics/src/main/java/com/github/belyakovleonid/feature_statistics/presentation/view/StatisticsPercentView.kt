package com.github.belyakovleonid.feature_statistics.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsPercentUiModel
import kotlin.math.min

class StatisticsPercentView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private var data: List<StatisticsPercentUiModel> = emptyList()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val borderRect = RectF()
    private val borderPath = Path()
    private val dividerWidth = context.dpToPx(DEFAULT_DIVIDER_DP)

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
        borderPath.reset()
        borderPath.addRoundRect(borderRect, h / 2f, h / 2f, Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.clipPath(borderPath)
        var occupiedSpace = 0f
        data.forEach { model ->
            paint.color = model.color

            canvas.drawRect(
                occupiedSpace,
                0f,
                occupiedSpace + (width * model.percent) - dividerWidth,
                height.toFloat(),
                paint
            )

            occupiedSpace += width * model.percent
        }
    }

    fun setData(data: List<StatisticsPercentUiModel>) {
        this.data = data
        invalidate()
    }

    companion object {
        private const val DEFAULT_HEIGHT_DP = 20
        private const val DEFAULT_DIVIDER_DP = 1
    }
}