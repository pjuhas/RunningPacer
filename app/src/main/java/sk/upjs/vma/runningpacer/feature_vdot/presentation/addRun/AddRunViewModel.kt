package sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import sk.upjs.vma.runningpacer.common.enum.MetricTypeEnum
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.TrainingPaceUseCases
import javax.inject.Inject

@HiltViewModel
class AddRunViewModel @Inject constructor(
    private val trainingPaceUseCases: TrainingPaceUseCases
) : ViewModel() {

    private val _addRunDifficulty = mutableStateOf("")
    private val runDifficulty: State<String> = _addRunDifficulty

    private val _addRunDistance = mutableStateOf("")
    private val runDistance: State<String> = _addRunDistance

    private val _addRunTimeSS = mutableStateOf("")
    private val runTimeSS: State<String> = _addRunTimeSS

    private val _addRunTimeMM = mutableStateOf("")
    private val runTimeMM: State<String> = _addRunTimeMM

    private val _addRunTimeHH = mutableStateOf("")
    private val runTimeHH: State<String> = _addRunTimeHH

    private val _addRunPace = mutableStateOf("")
    val runPace: State<String> = _addRunPace

    private val _pickedDistance = mutableStateOf("")
    private val pickedDistance: State<String> = _pickedDistance

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var totalTime: Int = 0
    private var currentNoteId: Int? = null


    fun onEvent(event: AddRunEvent) {
        when (event) {
            is AddRunEvent.RunDifficulty -> {
                _addRunDifficulty.value = event.value
            }
            is AddRunEvent.RunDistance -> {
                _addRunDistance.value = event.value
                calculatePace()
            }
            is AddRunEvent.PickedDistance -> {
                _pickedDistance.value = event.value
                calculatePace()
            }
            is AddRunEvent.RunTimeSS -> {
                _addRunTimeSS.value = event.value
                calculatePace()
            }
            is AddRunEvent.RunTimeMM -> {
                _addRunTimeMM.value = event.value
                calculatePace()
            }
            is AddRunEvent.RunTimeHH -> {
                _addRunTimeHH.value = event.value
                calculatePace()
            }
            is AddRunEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        trainingPaceUseCases.addTrainingPace(
                            TrainingPace(
                                difficulty = runDifficulty.value,
                                distanceType = pickedDistance.value,
                                distance =  when(pickedDistance.value) {
                                    MetricTypeEnum.KILOMETERS.type -> { runDistance.value.toDouble() * 1000 }
                                    MetricTypeEnum.MILES.type -> { runDistance.value.toDouble() * 1609.344 }
                                    else -> {runDistance.value.toDouble()}
                                },
                                time = totalTime,
                                pace = runPace.value,
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                                } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnack(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun calculatePace() {
        viewModelScope.launch {
            totalTime = trainingPaceUseCases.regularTimeToSec(
                runTimeSS.value,
                runTimeMM.value,
                runTimeHH.value
            )
            val pace = trainingPaceUseCases.calculatePace(
                runDistance.value,
                pickedDistance.value,
                totalTime,
            )
            _addRunPace.value = pace
        }
    }

    sealed class UiEvent {
        data class ShowSnack(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}