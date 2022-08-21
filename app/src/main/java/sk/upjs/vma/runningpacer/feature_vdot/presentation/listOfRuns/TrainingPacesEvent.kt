package sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns

import sk.upjs.vma.runningpacer.common.enum.OrderByEnum
import sk.upjs.vma.runningpacer.common.enum.OrderTypeEnum

sealed class TrainingPacesEvent {
    data class Order(val orderType: OrderTypeEnum, val orderBy: OrderByEnum) : TrainingPacesEvent()
}
