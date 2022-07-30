package sk.upjs.vma.runningpacer.feature_vdot.data.repository

import kotlinx.coroutines.flow.Flow
import sk.upjs.vma.runningpacer.feature_vdot.data.data_source.TrainingPaceDao
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.TrainingPaceRepository

class TrainingPaceImpl(
    private val trainingPaceDao: TrainingPaceDao
) : TrainingPaceRepository {

    override fun getTrainingPaces(): Flow<List<TrainingPace>> {
        return trainingPaceDao.getTrainingPaces()
    }

    override suspend fun getLatestTrainingPace(): TrainingPace? {
        return trainingPaceDao.getLatestTrainingPace()
    }

    override suspend fun insertTrainingPace(trainingPace: TrainingPace) {
        return trainingPaceDao.insertTrainingPace(trainingPace)
    }

}