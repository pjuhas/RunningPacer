package sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns

import sk.upjs.vma.runningpacer.common.enum.OrderByEnum
import sk.upjs.vma.runningpacer.common.enum.OrderTypeEnum
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace

data class TrainingPacesState(
    val trainingPaces: List<TrainingPace> = emptyList(),
    val orderType: OrderTypeEnum = OrderTypeEnum.DESCENDING,
    val orderBy: OrderByEnum = OrderByEnum.DATE
)
