package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RacePace
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTimes
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TableData
import sk.upjs.vma.runningpacer.common.enum.DistanceOptions
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.DefaultDataStoreRepository

class GetRacePaces(private val repository: DefaultDataStoreRepository) {

    suspend operator fun invoke(
        distanceOptions: DistanceOptions,
        time: Int,
    ) {
        when (distanceOptions.type) {
            DistanceOptions.MARATHON.type -> {
                val distanceObject = TableData(
                    racePace = RacePace(
                        distanceMarathon = 8,
                        distanceHalfMarathon = 7,
                        distanceFiveteenk = 6,
                        distanceTenk = 5,
                        distanceFivek = 4,
                        distanceTwoMiles = 3,
                        distanceThreek = 2,
                        distanceOnemile = 1,
                        distanceOnefivem = 0
                    ),
                    raceTimes = RaceTimes(),
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
