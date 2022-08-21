package sk.upjs.vma.runningpacer.common.enum

import androidx.compose.runtime.MutableState

enum class RunDifficultyEnum(val type: String) {
    EASY("Easy"),
    TEMPO("Tempo"),
    THRESHOLD("Threshold"),
    INTERVAL("Interval"),
    LONG("Long")
}

enum class MetricTypeEnum(val type: String, val alias: String) {
    METERS("m", "m"),
    KILOMETERS("km", "km"),
    MILES("Mile", "mile")
}

enum class OrderTypeEnum(val type: String, var mutableState: MutableState<Boolean>?) {
    ASCENDING("Ascending", null),
    DESCENDING("Descending", null)
}

enum class OrderByEnum(val type: String, var mutableState: MutableState<Boolean>?) {
    DISTANCE("Distance", null),
    DATE("Date", null),
    TIME("Time", null)
}