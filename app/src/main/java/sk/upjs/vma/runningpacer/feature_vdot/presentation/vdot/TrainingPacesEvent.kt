package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import sk.upjs.vma.runningpacer.feature_vdot.domain.util.TrainingPaceOrder

sealed class TrainingPacesEvent {
    data class Order(val trainingPaceOrder: TrainingPaceOrder): TrainingPacesEvent()
}
