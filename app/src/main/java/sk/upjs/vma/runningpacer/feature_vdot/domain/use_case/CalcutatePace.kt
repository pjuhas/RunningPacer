package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import android.text.format.DateUtils
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.InvalidTrainingPaceException
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace

class CalcutatePace() {

    @Throws(InvalidTrainingPaceException::class)
    operator fun invoke(runDistance: String, runMetrics: String, runTimeSS: String, runTimeMM: String, runTimeHH: String) : String {
        var totalTime = 0
        if (runDistance.isEmpty() || runMetrics.isEmpty()){
            return ""
        }
        totalTime += runTimeSS.toIntOrNull()?:0
        totalTime += (runTimeMM.toIntOrNull() ?:0) * 60
        totalTime += (runTimeHH.toIntOrNull()?:0) * 60 * 60

        when (runMetrics) {
            "m" -> {
                return minPerMetric(1000 / runDistance.toDouble() * totalTime, "km")
            }
            "km" -> {
                return minPerMetric(totalTime / runDistance.toDouble(), "km")
            }
            "mile" -> {
                return minPerMetric(totalTime / runDistance.toDouble(), "mile")
            }

        }
        return "Calculating"
    }

    private fun minPerMetric(timeInSec: Double, metrics: String): String {
        return "%s min/%s".format(DateUtils.formatElapsedTime(timeInSec.toLong()), metrics)
    }
}