package sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns

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
class TrainingPacesViewModel @Inject constructor(
    private val trainingPacesUseCase: TrainingPaceUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TrainingPacesState())
    val state: State<TrainingPacesState> = _state

    private var getTrainingPaceJob: Job? = null

    init {
        getTrainingPaces(OrderTypeEnum.DESCENDING, OrderByEnum.DATE)
    }

    fun onEvent(event: TrainingPacesEvent) {
        when (event) {
            is TrainingPacesEvent.Order -> {
                if (state.value.orderType::class == event.orderType::class
                    && state.value.orderBy::class == event.orderBy::class
                    && state.value.orderType == event.orderType
                    && state.value.orderBy == event.orderBy
                ) {
                    return
                }
                getTrainingPaces(event.orderType, event.orderBy)
            }

        }
    }

    private fun getTrainingPaces(orderType: OrderTypeEnum, orderBy: OrderByEnum) {
        getTrainingPaceJob?.cancel()
        getTrainingPaceJob = trainingPacesUseCase.getTrainingPaces(orderType, orderBy)
            .onEach { trainingPaces ->
                _state.value = state.value.copy(
                    trainingPaces = trainingPaces,
                    orderType = orderType,
                    orderBy = orderBy
                )
            }.launchIn(viewModelScope)
    }
}