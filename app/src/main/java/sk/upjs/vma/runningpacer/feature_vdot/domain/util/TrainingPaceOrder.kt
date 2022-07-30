package sk.upjs.vma.runningpacer.feature_vdot.domain.util

sealed class TrainingPaceOrder(val orderType: OrderType) {
    class Distance(orderType: OrderType): TrainingPaceOrder(orderType)
    class Date(orderType: OrderType): TrainingPaceOrder(orderType)
}
