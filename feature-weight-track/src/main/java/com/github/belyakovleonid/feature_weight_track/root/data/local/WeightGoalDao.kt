package com.github.belyakovleonid.feature_weight_track.root.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.belyakovleonid.feature_weight_track.root.data.local.model.WeightGoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightGoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: WeightGoalEntity)

    @Query(SELECT_GOAL_QUERY)
    fun getWeightGoalAsFlow(): Flow<WeightGoalEntity?>

    @Query(SELECT_GOAL_QUERY)
    suspend fun getWeightGoal(): WeightGoalEntity?

    companion object {

        private const val SELECT_GOAL_QUERY = """
            SELECT * FROM ${WeightGoalEntity.WEIGHT_GOAL_TABLE_NAME}
            WHERE id = ${WeightGoalEntity.THE_ONLY_ID}
        """
    }
}