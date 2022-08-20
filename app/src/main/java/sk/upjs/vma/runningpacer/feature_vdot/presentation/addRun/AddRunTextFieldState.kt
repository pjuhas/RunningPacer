package sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun

data class AddRunTextFieldState(
    var text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)