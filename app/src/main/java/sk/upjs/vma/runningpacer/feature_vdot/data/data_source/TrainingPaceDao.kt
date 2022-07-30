package sk.upjs.vma.runningpacer.feature_vdot.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace

@Dao
interface TrainingPaceDao {

    @Query("SELECT * FROM trainingPace")
    fun getTrainingPaces(): Flow<List<TrainingPace>>

    @Query("SELECT * FROM trainingPace ORDER BY id DESC LIMIT 1")
    suspend fun getLatestTrainingPace(): TrainingPace?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingPace(trainingPace: TrainingPace)
}