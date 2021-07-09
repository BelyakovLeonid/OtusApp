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
                    id = 1L,
                    name = "Фрукты и овощи",
                    iconUrl = "https://i.ibb.co/jgVLyP9/fruit.png",
                    percent = 0.35F,
                    color = "#F29702",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            id = 11L,
                            name = "Банан",
                            percent = 0.1F
                        ),
                        StatisticsSubcategoryDto(
                            id = 12L,
                            name = "Апельсин",
                            percent = 0.2F
                        ),
                        StatisticsSubcategoryDto(
                            id = 13L,
                            name = "Яблоко",
                            percent = 0.3F
                        ),
                        StatisticsSubcategoryDto(
                            id = 14L,
                            name = "Ананас",
                            percent = 0.05F
                        ),
                        StatisticsSubcategoryDto(
                            id = 15L,
                            name = "Мараккуя",
                            percent = 0.04F
                        ),
                        StatisticsSubcategoryDto(
                            id = 16L,
                            name = "Картошка",
                            percent = 0.11F
                        ),
                    )
                ),
                StatisticsCategoryDto(
                    id = 2L,
                    name = "Мясные продукты",
                    iconUrl = "https://i.ibb.co/nQ6fxjf/meat.png",
                    percent = 0.18F,
                    color = "#EC5179",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            id = 21L,
                            name = "Свинина",
                            percent = 0.11F
                        ),
                        StatisticsSubcategoryDto(
                            id = 22L,
                            name = "Говядина",
                            percent = 0.03F
                        ),
                        StatisticsSubcategoryDto(
                            id = 23L,
                            name = "Птица",
                            percent = 0.03F
                        )
                    )
                ),
                StatisticsCategoryDto(
                    id = 3L,
                    name = "Молочные продукты и яйца",
                    iconUrl = "https://i.ibb.co/jrs7D9Y/Milk-1.png",
                    percent = 0.22F,
                    color = "#A0EBFF",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            id = 31L,
                            name = "Яйца",
                            percent = 0.08F
                        ),
                        StatisticsSubcategoryDto(
                            id = 32L,
                            name = "Молоко",
                            percent = 0.03F
                        ),
                        StatisticsSubcategoryDto(
                            id = 33L,
                            name = "Кефир",
                            percent = 0.05F
                        ),
                        StatisticsSubcategoryDto(
                            id = 34L,
                            name = "Творог",
                            percent = 0.08F
                        )
                    )
                ),
                StatisticsCategoryDto(
                    id = 4L,
                    name = "Хлеб и выпечка",
                    iconUrl = "https://i.ibb.co/nBpWVwM/bread-2.png",
                    percent = 0.15F,
                    color = "#936242",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            id = 41L,
                            name = "Хлеб",
                            percent = 0.12F
                        ),
                        StatisticsSubcategoryDto(
                            id = 42L,
                            name = "Булочки",
                            percent = 0.03F
                        )
                    )
                ),
                StatisticsCategoryDto(
                    id = 5L,
                    name = "Морепродукты",
                    iconUrl = "https://i.ibb.co/3zdNTkr/fish.png",
                    percent = 0.10F,
                    color = "#9EADC0",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            id = 51L,
                            name = "Сельдь",
                            percent = 0.05F
                        ),
                        StatisticsSubcategoryDto(
                            id = 52L,
                            name = "Корюшка",
                            percent = 0.03F
                        ),
                        StatisticsSubcategoryDto(
                            id = 53L,
                            name = "Кальмар",
                            percent = 0.02F
                        )
                    )
                )
            )
        )
    }
}