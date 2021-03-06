package com.github.belyakovleonid.feature_statistics.presentation

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.belyakovleonid.core.presentation.base.BaseFragment
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_statistics.R
import com.github.belyakovleonid.feature_statistics.databinding.FStatisticsBinding
import com.github.belyakovleonid.feature_statistics.di.StatisticsApiProvider
import com.github.belyakovleonid.feature_statistics.di.StatisticsComponentHolder
import com.github.belyakovleonid.feature_statistics.presentation.adapter.StatisticsCategoryAdapter

class StatisticsFragment :
    BaseFragment<StatisticsContract.State, StatisticsContract.SideEffect>(R.layout.f_statistics) {

    private lateinit var injector: StatisticsApiProvider

    override val viewModel: StatisticsViewModel by viewModel { injector.viewModel }

    private val binding by viewBinding(FStatisticsBinding::bind)

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        StatisticsCategoryAdapter(
            onCategoryClick = { viewModel.submitEvent(StatisticsContract.Event.CategoryClicked(it)) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector = StatisticsComponentHolder.getInstance(getDependencies())
    }

    override fun setupView() = with(binding) {
        productList.adapter = adapter
        percentView.onItemClickListener = viewModel::submitEvent
        percentDiagramView.onItemClickListener = viewModel::submitEvent
    }

    override fun renderState(state: StatisticsContract.State) = with(binding) {
        percentDiagramView.setData(state.percents)
        percentView.setData(state.percents)
        adapter.submitList(state.categories.getItems())
    }

    override fun reactToSideEffect(effect: StatisticsContract.SideEffect) = with(binding) {
        when (effect) {
            is StatisticsContract.SideEffect.AnimateData -> {
                percentDiagramView.animateData()
                percentView.animateData()
            }
            is StatisticsContract.SideEffect.AnimExpandItem -> {
                percentDiagramView.animateItem(effect.itemId, true)
//                percentView.animateItem(effect.itemId)
            }
            is StatisticsContract.SideEffect.AnimCollapseItem -> {
                percentDiagramView.animateItem(effect.itemId, false)
//                percentView.animateItem(effect.itemId)
            }
        }
    }
}