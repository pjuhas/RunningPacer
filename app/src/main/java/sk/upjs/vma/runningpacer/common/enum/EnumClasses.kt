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
    METERS("Meters", "m"),
    KILOMETERS("Kilometers", "km"),
    MILES("Miles", "mile")
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

enum class CalculatorOptions(val type: String, var mutableState: MutableState<Boolean>?) {
    TRAINING("Training", null),
    RACE("Race", null)
}

enum class DistanceOptions(val type: String) {
    MARATHON("Marathon"),
    HALF_MARATHON("Half marathon"),
    FIFTEENK("15K"),
    TENK("10K"),
    FIVEK("5K"),
    TWOMILES("2Mi"),
    THREEK("3K"),
    ONEMILE("1Mi"),
    ONEFIVEM("1500M")
}