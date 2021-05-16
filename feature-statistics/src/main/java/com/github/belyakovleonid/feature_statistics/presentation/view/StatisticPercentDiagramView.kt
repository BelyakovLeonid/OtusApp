package com.github.belyakovleonid.feature_statistics.presentation.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsPercentUiModel
import kotlin.math.min

class StatisticPercentDiagramView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private var data: List<StatisticsPercentUiModel> = emptyList()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    private val drawingRectF = RectF()
    private val borderPath = Path()
    private val helperPath = Path()
    private val transformationMatrix = Matrix()

    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f

    private val dividerWidth = context.dpToPx(DEFAULT_DIVIDER_WIDTH_DP)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minDim = min(measuredHeight, measuredWidth)
        setMeasuredDimension(minDim, minDim)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val minDim = min(w, h).toFloat()
        drawingRectF.set(0F, 0F, minDim, minDim)
        centerX = drawingRectF.centerX()
        centerY = drawingRectF.centerY()
        radius = minDim / 2

        borderPath.reset()
        borderPath.addCircle(
            centerX,
            centerY,
            radius * DEFAULT_RADIUS_RATIO,
            Path.Direction.CW
        )
    }

    override fun onDraw(canvas: Canvas) {
        clipPath(canvas)
        drawDiagram(canvas)
    }

    private fun clipPath(canvas: Canvas) {
        var occupiedAngle = DEFAULT_START_DEGREE
        data.forEach { model ->
            helperPath.addRect(
                centerX,
                centerY - dividerWidth / 2,
                centerX + radius,
                centerY + dividerWidth / 2,
                Path.Direction.CW
            )

            transformationMatrix.setRotate(occupiedAngle, centerX, centerY)
            helperPath.transform(transformationMatrix)

            borderPath.addPath(helperPath)
            helperPath.reset()

            occupiedAngle += model.percent * TOTAL_DEGREE
        }

        canvas.clipOutPath(borderPath)
    }

    private fun drawDiagram(canvas: Canvas) {
        var occupiedAngle = DEFAULT_START_DEGREE
        data.forEach { model ->
            paint.color = model.color

            canvas.drawArc(
                drawingRectF,
                occupiedAngle,
                model.percent * TOTAL_DEGREE,
                true,
                paint
            )

            occupiedAngle += model.percent * TOTAL_DEGREE
        }

    }

    fun setData(data: List<StatisticsPercentUiModel>) {
        this.data = data
        invalidate()
    }

    companion object {
        private const val DEFAULT_DIVIDER_WIDTH_DP = 5
        private const val DEFAULT_RADIUS_RATIO = 0.5F
        private const val DEFAULT_START_DEGREE = -180F
        private const val TOTAL_DEGREE = 360
    }
}