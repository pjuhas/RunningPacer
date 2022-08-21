package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sk.upjs.vma.runningpacer.common.enum.OrderByEnum
import sk.upjs.vma.runningpacer.common.enum.OrderTypeEnum
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.TrainingPaceRepository

class GetTrainingPaces(private val repository: TrainingPaceRepository) {

    operator fun invoke(
        orderType: OrderTypeEnum = OrderTypeEnum.DESCENDING,
        orderBy: OrderByEnum = OrderByEnum.DATE
    ): Flow<List<TrainingPace>> {
        return repository.getTrainingPaces().map { trainingPaces ->
            when (orderType){
                OrderTypeEnum.ASCENDING -> {
                    when (orderBy){
                        OrderByEnum.DISTANCE -> trainingPaces.sortedBy { it.distance }
                        OrderByEnum.DATE -> trainingPaces.sortedBy { it.timestamp }
                        OrderByEnum.TIME -> trainingPaces.sortedBy { it.time }
                    }
                }
                OrderTypeEnum.DESCENDING -> {
                    when (orderBy){
                        OrderByEnum.DISTANCE -> trainingPaces.sortedByDescending { it.distance }
                        OrderByEnum.DATE -> trainingPaces.sortedByDescending { it.timestamp }
                        OrderByEnum.TIME -> trainingPaces.sortedByDescending { it.time }
                    }
                }
            }
        }
    }
}