package sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.TrainingPaceUseCases
import javax.inject.Inject

@HiltViewModel
class AddRunViewModel @Inject constructor(
    private val trainingPaceUseCases: TrainingPaceUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _addRunDifficulty = mutableStateOf(AddRunTextFieldState())
    val runDifficulty: State<AddRunTextFieldState> = _addRunDifficulty

    private val _addRunDistance = mutableStateOf(AddRunTextFieldState())
    val runDistance: State<AddRunTextFieldState> = _addRunDistance

    private val _addRunTimeSS = mutableStateOf(AddRunTextFieldState())
    val runTimeSS: State<AddRunTextFieldState> = _addRunTimeSS

    private val _addRunTimeMM = mutableStateOf(AddRunTextFieldState())
    val runTimeMM: State<AddRunTextFieldState> = _addRunTimeMM

    private val _addRunTimeHH = mutableStateOf(AddRunTextFieldState())
    val runTimeHH: State<AddRunTextFieldState> = _addRunTimeHH

    private val _addRunPace = mutableStateOf(AddRunTextFieldState())
    val runPace: State<AddRunTextFieldState> = _addRunPace

    private val _pickedDistance = mutableStateOf(AddRunTextFieldState())
    val pickedDistance: State<AddRunTextFieldState> = _pickedDistance

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null


    fun onEvent(event: AddRunEvent) {
        when(event) {
            is AddRunEvent.RunDifficulty -> {
                _addRunDifficulty.value = _addRunDifficulty.value.copy(
                    text = event.value
                )
            }
            is AddRunEvent.RunDistance -> {
                _addRunDistance.value = _addRunDistance.value.copy(
                    text = event.value
                )
                calculatePace()
            }
            is AddRunEvent.PickedDistance -> {
                _pickedDistance.value = _pickedDistance.value.copy(
                    text = event.value
                )
                calculatePace()
            }

            is AddRunEvent.RunTimeSS -> {
                _addRunTimeSS.value = _addRunTimeSS.value.copy(
                    text = event.value
                )
                calculatePace()
            }
            is AddRunEvent.RunTimeMM -> {
                _addRunTimeMM.value = _addRunTimeMM.value.copy(
                    text = event.value
                )
                calculatePace()
            }
            is AddRunEvent.RunTimeHH -> {
                _addRunTimeHH.value = _addRunTimeHH.value.copy(
                    text = event.value
                )
                calculatePace()
            }


            is AddRunEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        trainingPaceUseCases.addTrainingPace(
                            TrainingPace(
                                difficulty = runDifficulty.value.text,
                                distanceType = pickedDistance.value.text,
                                distance =  Integer.parseInt(runDistance.value.text),
                                /*
                                when(pickedDistance.value.text) {
                                    "mile" -> { Integer.parseInt(runDistance.value.text) * 1000 }
                                    "km" -> { Integer.parseInt(runDistance.value.text) * 1609 }
                                    else -> { Integer.parseInt(runDistance.value.text)}
                                },
                                */
                                time = (runTimeHH.value.text.toIntOrNull()?:0) * 60 * 60 +
                                        (runTimeMM.value.text.toIntOrNull()?:0) * 60 +
                                        (runTimeSS.value.text.toIntOrNull()?:0),
                                pace = runPace.value.text,
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch(e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
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
            val pace = trainingPaceUseCases.calculatePace(runDistance.value.text, pickedDistance.value.text, runTimeSS.value.text, runTimeMM.value.text, runTimeHH.value.text)
            _addRunPace.value.text = pace
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}