package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sk.upjs.vma.runningpacer.common.enum.OrderByEnum
import sk.upjs.vma.runningpacer.common.enum.OrderTypeEnum
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.TrainingPaceUseCases
import javax.inject.Inject

@HiltViewModel
class VdotViewModel @Inject constructor(
    private val trainingPacesUseCase: TrainingPaceUseCases
) : ViewModel() {

    fun onEvent(event: VdotEvent) {
        when (event) {
            is VdotEvent.Calculate -> {
            }

        }
    }

}