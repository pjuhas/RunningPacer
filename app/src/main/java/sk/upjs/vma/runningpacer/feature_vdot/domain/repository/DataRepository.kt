package sk.upjs.vma.runningpacer.feature_vdot.domain.repository

import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTableData
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingTableData

interface DataRepository {

    suspend fun setRacePace(raceTableData: RaceTableData)

    suspend fun setTrainingPace(trainingTableData: TrainingTableData)

}