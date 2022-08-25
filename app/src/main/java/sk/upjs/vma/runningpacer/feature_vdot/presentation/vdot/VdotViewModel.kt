package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sk.upjs.vma.runningpacer.feature_vdot.data.repository.DataRepository
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.TrainingPaceUseCases
import javax.inject.Inject

@HiltViewModel
class VdotViewModel @Inject constructor(
    private val trainingPacesUseCase: TrainingPaceUseCases,
    private val dataRepository: DataRepository,

    ) : ViewModel() {
    private var totalTime: Int = 0
    private var calculateJob: Job? = null

    fun onEvent(event: VdotEvent) {
        when (event) {
            is VdotEvent.Calculate -> {
                calculateJob?.cancel()
                calculateJob = viewModelScope.launch {
                    totalTime = trainingPacesUseCase.regularTimeToSec(
                        event.runTimeSS,
                        event.runTimeMM,
                        event.runTimeHH
                    )
                    trainingPacesUseCase.getRacePaces(
                        event.distance,
                        totalTime
                    )
                }
            }
            else -> {}
        }
    }
}