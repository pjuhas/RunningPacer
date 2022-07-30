package sk.upjs.vma.runningpacer.feature_vdot.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace

@Database(
    entities = [TrainingPace::class],
    version = 1,
    exportSchema = false
)
abstract class TrainingPaceDatabase : RoomDatabase() {

    abstract val trainingPaceDao: TrainingPaceDao

    companion object {
        const val DATABASE_NAME = "trainingPace_db"
    }
}