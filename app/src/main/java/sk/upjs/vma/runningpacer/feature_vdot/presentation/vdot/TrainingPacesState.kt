package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace
import sk.upjs.vma.runningpacer.feature_vdot.domain.util.OrderType
import sk.upjs.vma.runningpacer.feature_vdot.domain.util.TrainingPaceOrder

data class TrainingPacesState (
    val trainingPaces: List<TrainingPace> = emptyList(),
    val trainingPaceOrder: TrainingPaceOrder = TrainingPaceOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
