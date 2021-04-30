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
                    iconUrl = "https://drive.google.com/file/d/1q8ho3ZBiGm0V-ihoV_-oL4-pfLTJGwbs/view?usp=sharing",
                    percent = 0.35,
                    color = "#F29702",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Банан",
                            percent = 0.1
                        ),
                        StatisticsSubcategoryDto(
                            name = "Апельсин",
                            percent = 0.2
                        ),
                        StatisticsSubcategoryDto(
                            name = "Яблоко",
                            percent = 0.3
                        ),
                        StatisticsSubcategoryDto(
                            name = "Ананас",
                            percent = 0.05
                        ),
                        StatisticsSubcategoryDto(
                            name = "Мараккуя",
                            percent = 0.04
                        ),
                        StatisticsSubcategoryDto(
                            name = "Картошка",
                            percent = 0.11
                        ),
                    )
                ),
                StatisticsCategoryDto(
                    name = "Мясные продукты",
                    iconUrl = "https://drive.google.com/file/d/1gk9XUnPIfGMUdNfiYNKO60Edl3VIglo7/view?usp=sharing",
                    percent = 0.18,
                    color = "#EC5179",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Свинина",
                            percent = 0.11
                        ),
                        StatisticsSubcategoryDto(
                            name = "Говядина",
                            percent = 0.03
                        ),
                        StatisticsSubcategoryDto(
                            name = "Птица",
                            percent = 0.03
                        )
                    )
                ),
                StatisticsCategoryDto(
                    name = "Молочные продукты и яйца",
                    iconUrl = "https://drive.google.com/file/d/1R0xpbZEa_4WExW8jw7EJ6LySY7w4BKHg/view?usp=sharing",
                    percent = 0.22,
                    color = "#A0EBFF",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Яйца",
                            percent = 0.08
                        ),
                        StatisticsSubcategoryDto(
                            name = "Молоко",
                            percent = 0.03
                        ),
                        StatisticsSubcategoryDto(
                            name = "Кефир",
                            percent = 0.05
                        ),
                        StatisticsSubcategoryDto(
                            name = "Творог",
                            percent = 0.08
                        )
                    )
                ),
                StatisticsCategoryDto(
                    name = "Хлеб и выпечка",
                    iconUrl = "https://drive.google.com/file/d/1Gp3EdFUOTUVDMiHUEZxGQsnoj7qeLA9g/view?usp=sharing",
                    percent = 0.05,
                    color = "#936242",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Хлеб",
                            percent = 0.02
                        ),
                        StatisticsSubcategoryDto(
                            name = "Булочки",
                            percent = 0.03
                        )
                    )
                ),
                StatisticsCategoryDto(
                    name = "Морепродукты",
                    iconUrl = "https://drive.google.com/file/d/1gvKv5zu9kiwNsv98pVHipF0bIy8yCHSV/view?usp=sharing",
                    percent = 0.10,
                    color = "#9EADC0",
                    subcategories = listOf(
                        StatisticsSubcategoryDto(
                            name = "Сельдь",
                            percent = 0.05
                        ),
                        StatisticsSubcategoryDto(
                            name = "Корюшка",
                            percent = 0.03
                        ),
                        StatisticsSubcategoryDto(
                            name = "Кальмар",
                            percent = 0.02
                        )
                    )
                )
            )
        )
    }
}