package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import android.content.Context
import sk.upjs.vma.runningpacer.common.enum.DistanceOptions
import sk.upjs.vma.runningpacer.common.enum.OrderByEnum
import sk.upjs.vma.runningpacer.common.enum.OrderTypeEnum
import sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun.AddRunEvent

sealed class VdotEvent {
    data class Calculate(
        val distance: DistanceOptions,
        val runTimeSS: String,
        val runTimeMM: String,
        val runTimeHH: String
    ) : VdotEvent()

    data class LoadData(
        val context: Context
    ) : VdotEvent()
}
