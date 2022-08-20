package sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
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
import kotlinx.coroutines.flow.collectLatest
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddRunScreen(
    navController: NavController,
    viewModel: AddRunViewModel = hiltViewModel(),

) {

    val scope = rememberCoroutineScope()

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
                is AddRunViewModel.UiEvent.ShowSnackbar -> {
                    // TODO()
                    //scaffoldState.snackbarHostState.showSnackbar(
                    //   message = event.message
                    // )
                }
                is AddRunViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxHeight(0.9f),
        topBar = {
            SmallTopAppBar(
                title = { Text("Add your run") }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding().fillMaxSize()
                    .padding(20.dp)
            ) {
                Row {
                    Text(
                        text = "Difficulty:",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedDifficulty,
                        onExpandedChange = { expandedDifficulty = !expandedDifficulty },
                    ) {
                        viewModel.onEvent(AddRunEvent.RunDifficulty(pickedDifficulty))
                        TextField(
                            readOnly = true,
                            value = pickedDifficulty,
                            onValueChange = {},
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
                                        viewModel.onEvent(AddRunEvent.RunDifficulty(selectionOption))
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Row {
                    Text(
                        text = "Distance:",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    TextField(
                        modifier = Modifier.weight(0.5f) ,
                        value = textDistance,
                        onValueChange = {
                            textDistance = it
                            viewModel.onEvent(AddRunEvent.RunDistance(textDistance.replace(",", ".")))
                            textPace = viewModel.runPace.value.text
                        },
                        placeholder = { Text("Fill") },
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
                        TextField(
                            readOnly = true,
                            singleLine = true,
                            value = pickedDistance,
                            onValueChange = {viewModel.onEvent(AddRunEvent.PickedDistance(pickedDistance))},
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
                                        viewModel.onEvent(AddRunEvent.PickedDistance(selectionOption))
                                        textPace = viewModel.runPace.value.text
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Row {
                    Text(
                        text = "Time:",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    TextField(
                        modifier = Modifier.weight(0.33f),
                        value = textHHTime,
                        onValueChange = {
                            textHHTime = it
                            viewModel.onEvent(AddRunEvent.RunTimeHH(textHHTime))
                            textPace = viewModel.runPace.value.text
                            editable = true
                        },
                        placeholder = { Text("hh") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextField(
                        modifier = Modifier.weight(0.33f),
                        value = textMMTime,
                        onValueChange = {
                            textMMTime = it
                            viewModel.onEvent(AddRunEvent.RunTimeMM(textMMTime))
                            textPace = viewModel.runPace.value.text
                            editable = true
                        },
                        placeholder = { Text("mm") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextField(
                        modifier = Modifier.weight(0.33f),
                        value = textSSTime,
                        onValueChange = {
                            textSSTime = it
                            viewModel.onEvent(AddRunEvent.RunTimeSS(textSSTime))
                            textPace = viewModel.runPace.value.text
                            editable = true
                        },
                        placeholder = { Text("ss") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))

                AnimatedVisibility(visible = editable,
                enter = scaleIn()) {
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
                    viewModel.onEvent(AddRunEvent.SaveNote)
                }
            ) { Icon(imageVector = Icons.Default.Save, contentDescription = "Save") }
        })
}
