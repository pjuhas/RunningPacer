package sk.upjs.vma.runningpacer.feature_vdot.domain.repository

import kotlinx.coroutines.flow.Flow
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace

interface TrainingPaceRepository {

    fun getTrainingPaces(): Flow<List<TrainingPace>>

    suspend fun getLatestTrainingPace(): TrainingPace?

    suspend fun insertTrainingPace(trainingPace: TrainingPace)
}