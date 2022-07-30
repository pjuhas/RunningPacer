package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.TrainingPaceUseCases
import sk.upjs.vma.runningpacer.feature_vdot.domain.util.OrderType
import sk.upjs.vma.runningpacer.feature_vdot.domain.util.TrainingPaceOrder
import javax.inject.Inject

@HiltViewModel
class TrainingPacesViewModel @Inject constructor(
    private val trainingPacesUseCase: TrainingPaceUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TrainingPacesState())
    val state: State<TrainingPacesState> = _state

    private var getTrainingPaceJob: Job? = null

    init {
        getTrainingPaces(TrainingPaceOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: TrainingPacesEvent) {
        when (event) {
            is TrainingPacesEvent.Order -> {
                if (state.value.trainingPaceOrder::class == event.trainingPaceOrder::class
                    && state.value.trainingPaceOrder.orderType == event.trainingPaceOrder.orderType){
                    return
                }
                getTrainingPaces(event.trainingPaceOrder)
            }
        }
    }

    private fun getTrainingPaces(trainingPaceOrder: TrainingPaceOrder) {
        getTrainingPaceJob?.cancel()
        getTrainingPaceJob = trainingPacesUseCase.getTrainingPaces(trainingPaceOrder).onEach {
            trainingPaces -> _state.value = state.value.copy(
                trainingPaces = trainingPaces,
                trainingPaceOrder = trainingPaceOrder
            )
        }.launchIn(viewModelScope)
    }
}