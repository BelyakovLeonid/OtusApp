package com.github.belyakovleonid.feature_statistics.data.remote

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_statistics.data.remote.model.StatisticsCategoryDto
import com.github.belyakovleonid.feature_statistics.data.remote.model.StatisticsSubcategoryDto
import javax.inject.Inject

//todo это mock
class StatisticsApiImpl @Inject constructor() : StatisticsApi {
    override suspend fun loadStatistics(): Result<List<StatisticsCategoryDto>> {
        return Result.Success(
            listOf(
                StatisticsCategoryDto(
                    name = "Фрукты и овощи",
                    iconUrl = "https://i.ibb.co/jgVLyP9/fruit.png",
                    percent = 0.35F,
                    color = "#F29702",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Банан",
                            percent = 0.1F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Апельсин",
                            percent = 0.2F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Яблоко",
                            percent = 0.3F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Ананас",
                            percent = 0.05F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Мараккуя",
                            percent = 0.04F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Картошка",
                            percent = 0.11F
                        ),
                    )
                ),
                StatisticsCategoryDto(
                    name = "Мясные продукты",
                    iconUrl = "https://i.ibb.co/nQ6fxjf/meat.png",
                    percent = 0.18F,
                    color = "#EC5179",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Свинина",
                            percent = 0.11F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Говядина",
                            percent = 0.03F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Птица",
                            percent = 0.03F
                        )
                    )
                ),
                StatisticsCategoryDto(
                    name = "Молочные продукты и яйца",
                    iconUrl = "https://i.ibb.co/jrs7D9Y/Milk-1.png",
                    percent = 0.22F,
                    color = "#A0EBFF",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Яйца",
                            percent = 0.08F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Молоко",
                            percent = 0.03F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Кефир",
                            percent = 0.05F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Творог",
                            percent = 0.08F
                        )
                    )
                ),
                StatisticsCategoryDto(
                    name = "Хлеб и выпечка",
                    iconUrl = "https://i.ibb.co/nBpWVwM/bread-2.png",
                    percent = 0.15F,
                    color = "#936242",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Хлеб",
                            percent = 0.12F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Булочки",
                            percent = 0.03F
                        )
                    )
                ),
                StatisticsCategoryDto(
                    name = "Морепродукты",
                    iconUrl = "https://i.ibb.co/3zdNTkr/fish.png",
                    percent = 0.10F,
                    color = "#9EADC0",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Сельдь",
                            percent = 0.05F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Корюшка",
                            percent = 0.03F
                        ),
                        StatisticsSubcategoryDto(
                            name = "Кальмар",
                            percent = 0.02F
                        )
                    )
                )
            )
        )
    }
}