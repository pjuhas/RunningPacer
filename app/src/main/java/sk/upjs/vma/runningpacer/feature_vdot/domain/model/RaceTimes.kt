package sk.upjs.vma.runningpacer.feature_vdot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TableData(
    val racePace: RacePace = RacePace(0, 0, 0, 0, 0, 0, 0, 0, 0),
    val raceTimes: RaceTimes = RaceTimes(0, 0, 0, 0, 0, 0, 0, 0, 0),
    val vdot: Int = 0
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
    val distanceOnefivem: Int = 0
)

@Serializable
data class RacePace(
    val distanceMarathon: Int = 0,
    val distanceHalfMarathon: Int = 0,
    val distanceFiveteenk: Int = 0,
    val distanceTenk: Int = 0,
    val distanceFivek: Int = 0,
    val distanceTwoMiles: Int = 0,
    val distanceThreek: Int = 0,
    val distanceOnemile: Int = 0,
    val distanceOnefivem: Int = 0
)
