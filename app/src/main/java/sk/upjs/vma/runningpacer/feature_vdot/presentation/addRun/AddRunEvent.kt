package sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun

sealed class AddRunEvent {
    data class RunDifficulty(val value: String) : AddRunEvent()
    data class RunDistance(val value: String) : AddRunEvent()
    data class RunTimeSS(val value: String) : AddRunEvent()
    data class RunTimeMM(val value: String) : AddRunEvent()
    data class RunTimeHH(val value: String) : AddRunEvent()
    data class PickedDistance(val value: String) : AddRunEvent()

    object SaveNote : AddRunEvent()
}
