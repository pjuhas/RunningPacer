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
    ENTRY("Calculator", null)
}

enum class DistanceOptions(val type: String, val length: Double) {
    MARATHON("Marathon", 42.195),
    HALF_MARATHON("Half marathon", 21.0975),
    FIFTEENK("15K", 15.0),
    TENK("10K", 10.0),
    FIVEK("5K", 5.0),
    TWOMILES("2Mi", 3.218688),
    THREEK("3K", 3.0),
    ONEMILE("1Mi", 1.609344),
    ONEFIVEM("1500M", 1.5)
}