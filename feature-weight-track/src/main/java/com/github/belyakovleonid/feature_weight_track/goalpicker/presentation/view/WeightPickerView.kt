package com.github.belyakovleonid.feature_weight_track.goalpicker.presentation.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.github.belyakovleonid.core.base.fractionalnumber.FractionalNumber
import com.github.belyakovleonid.core.base.fractionalnumber.NumberPart
import com.github.belyakovleonid.core.presentation.*
import com.github.belyakovleonid.feature_weight_track.R
import com.github.belyakovleonid.feature_weight_track.databinding.VWeightPickerBinding

class WeightPickerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr), View.OnClickListener {

    var onPlusListener: ((numPartSelected: NumberPart, weight: FractionalNumber) -> Unit)? = null
    var onMinusListener: ((numPartSelected: NumberPart, weight: FractionalNumber) -> Unit)? = null

    private var binding: VWeightPickerBinding = VWeightPickerBinding.inflate(
        LayoutInflater.from(context), this
    )

    private var currentWeight = FractionalNumber.ZERO

    private var numberPartSelected: NumberPart = NumberPart.INT_PART
        set(value) {
            field = value
            renderSelectPart()
        }

    private val selectedView: TextView
        get() = if (numberPartSelected == NumberPart.INT_PART) {
            binding.intPart
        } else {
            binding.fractPart
        }

    private val notSelectedView: TextView
        get() = if (numberPartSelected == NumberPart.INT_PART) {
            binding.fractPart
        } else {
            binding.intPart
        }

    private var animator = ValueAnimator.ofFloat(SELECTED_SCALE, ANIMATED_SCALE, SELECTED_SCALE)
        .apply {
            duration = ANIMATION_DURATION
            addProgressListener<Float> { animatedScale ->
                selectedView.setScale(animatedScale)
            }
        }

    private var selectedColor = Color.BLACK
        set(value) {
            selectedView.setTextColor(value)
            field = value
        }

    private var notSelectedColor = Color.BLACK
        set(value) {
            notSelectedView.setTextColor(value)
            field = value
        }

    init {
        with(binding) {
            context.obtainStyledAttributes(attributeSet, R.styleable.WeightPickerView).use { a ->
                selectedColor = a.getColor(
                    R.styleable.WeightPickerView_selectedTextColor,
                    selectedColor
                )
                notSelectedColor = a.getColor(
                    R.styleable.WeightPickerView_notSelectedTextColor,
                    notSelectedColor
                )

                val textSize = a.getDimension(
                    R.styleable.WeightPickerView_android_textSize,
                    context.dpToPx(DEFAULT_TEXT_SIZE_DP)
                )
                intPart.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                fractPart.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                fractPart.marginLayoutParams?.marginStart = (textSize * MARGIN_RATIO).toInt()

                val controlTint = a.getColor(R.styleable.WeightPickerView_controlsTint, Color.BLACK)
                minusControl.setColorFilter(controlTint)
                plusControl.setColorFilter(controlTint)

                val controlSizeIndex = a.getInt(R.styleable.WeightPickerView_controlsSize, 0)
                val controlSize = ControlsSize.values().getOrNull(controlSizeIndex)
                    ?: ControlsSize.MEDIUM
                val controlSizePx = context.dpToPx(controlSize.dpSize).toInt()
                minusControl.updateSize(controlSizePx)
                plusControl.updateSize(controlSizePx)
            }

            minusControl.setOnClickListener(this@WeightPickerView)
            plusControl.setOnClickListener(this@WeightPickerView)
            intPart.setOnClickListener(this@WeightPickerView)
            fractPart.setOnClickListener(this@WeightPickerView)
            renderSelectPart()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.minusControl -> onMinusListener?.invoke(numberPartSelected, currentWeight)
            R.id.plusControl -> onPlusListener?.invoke(numberPartSelected, currentWeight)
            R.id.intPart -> numberPartSelected = NumberPart.INT_PART
            R.id.fractPart -> numberPartSelected = NumberPart.FRACT_PART
            else -> throw NotImplementedError()
        }
    }

    override fun onDetachedFromWindow() {
        animator.cancel()
        super.onDetachedFromWindow()
    }

    fun setWeight(weight: FractionalNumber, animated: Boolean) {
        currentWeight = weight
        binding.intPart.text = currentWeight.intPart.toString()
        binding.fractPart.text = ".${currentWeight.fractPart}"

        if (animated) animateSelectedPart()
    }

    fun getCurrentWeight(): FractionalNumber = currentWeight

    private fun animateSelectedPart() {
        animator.cancel()
        animator.start()
    }

    private fun renderSelectPart() {
        selectedView.setTextColor(selectedColor)
        notSelectedView.setTextColor(notSelectedColor)
        selectedView.setScale(SELECTED_SCALE)
        notSelectedView.setScale(DEFAULT_SCALE)
    }

    companion object {
        private const val DEFAULT_TEXT_SIZE_DP = 12
        private const val DEFAULT_SCALE = 1F
        private const val SELECTED_SCALE = 1.4F
        private const val ANIMATED_SCALE = 1.6F
        private const val MARGIN_RATIO = 0.2F

        private const val ANIMATION_DURATION = 200L
    }

    private enum class ControlsSize(val dpSize: Int) {
        SMALL(30),
        MEDIUM(45),
        LARGE(65),
    }
}