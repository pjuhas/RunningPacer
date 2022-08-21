package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import android.text.format.DateUtils
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.InvalidTrainingPaceException
import sk.upjs.vma.runningpacer.common.enum.MetricTypeEnum

class CalculatePace() {

    operator fun invoke(
        runDistance: String,
        runMetrics: String,
        totalTime: Int
    ): String {
        if (runDistance.isEmpty() || runMetrics.isEmpty() || runDistance.toDoubleOrNull() == null) {
            return "Fill out distance and metrics"
        }
        when (runMetrics) {
            MetricTypeEnum.METERS.type -> {
                return minPerMetric(
                    1000 / runDistance.toDouble() * totalTime,
                    MetricTypeEnum.KILOMETERS.alias
                )
            }
            MetricTypeEnum.KILOMETERS.type -> {
                return minPerMetric(
                    totalTime / runDistance.toDouble(),
                    MetricTypeEnum.KILOMETERS.alias
                )
            }
            MetricTypeEnum.MILES.type -> {
                return minPerMetric(totalTime / runDistance.toDouble(), MetricTypeEnum.MILES.alias)
            }
        }
        return "Calculating"
    }

    private fun minPerMetric(timeInSec: Double, metrics: String): String {
        return "%s min/%s".format(DateUtils.formatElapsedTime(timeInSec.toLong()), metrics)
    }
}