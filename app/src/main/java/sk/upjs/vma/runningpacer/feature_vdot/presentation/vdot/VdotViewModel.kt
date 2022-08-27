package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTimesList
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingTimesList
import sk.upjs.vma.runningpacer.feature_vdot.domain.use_case.TrainingPaceUseCases
import javax.inject.Inject

@HiltViewModel
class VdotViewModel @Inject constructor(
    private val trainingPacesUseCase: TrainingPaceUseCases,
) : ViewModel() {
    private var totalTime: Int = 0
    private lateinit var listOfRaceTimes: RaceTimesList
    private lateinit var listOfTrainingTimes: TrainingTimesList
    private var vdot = 30
    private var calculateJob: Job? = null
    private var loadDataJob: Job? = null

    fun onEvent(event: VdotEvent) {
        when (event) {
            is VdotEvent.LoadData -> {
                loadDataJob?.cancel()
                loadDataJob = viewModelScope.launch(Dispatchers.IO) {
                    listOfRaceTimes = trainingPacesUseCase.loadRaceData(event.context)
                    listOfTrainingTimes = trainingPacesUseCase.loadTrainingData(event.context)
                }
            }
            is VdotEvent.Calculate -> {
                calculateJob?.cancel()
                calculateJob = viewModelScope.launch {
                    totalTime = trainingPacesUseCase.regularTimeToSec(
                        runTimeSS = event.runTimeSS,
                        runTimeMM = event.runTimeMM,
                        runTimeHH = event.runTimeHH
                    )
                    vdot = trainingPacesUseCase.getRacePaces(
                        distanceOptions = event.distance,
                        time = totalTime,
                        listOfRaceTimes = listOfRaceTimes
                    )

                    trainingPacesUseCase.getTrainingPaces(
                        vdot = vdot,
                        listOfTrainingTimes = listOfTrainingTimes
                    )
                }
            }
        }
    }
}