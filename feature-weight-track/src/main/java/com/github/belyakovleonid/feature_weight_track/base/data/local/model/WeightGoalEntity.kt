package com.github.belyakovleonid.feature_weight_track.base.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.belyakovleonid.feature_weight_track.base.data.local.model.WeightGoalEntity.Companion.WEIGHT_GOAL_TABLE_NAME
import com.github.belyakovleonid.feature_weight_track.base.domain.model.WeightGoal

@Entity(tableName = WEIGHT_GOAL_TABLE_NAME)
data class WeightGoalEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = THE_ONLY_ID,
    val weight: Float
) {

    companion object {
        const val WEIGHT_GOAL_TABLE_NAME = "weight_goal"
        const val THE_ONLY_ID = 1L
    }
}

fun WeightGoalEntity.toDomain(): WeightGoal {
    return WeightGoal(
        weight = weight
    )
}