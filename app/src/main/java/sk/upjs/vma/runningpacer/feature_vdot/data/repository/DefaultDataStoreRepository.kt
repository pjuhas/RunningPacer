package sk.upjs.vma.runningpacer.feature_vdot.data.repository

import androidx.datastore.core.DataStore
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RacePace
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTimes
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTableData
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingTableData
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.DataRepository
import javax.inject.Inject


class DefaultDataStoreRepository @Inject constructor(
    private val raceDataStore: DataStore<RaceTableData>,
    private val trainingDataStore: DataStore<TrainingTableData>
) : DataRepository {

    override suspend fun setRacePace(raceTableData: RaceTableData) {
        raceDataStore.updateData {
            it.copy(
                racePace = raceTableData.racePace,
                raceTimes = raceTableData.raceTimes
            )
        }
    }

    override suspend fun setTrainingPace(trainingTableData: TrainingTableData) {
        trainingDataStore.updateData {
            it.copy(
                vdotType = trainingTableData.vdotType,
                marathonType = trainingTableData.marathonType,
                easyType = trainingTableData.easyType,
                relayType = trainingTableData.relayType,
                intervalType = trainingTableData.intervalType,
                thresholdType = trainingTableData.thresholdType
            )
        }
    }
}