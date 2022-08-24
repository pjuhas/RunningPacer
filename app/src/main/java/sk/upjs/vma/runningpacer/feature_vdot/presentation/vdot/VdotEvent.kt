package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import sk.upjs.vma.runningpacer.common.enum.OrderByEnum
import sk.upjs.vma.runningpacer.common.enum.OrderTypeEnum

sealed class VdotEvent {
    data class Calculate(val pace: Int) : VdotEvent()
}
