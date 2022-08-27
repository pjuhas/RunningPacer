package sk.upjs.vma.runningpacer.feature_vdot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RaceTableData(
    var racePace: RacePace = RacePace("", "", "", "", "", "", "", "", ""),
    val raceTimes: RaceTimes = RaceTimes(
        Int.MAX_VALUE,
        Int.MAX_VALUE,
        Int.MAX_VALUE,
        Int.MAX_VALUE,
        Int.MAX_VALUE,
        Int.MAX_VALUE,
        Int.MAX_VALUE,
        Int.MAX_VALUE
    ),
)


@Serializable
data class RaceTimes(
    val distanceMarathon: Int = 0,
    val distanceHalfMarathon: Int = 0,
    val distanceFiveteenk: Int = 0,
    val distanceTenk: Int = 0,
    val distanceFivek: Int = 0,
    val distanceTwoMiles: Int = 0,
    val distanceThreek: Int = 0,
    val distanceOnemile: Int = 0,
    val distanceOnefivem: Int = 0,
    val vdot: Int = 0
)

@Serializable
data class RacePace(
    var distanceMarathon: String = "",
    val distanceHalfMarathon: String = "",
    val distanceFiveteenk: String = "",
    val distanceTenk: String = "",
    val distanceFivek: String = "",
    val distanceTwoMiles: String = "",
    val distanceThreek: String = "",
    val distanceOnemile: String = "",
    val distanceOnefivem: String = "",
)

class RaceTimesList(
    val raceList: MutableList<RaceTimes> = mutableListOf(),
    val paceList: MutableList<RacePace> = mutableListOf(),
)
