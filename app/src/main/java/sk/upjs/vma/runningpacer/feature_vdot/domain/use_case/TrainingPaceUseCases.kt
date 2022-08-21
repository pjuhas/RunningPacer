package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

data class TrainingPaceUseCases(
    val getTrainingPaces: GetTrainingPaces,
    val addTrainingPace: AddTrainingPace,
    val calculatePace: CalculatePace,
    val regularTimeToSec: RegularTimeToSec
)