package sk.upjs.vma.runningpacer.feature_vdot.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
