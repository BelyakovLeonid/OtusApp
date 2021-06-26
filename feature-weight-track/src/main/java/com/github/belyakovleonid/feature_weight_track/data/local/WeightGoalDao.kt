package com.github.belyakovleonid.feature_weight_track.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.belyakovleonid.feature_weight_track.data.local.model.WeightGoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightGoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoal(goal: WeightGoalEntity)

    @Query("SELECT * FROM ${WeightGoalEntity.WEIGHT_GOAL_TABLE_NAME}")
    fun getWeightGoalAsFlow(): Flow<WeightGoalEntity>
}