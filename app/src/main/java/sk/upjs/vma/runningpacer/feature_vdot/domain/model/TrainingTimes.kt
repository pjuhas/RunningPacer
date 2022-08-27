package sk.upjs.vma.runningpacer.feature_vdot.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainingTableData(
    val vdotType: VdotType = VdotType(30),
    val easyType: EasyType = EasyType(""),
    val marathonType: MarathonType = MarathonType(0),
    val thresholdType: ThresholdType = ThresholdType(0, 0),
    val intervalType: IntervalType = IntervalType(0, 0, 0, 0),
    val relayType: RelayType = RelayType(0, 0, 0, 0, 0)
)

@Serializable
class VdotType(
    val vdot: Int = 30
)

@Serializable
class EasyType(
    val distanceKm: String = ""
)

@Serializable
class MarathonType(
    val distanceKm: Int = 0
)

@Serializable
class ThresholdType(
    val distanceKm: Int = 0,
    val distanceFourm: Int = 0
)

@Serializable
class IntervalType(
    val distanceMile: Int = 0,
    val distanceOnetwom: Int = 0,
    val distanceKm: Int = 0,
    val distanceFourm: Int = 0
)

@Serializable
class RelayType(
    val distanceTwom: Int = 0,
    val distanceThreem: Int = 0,
    val distanceFourm: Int = 0,
    val distanceSixm: Int = 0,
    val distanceEightm: Int = 0,
)


class TrainingTimesList(
    val vdotTypeList: MutableList<VdotType> = mutableListOf(),
    val easyTypeList: MutableList<EasyType> = mutableListOf(),
    val marathonTypeList: MutableList<MarathonType> = mutableListOf(),
    val thresholdTypeList: MutableList<ThresholdType> = mutableListOf(),
    val intervalTypeList: MutableList<IntervalType> = mutableListOf(),
    val relayTypeList: MutableList<RelayType> = mutableListOf()
)
