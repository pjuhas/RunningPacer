package sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sk.upjs.vma.runningpacer.common.presentation.Screen
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddRunScreen(
    navController: NavController,
    viewModel: AddRunViewModel = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()
    val snackHostState = remember { SnackbarHostState() }

    var textDistance by rememberSaveable { mutableStateOf("") }
    var textHHTime by rememberSaveable { mutableStateOf("") }
    var textMMTime by rememberSaveable { mutableStateOf("") }
    var textSSTime by rememberSaveable { mutableStateOf("") }
    var textPace by rememberSaveable { mutableStateOf("") }

    var pickedDistance by rememberSaveable { mutableStateOf(TrainingPace.metricType[0]) }
    var expandedDistance by remember { mutableStateOf(false) }

    var pickedDifficulty by rememberSaveable { mutableStateOf(TrainingPace.difficulties[0]) }
    var expandedDifficulty by remember { mutableStateOf(false) }

    var editable by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddRunViewModel.UiEvent.ShowSnack -> {
                    scope.launch {
                        snackHostState.showSnackbar(event.message)
                    }
                }
                is AddRunViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Add your run") }
            )
        },
        snackbarHost = { SnackbarHost(snackHostState, modifier = Modifier.padding(9.dp)) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                Row {
                    ExposedDropdownMenuBox(
                        expanded = expandedDifficulty,
                        onExpandedChange = { expandedDifficulty = !expandedDifficulty },
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            value = pickedDifficulty,
                            onValueChange = {},
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DirectionsRun,
                                    contentDescription = ""
                                )
                            },
                            label = { Text("Difficulty") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDifficulty) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        )
                        ExposedDropdownMenu(
                            expanded = expandedDifficulty,
                            onDismissRequest = { expandedDifficulty = false },
                        ) {
                            TrainingPace.difficulties.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        pickedDifficulty = selectionOption
                                        expandedDifficulty = false
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedTextField(
                        modifier = Modifier.weight(0.5f),
                        value = textDistance,
                        onValueChange = {
                            textDistance = it
                            viewModel.onEvent(
                                AddRunEvent.RunDistance(
                                    textDistance.replace(
                                        ",",
                                        "."
                                    )
                                )
                            )
                            textPace = viewModel.runPace.value
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AddRoad,
                                contentDescription = ""
                            )
                        },
                        label = { Text("Distance") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    ExposedDropdownMenuBox(
                        modifier = Modifier.weight(0.5f),
                        expanded = expandedDistance,
                        onExpandedChange = { expandedDistance = !expandedDistance },
                    ) {
                        viewModel.onEvent(AddRunEvent.PickedDistance(pickedDistance))
                        OutlinedTextField(
                            readOnly = true,
                            singleLine = true,
                            value = pickedDistance,
                            onValueChange = {
                                viewModel.onEvent(
                                    AddRunEvent.PickedDistance(
                                        pickedDistance
                                    )
                                )
                            },
                            label = { Text("Metrics") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDistance) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        )
                        ExposedDropdownMenu(
                            expanded = expandedDistance,
                            onDismissRequest = { expandedDistance = false },
                        ) {
                            TrainingPace.metricType.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        pickedDistance = selectionOption
                                        expandedDistance = false
                                        viewModel.onEvent(
                                            AddRunEvent.PickedDistance(
                                                selectionOption
                                            )
                                        )
                                        textPace = viewModel.runPace.value
                                    }
                                )
                            }
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
                            viewModel.onEvent(AddRunEvent.RunTimeHH(textHHTime))
                            textPace = viewModel.runPace.value
                            editable = true
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
                            viewModel.onEvent(AddRunEvent.RunTimeMM(textMMTime))
                            textPace = viewModel.runPace.value
                            editable = true
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
                            viewModel.onEvent(AddRunEvent.RunTimeSS(textSSTime))
                            textPace = viewModel.runPace.value
                            editable = true
                        },
                        label = { Text("Sec") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))

                AnimatedVisibility(
                    visible = editable,
                    enter = scaleIn()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Calculated pace:",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = textPace,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                onClick = {
                    viewModel.onEvent(AddRunEvent.RunDifficulty(pickedDifficulty))
                    viewModel.onEvent(AddRunEvent.SaveNote)
                }
            ) { Icon(imageVector = Icons.Default.Save, contentDescription = "Save") }
        })
}

