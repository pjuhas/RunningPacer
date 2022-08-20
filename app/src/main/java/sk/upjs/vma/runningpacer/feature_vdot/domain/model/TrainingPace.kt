package sk.upjs.vma.runningpacer.feature_vdot.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingPace(
    var difficulty: String = "",
    @ColumnInfo(defaultValue = 0.toString()) var distance: Double = 0.0,
    @ColumnInfo(defaultValue = "") var distanceType: String = "",
    @ColumnInfo(defaultValue = 0.toString()) var time: Int = 0,
    @ColumnInfo(defaultValue = "") var pace: String = "",
    var timestamp: Long = 0L,
    @PrimaryKey(autoGenerate = true) var id: Int? = null
){
    companion object {
        val metricType = MetricType.values().map { it.type }
        val difficulties = RunDifficulty.values().map { it.type }
    }
}

enum class MetricType(val type: String, val alias: String){
    METERS("m", "m"),
    KILOMETERS("km", "km"),
    MILES("Mile", "mile")
}

enum class RunDifficulty(val type: String){
    EASY("Easy"),
    TEMPO("Tempo"),
    THRESHOLD("Threshold"),
    INTERVAL("Interval"),
    LONG("Long")
}

class InvalidTrainingPaceException(message: String): Exception(message)
