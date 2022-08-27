package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import sk.upjs.vma.runningpacer.feature_vdot.domain.model.*
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.DataRepository

class GetTrainingPaces(private val repository: DataRepository) {
    suspend operator fun invoke(
        vdot: Int,
        listOfTrainingTimes: TrainingTimesList,
    ) {
        var trainingObject = TrainingTableData()
        for (i in 0 until listOfTrainingTimes.vdotTypeList.size) {
            if (listOfTrainingTimes.vdotTypeList[i].vdot == vdot) {
                trainingObject = TrainingTableData(
                    vdotType = VdotType(listOfTrainingTimes.vdotTypeList[i].vdot),
                    easyType = EasyType(listOfTrainingTimes.easyTypeList[i].distanceKm),
                    marathonType = MarathonType(listOfTrainingTimes.marathonTypeList[i].distanceKm),
                    thresholdType = ThresholdType(
                        listOfTrainingTimes.thresholdTypeList[i].distanceKm,
                        listOfTrainingTimes.thresholdTypeList[i].distanceFourm
                    ),
                    intervalType = IntervalType(
                        distanceMile = listOfTrainingTimes.intervalTypeList[i].distanceMile,
                        distanceOnetwom = listOfTrainingTimes.intervalTypeList[i].distanceOnetwom,
                        distanceKm = listOfTrainingTimes.intervalTypeList[i].distanceKm,
                        distanceFourm = listOfTrainingTimes.intervalTypeList[i].distanceFourm
                    ),
                    relayType = RelayType(
                        distanceTwom = listOfTrainingTimes.relayTypeList[i].distanceTwom,
                        distanceThreem = listOfTrainingTimes.relayTypeList[i].distanceThreem,
                        distanceFourm = listOfTrainingTimes.relayTypeList[i].distanceFourm,
                        distanceSixm = listOfTrainingTimes.relayTypeList[i].distanceSixm,
                        distanceEightm = listOfTrainingTimes.relayTypeList[i].distanceEightm
                    )
                )
                break
            }
        }
        repository.setTrainingPace(trainingObject)
    }
}
