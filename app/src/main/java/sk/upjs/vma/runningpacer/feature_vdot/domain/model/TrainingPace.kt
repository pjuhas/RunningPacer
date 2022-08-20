package sk.upjs.vma.runningpacer.feature_vdot.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingPace(
    var difficulty: String = "",
    var distance: Int = 0,
    @ColumnInfo(defaultValue = "") var distanceType: String = "",
    @ColumnInfo(defaultValue = 0.toString()) var time: Int = 0,
    @ColumnInfo(defaultValue = "") var pace: String = "",
    var timestamp: Long = 0L,
    @PrimaryKey(autoGenerate = true) var id: Int? = null
){

    companion object {
        val difficulties = listOf("Easy", "Tempo", "Threshold", "Interval", "Long")
        val length = listOf("m", "km", "mile")
    }
}

class InvalidTrainingPaceException(message: String): Exception(message)
