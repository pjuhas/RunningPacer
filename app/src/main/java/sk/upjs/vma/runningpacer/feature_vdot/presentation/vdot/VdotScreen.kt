package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TableData
import sk.upjs.vma.runningpacer.common.enum.CalculatorOptions
import sk.upjs.vma.runningpacer.common.enum.DistanceOptions


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun VdotScreen(
    navController: NavController,
    viewModel: VdotViewModel = hiltViewModel(),
    dataStorePace: TableData
) {
    val showResults = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    viewModel.onEvent(VdotEvent.LoadData(context = context))

    CalculatorOptions.values().forEach {
        if (it == CalculatorOptions.TRAINING) {
            it.mutableState = rememberSaveable { mutableStateOf(true) }
        } else {
            it.mutableState = rememberSaveable { mutableStateOf(false) }
        }
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
                CalculatorOptions.values().forEach { option ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = option.type, style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis
                        )
                        OutlinedIconToggleButton(
                            checked = option.mutableState!!.value,
                            onCheckedChange = { option.mutableState!!.value = it }) {
                            if (option.mutableState!!.value) {
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
                    Spacer(modifier = Modifier.height(30.dp))
                    Column {
                        AnimatedVisibility(
                            visible = option.mutableState!!.value,
                            enter = expandVertically()
                        ) {
                            VdotContent(viewModel, option, showResults, dataStorePace)
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                AnimatedVisibility(
                    visible = true,
                    enter = expandVertically()
                ) {
                    VdotShowResultsContent(viewModel, dataStorePace)
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VdotContent(
    viewModel: VdotViewModel,
    option: CalculatorOptions,
    showResults: MutableState<Boolean>,
    dataStorePace: TableData
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
                label = { Text("Hours") },
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
                label = { Text("Minutes") },
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
                label = { Text("Seconds") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(onClick = {
            option.mutableState!!.value = !option.mutableState!!.value
            showResults.value = true
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

        }) { Text("Calculate") }
        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Composable
fun VdotShowResultsContent(
    viewModel: VdotViewModel,
    dataStorePace: TableData
) {

    Column() {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(25.dp))

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
                        text = dataStorePace.raceTimes.distanceMarathon.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceHalfMarathon.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceFiveteenk.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceTenk.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceFivek.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceTwoMiles.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceThreek.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceOnemile.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.raceTimes.distanceOnefivem.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )

                }
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "Pace", color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceMarathon.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceHalfMarathon.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceFiveteenk.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceTenk.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceFivek.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceTwoMiles.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceThreek.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceOnemile.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dataStorePace.racePace.distanceOnefivem.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }
}


