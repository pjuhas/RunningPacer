package sk.upjs.vma.runningpacer.feature_vdot.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingPace(
    var difficulty: String = "",
    var distance: Int = 0,
    var pace: Int = 0,
    var timestamp: Long = 0L,
    @PrimaryKey(autoGenerate = true) var id: Int? = null
){

    companion object {
        val difficulties = listOf("Easy", "Marathon", "Threshold", "Interval", "Repetition")
        val distances = listOf(200, 400, 600, 800, 1000, 1200, 1600)
    }
}

class InvalidTrainingPaceException(message: String): Exception(message)
