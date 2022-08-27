package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import android.text.format.DateUtils
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTableData
import sk.upjs.vma.runningpacer.common.enum.CalculatorOptions
import sk.upjs.vma.runningpacer.common.enum.DistanceOptions
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingTableData

val MINPERKM = " min/km"
val SEC = " sec"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VdotScreen(
    viewModel: VdotViewModel = hiltViewModel(),
    dataStoreRace: RaceTableData,
    dataStoreTraining: TrainingTableData
) {
    val parseJson = rememberSaveable { mutableStateOf(true) }
    val showTrainingAndPaceResults = rememberSaveable { mutableStateOf(true) }
    CalculatorOptions.ENTRY.mutableState = rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    if (parseJson.value) {
        viewModel.onEvent(VdotEvent.LoadData(context = context))
        parseJson.value = false
    }

    /**
     * CLEAN APP - FIRST TIME RUN CHECK
     */
    if (dataStoreRace.raceTimes.vdot == 0) {
        showTrainingAndPaceResults.value = false
        CalculatorOptions.ENTRY.mutableState!!.value = true
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Vdot Calculator") },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = CalculatorOptions.ENTRY.type,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )
                    OutlinedIconToggleButton(
                        checked = CalculatorOptions.ENTRY.mutableState!!.value,
                        onCheckedChange = { CalculatorOptions.ENTRY.mutableState!!.value = it }) {
                        if (CalculatorOptions.ENTRY.mutableState!!.value) {
                            Icon(
                                Icons.Outlined.ArrowDropUp,
                                contentDescription = "Up"
                            )
                        } else {
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = "Down"
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Column {
                    AnimatedVisibility(
                        visible = CalculatorOptions.ENTRY.mutableState!!.value,
                        enter = expandVertically()
                    ) {
                        VdotContent(viewModel, CalculatorOptions.ENTRY, showTrainingAndPaceResults)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                AnimatedVisibility(
                    visible = showTrainingAndPaceResults.value,
                    enter = expandVertically()
                ) {
                    VdotShowRaceResultsContent(dataStoreRace, dataStoreTraining)
                }
            }
        })
}


/**
 * Function to create dropdown animation, textFields and provide calculations for later usage
 * (calculating Race Paces and Training Paces
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VdotContent(
    viewModel: VdotViewModel,
    option: CalculatorOptions,
    showTrainingAndPaceContent: MutableState<Boolean>
) {
    val scope = rememberCoroutineScope()
    var expandedOption by remember { mutableStateOf(false) }
    var pickedOption by rememberSaveable { mutableStateOf(DistanceOptions.values()[0].type) }
    var textHHTime by rememberSaveable { mutableStateOf("") }
    var textMMTime by rememberSaveable { mutableStateOf("") }
    var textSSTime by rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expandedOption,
            onExpandedChange = { expandedOption = !expandedOption },
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                value = pickedOption,
                onValueChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DirectionsRun,
                        contentDescription = ""
                    )
                },
                label = { Text("Distance") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedOption) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expandedOption,
                onDismissRequest = { expandedOption = false },
            ) {
                DistanceOptions.values().forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption.type) },
                        onClick = {
                            pickedOption = selectionOption.type
                            expandedOption = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                modifier = Modifier.weight(0.4f),
                value = textHHTime,
                onValueChange = {
                    textHHTime = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = ""
                    )
                },
                label = { Text("Hrs") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedTextField(
                modifier = Modifier.weight(0.3f),
                value = textMMTime,
                onValueChange = {
                    textMMTime = it
                },
                label = { Text("Min") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedTextField(
                modifier = Modifier.weight(0.3f),
                value = textSSTime,
                onValueChange = {
                    textSSTime = it
                },
                label = { Text("Sec") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(onClick = {
            option.mutableState!!.value = !option.mutableState!!.value
            if (textHHTime.isNotEmpty() || textMMTime.isNotEmpty() || textSSTime.isNotEmpty()) {
                showTrainingAndPaceContent.value = true
                scope.launch {
                    viewModel.onEvent(
                        VdotEvent.Calculate(
                            DistanceOptions.values().first { it.type == pickedOption },
                            textSSTime,
                            textMMTime,
                            textHHTime,
                        )
                    )
                }
            }
        }) { Text("Calculate") }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

/**
 * Composable to generate 2 cards - training pace and race pace
 */
@Composable
fun VdotShowRaceResultsContent(
    dataStorePace: RaceTableData,
    dataStoreTraining: TrainingTableData
) {
    Column() {
        /** Start of Training examples Card
         */

        Spacer(modifier = Modifier.height(20.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Training times",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "General",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(text = "VDOT", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Easy", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Marathon", color = MaterialTheme.colorScheme.primary)
                }
                Column() {
                    Text(
                        text = dataStoreTraining.vdotType.vdot.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = dataStoreTraining.easyType.distanceKm + MINPERKM,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = dataStoreTraining.marathonType.distanceKm.getRunningTime() + MINPERKM,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Threshold",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(text = "Km", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "400 m", color = MaterialTheme.colorScheme.primary)
                }
                Column() {
                    Text(
                        text = dataStoreTraining.thresholdType.distanceKm.getRunningTime() + MINPERKM,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = dataStoreTraining.thresholdType.distanceFourm.getRunningTime() + MINPERKM,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Interval",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(text = "400 m", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Km", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "1200 m", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Mile", color = MaterialTheme.colorScheme.primary)
                }
                Column() {
                    Text(
                        text = if (dataStoreTraining.intervalType.distanceFourm == 0) "-" else dataStoreTraining.intervalType.distanceFourm.getRunningTime() + MINPERKM,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if (dataStoreTraining.intervalType.distanceKm == 0) "-" else dataStoreTraining.intervalType.distanceKm.getRunningTime() + MINPERKM,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if (dataStoreTraining.intervalType.distanceOnetwom == 0) "-" else dataStoreTraining.intervalType.distanceOnetwom.getRunningTime() + MINPERKM,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if (dataStoreTraining.intervalType.distanceMile == 0) "-" else dataStoreTraining.intervalType.distanceMile.getRunningTime() + MINPERKM,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Relay",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(text = "200 m", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "300 m", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "400 m", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "600 m", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "800 m", color = MaterialTheme.colorScheme.primary)
                }
                Column() {
                    Text(
                        text = if (dataStoreTraining.relayType.distanceTwom == 0) "-" else dataStoreTraining.relayType.distanceTwom.toString() + SEC,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if (dataStoreTraining.relayType.distanceThreem == 0) "-" else dataStoreTraining.relayType.distanceThreem.toString() + SEC,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if (dataStoreTraining.relayType.distanceFourm == 0) "-" else dataStoreTraining.relayType.distanceFourm.toString() + SEC,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if (dataStoreTraining.relayType.distanceSixm == 0) "-" else dataStoreTraining.relayType.distanceSixm.toString() + SEC,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if (dataStoreTraining.relayType.distanceEightm == 0) "-" else dataStoreTraining.relayType.distanceEightm.toString() + SEC,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(25.dp))
        /** Start of Race examples Card
         */
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Race equivalent",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "Distance", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(10.dp))
                    DistanceOptions.values().forEach {
                        Text(
                            text = it.type,
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "Time", color = MaterialTheme.colorScheme.primary)

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceMarathon.getRunningTime(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceHalfMarathon.getRunningTime(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceFiveteenk.getRunningTime(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceTenk.getRunningTime(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceFivek.getRunningTime(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceTwoMiles.getRunningTime(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceThreek.getRunningTime(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceOnemile.getRunningTime(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceOnefivem.getRunningTime(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )

                }
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "Pace", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceMarathon,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceHalfMarathon,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceFiveteenk,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceTenk,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceFivek,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceTwoMiles,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceThreek,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceOnemile,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceOnefivem,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

private fun Int.getRunningTime(): String {
    return DateUtils.formatElapsedTime(this.toLong())
}


