package sk.upjs.vma.runningpacer.feature_vdot.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.upjs.vma.runningpacer.common.enum.MetricTypeEnum
import sk.upjs.vma.runningpacer.common.enum.RunDifficultyEnum

@Entity
data class TrainingPace(
    var difficulty: String = "",
    @ColumnInfo(defaultValue = 0.toString()) var distance: Double = 0.0,
    @ColumnInfo(defaultValue = "") var distanceType: String = "",
    @ColumnInfo(defaultValue = 0.toString()) var time: Int = 0,
    @ColumnInfo(defaultValue = "") var pace: String = "",
    var timestamp: Long = 0L,
    @PrimaryKey(autoGenerate = true) var id: Int? = null
) {
    companion object {
        val metricType = MetricTypeEnum.values().map { it.type }
        val difficulties = RunDifficultyEnum.values().map { it.type }
    }
}

class InvalidTrainingPaceException(message: String) : Exception(message)
