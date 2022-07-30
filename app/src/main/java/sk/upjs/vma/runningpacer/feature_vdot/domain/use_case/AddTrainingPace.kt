package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import sk.upjs.vma.runningpacer.feature_vdot.domain.model.InvalidTrainingPaceException
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.TrainingPaceRepository

class AddTrainingPace(private val repository: TrainingPaceRepository) {

    @Throws(InvalidTrainingPaceException::class)
    suspend operator fun invoke(trainingPace: TrainingPace){
        if (trainingPace.difficulty.isBlank()){
            throw InvalidTrainingPaceException("Empty values")
        }
        repository.insertTrainingPace(trainingPace)
    }
}