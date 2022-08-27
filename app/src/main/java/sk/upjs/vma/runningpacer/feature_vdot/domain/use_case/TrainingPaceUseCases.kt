package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

data class TrainingPaceUseCases(
    val getTrainingPacesOrder: GetTrainingPacesOrder,
    val addTrainingPace: AddTrainingPace,
    val calculatePace: CalculatePace,
    val regularTimeToSec: RegularTimeToSec,
    val getRacePaces: GetRacePaces,
    val getTrainingPaces: GetTrainingPaces,
    val loadRaceData: LoadRaceData,
    val loadTrainingData: LoadTrainingData
)