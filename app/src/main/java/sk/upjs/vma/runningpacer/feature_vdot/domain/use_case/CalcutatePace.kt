package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import android.text.format.DateUtils
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.InvalidTrainingPaceException
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.MetricType

class CalcutatePace() {

    @Throws(InvalidTrainingPaceException::class)
    operator fun invoke(
        runDistance: String,
        runMetrics: String,
        totalTime: Int
    ): String {
        if (runDistance.isEmpty() || runMetrics.isEmpty() || runDistance.toDoubleOrNull() == null) {
            return ""
        }

        when (runMetrics) {
            MetricType.METERS.type -> {
                return minPerMetric(
                    1000 / runDistance.toDouble() * totalTime,
                    MetricType.KILOMETERS.alias
                )
            }
            MetricType.KILOMETERS.type -> {
                return minPerMetric(totalTime / runDistance.toDouble(), MetricType.KILOMETERS.alias)
            }
            MetricType.MILES.type -> {
                return minPerMetric(totalTime / runDistance.toDouble(), MetricType.MILES.alias)
            }

        }
        return "Calculating"
    }

    private fun minPerMetric(timeInSec: Double, metrics: String): String {
        return "%s min/%s".format(DateUtils.formatElapsedTime(timeInSec.toLong()), metrics)
    }
}