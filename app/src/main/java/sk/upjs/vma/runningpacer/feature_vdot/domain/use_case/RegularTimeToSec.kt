package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

class RegularTimeToSec {
    operator fun invoke(runTimeSS: String,
                        runTimeMM: String,
                        runTimeHH: String
    ): Int {
        return (runTimeSS.toIntOrNull() ?: 0) + (runTimeMM.toIntOrNull()
            ?: 0) * 60 + (runTimeHH.toIntOrNull() ?: 0) * 60 * 60
    }
}
