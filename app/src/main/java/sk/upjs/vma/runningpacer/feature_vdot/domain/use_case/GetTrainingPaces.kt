package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.TrainingPaceRepository
import sk.upjs.vma.runningpacer.feature_vdot.domain.util.OrderType
import sk.upjs.vma.runningpacer.feature_vdot.domain.util.TrainingPaceOrder

class GetTrainingPaces(private val repository: TrainingPaceRepository) {

    operator fun invoke(
        trainingPaceOrder: TrainingPaceOrder = TrainingPaceOrder.Date(OrderType.Descending)
    ): Flow<List<TrainingPace>> {
        return repository.getTrainingPaces().map { trainingPaces ->
            when (trainingPaceOrder.orderType){
                is OrderType.Ascending -> {
                    when (trainingPaceOrder){
                        is TrainingPaceOrder.Distance -> trainingPaces.sortedBy { it.distance }
                        is TrainingPaceOrder.Date -> trainingPaces.sortedBy { it.timestamp }
                    }
                }
                is OrderType.Descending -> {
                    when (trainingPaceOrder){
                        is TrainingPaceOrder.Distance -> trainingPaces.sortedByDescending { it.distance }
                        is TrainingPaceOrder.Date -> trainingPaces.sortedByDescending { it.timestamp }
                    }
                }
            }
        }
    }
}