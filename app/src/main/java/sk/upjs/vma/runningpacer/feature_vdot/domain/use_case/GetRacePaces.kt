package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import android.text.format.DateUtils
import sk.upjs.vma.runningpacer.common.enum.DistanceOptions
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RacePace
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTimesList
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTableData
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.DataRepository
import kotlin.math.abs
import kotlin.math.roundToInt

class GetRacePaces(private val repository: DataRepository) {

    suspend operator fun invoke(
        distanceOptions: DistanceOptions,
        time: Int,
        listOfRaceTimes: RaceTimesList,
    ): Int {
        var distanceObject = RaceTableData()
        var closestPace = Int.MAX_VALUE
        var rememberIndex = 0

        when (distanceOptions.type) {
            DistanceOptions.MARATHON.type -> {
                for (i in 0 until listOfRaceTimes.raceList.size) {
                    if (closestPace > abs(time - listOfRaceTimes.raceList[i].distanceMarathon)) {
                        closestPace = abs(time - listOfRaceTimes.raceList[i].distanceMarathon)
                        rememberIndex = i
                    }
                }
                distanceObject = RaceTableData(
                    raceTimes = listOfRaceTimes.raceList[rememberIndex],
                )
            }
            DistanceOptions.HALF_MARATHON.type -> {
                for (i in 0 until listOfRaceTimes.raceList.size) {
                    if (closestPace > abs(time - listOfRaceTimes.raceList[i].distanceHalfMarathon)) {
                        closestPace = abs(time - listOfRaceTimes.raceList[i].distanceHalfMarathon)
                        rememberIndex = i
                    }
                }
                distanceObject = RaceTableData(
                    raceTimes = listOfRaceTimes.raceList[rememberIndex],
                )
            }
            DistanceOptions.FIFTEENK.type -> {
                for (i in 0 until listOfRaceTimes.raceList.size) {
                    if (closestPace > abs(time - listOfRaceTimes.raceList[i].distanceFiveteenk)) {
                        closestPace = abs(time - listOfRaceTimes.raceList[i].distanceFiveteenk)
                        rememberIndex = i
                    }
                }
                distanceObject = RaceTableData(
                    raceTimes = listOfRaceTimes.raceList[rememberIndex],
                )
            }
            DistanceOptions.TENK.type -> {
                for (i in 0 until listOfRaceTimes.raceList.size) {
                    if (closestPace > abs(time - listOfRaceTimes.raceList[i].distanceTenk)) {
                        closestPace = abs(time - listOfRaceTimes.raceList[i].distanceTenk)
                        rememberIndex = i
                    }
                }
                distanceObject = RaceTableData(
                    raceTimes = listOfRaceTimes.raceList[rememberIndex],
                )
            }
            DistanceOptions.FIVEK.type -> {
                for (i in 0 until listOfRaceTimes.raceList.size) {
                    if (closestPace > abs(time - listOfRaceTimes.raceList[i].distanceFivek)) {
                        closestPace = abs(time - listOfRaceTimes.raceList[i].distanceFivek)
                        rememberIndex = i
                    }
                }
                distanceObject = RaceTableData(
                    raceTimes = listOfRaceTimes.raceList[rememberIndex],
                )
            }
            DistanceOptions.TWOMILES.type -> {
                for (i in 0 until listOfRaceTimes.raceList.size) {
                    if (closestPace > abs(time - listOfRaceTimes.raceList[i].distanceTwoMiles)) {
                        closestPace = abs(time - listOfRaceTimes.raceList[i].distanceTwoMiles)
                        rememberIndex = i
                    }
                }
                distanceObject = RaceTableData(
                    raceTimes = listOfRaceTimes.raceList[rememberIndex],
                )
            }
            DistanceOptions.THREEK.type -> {
                for (i in 0 until listOfRaceTimes.raceList.size) {
                    if (closestPace > abs(time - listOfRaceTimes.raceList[i].distanceThreek)) {
                        closestPace = abs(time - listOfRaceTimes.raceList[i].distanceThreek)
                        rememberIndex = i
                    }
                }
                distanceObject = RaceTableData(
                    raceTimes = listOfRaceTimes.raceList[rememberIndex],
                )
            }
            DistanceOptions.ONEMILE.type -> {
                for (i in 0 until listOfRaceTimes.raceList.size) {
                    if (closestPace > abs(time - listOfRaceTimes.raceList[i].distanceOnemile)) {
                        closestPace = abs(time - listOfRaceTimes.raceList[i].distanceOnemile)
                        rememberIndex = i
                    }
                }
                distanceObject = RaceTableData(
                    raceTimes = listOfRaceTimes.raceList[rememberIndex],
                )
            }
            DistanceOptions.ONEFIVEM.type -> {
                for (i in 0 until listOfRaceTimes.raceList.size) {
                    if (closestPace > abs(time - listOfRaceTimes.raceList[i].distanceOnefivem)) {
                        closestPace = abs(time - listOfRaceTimes.raceList[i].distanceOnefivem)
                        rememberIndex = i
                    }
                }
                distanceObject = RaceTableData(
                    raceTimes = listOfRaceTimes.raceList[rememberIndex]
                )
            }
        }

        distanceObject.racePace = RacePace(
            distanceMarathon = minPerKm(distanceObject.raceTimes.distanceMarathon, DistanceOptions.MARATHON.length),
            distanceHalfMarathon = minPerKm(distanceObject.raceTimes.distanceHalfMarathon, DistanceOptions.HALF_MARATHON.length),
            distanceFiveteenk = minPerKm(distanceObject.raceTimes.distanceFiveteenk, DistanceOptions.FIFTEENK.length),
            distanceTenk = minPerKm(distanceObject.raceTimes.distanceTenk, DistanceOptions.TENK.length),
            distanceFivek = minPerKm(distanceObject.raceTimes.distanceFivek, DistanceOptions.FIVEK.length),
            distanceTwoMiles =  minPerKm(distanceObject.raceTimes.distanceTwoMiles, DistanceOptions.TWOMILES.length),
            distanceThreek =  minPerKm(distanceObject.raceTimes.distanceThreek, DistanceOptions.THREEK.length),
            distanceOnemile =  minPerKm(distanceObject.raceTimes.distanceOnemile, DistanceOptions.ONEMILE.length),
            distanceOnefivem =  minPerKm(distanceObject.raceTimes.distanceOnefivem, DistanceOptions.ONEFIVEM.length),
        )
        repository.setRacePace(
            raceTableData = distanceObject
        )
        return listOfRaceTimes.raceList[rememberIndex].vdot
    }

    private fun minPerKm(timeInSec: Int, length: Double): String {
        return "%s m/km".format(DateUtils.formatElapsedTime((timeInSec / length).roundToInt().toLong()))
    }
}
