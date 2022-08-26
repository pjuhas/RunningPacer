package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import sk.upjs.vma.runningpacer.common.enum.DistanceOptions
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RacePace
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTimes
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TableData
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.DataRepository

class GetRacePaces(private val repository: DataRepository) {

    suspend operator fun invoke(
        distanceOptions: DistanceOptions,
        time: Int,
    ) {
        when (distanceOptions.type) {
            DistanceOptions.MARATHON.type -> {
                val distanceObject = TableData(
                    racePace = RacePace(
                        distanceMarathon = 20,
                        distanceHalfMarathon = 19,
                        distanceFiveteenk = 18,
                        distanceTenk = 17,
                        distanceFivek = 16,
                        distanceTwoMiles =15,
                        distanceThreek = 15,
                        distanceOnemile = 14,
                        distanceOnefivem = 21
                    ),
                    raceTimes = RaceTimes(8,2,6,564,4,5,9,77,52),
                    vdot = 5
                )
                repository.setRaceDistances(
                    distance = distanceObject
                )

            }
            DistanceOptions.HALF_MARATHON.type -> {}
            DistanceOptions.FIFTEENK.type -> {}
            DistanceOptions.TENK.type -> {}
            DistanceOptions.FIVEK.type -> {}
            DistanceOptions.TWOMILES.type -> {}
            DistanceOptions.THREEK.type -> {}
            DistanceOptions.ONEMILE.type -> {}
            DistanceOptions.ONEFIVEM.type -> {}
        }
    }
}
