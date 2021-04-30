package com.github.belyakovleonid.feature_statistics.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.use
import com.github.belyakovleonid.core.presentation.dpToPx
import com.github.belyakovleonid.feature_statistics.R
import com.github.belyakovleonid.feature_statistics.presentation.model.StatisticsPercentUiModel

class StatisticsPercentView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    private var data: List<StatisticsPercentUiModel> = emptyList()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val borderPath = Path()

    private var cornerRadius: Float = context.dpToPx(DEFAULT_CORNER_RADIUS_DP)

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.StatisticsPercentView).use {
            cornerRadius = it.getDimension(R.styleable.StatisticsPercentView_cornerRadius, 0F)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        var occupiedSpace = 0f
        data.forEach { model ->
            paint.color = model.color ?: Color.GRAY

            canvas.drawRect(
                occupiedSpace,
                0f,
                occupiedSpace + (width * model.percent),
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
        private const val DEFAULT_CORNER_RADIUS_DP = 12
    }
}