package sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.coroutines.launch
import sk.upjs.vma.runningpacer.common.enum.OrderByEnum
import sk.upjs.vma.runningpacer.common.enum.OrderTypeEnum
import sk.upjs.vma.runningpacer.common.presentation.Screen
import sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns.components.SortDialog
import sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns.components.TrainingPaceItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingPaceScreen(
    navController: NavController,
    viewModel: TrainingPacesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    remember { SnackbarHostState() }

    OrderByEnum.values().forEach {
        if (it == OrderByEnum.DATE) {
            it.mutableState = rememberSaveable { mutableStateOf(true) }
        } else {
            it.mutableState = rememberSaveable { mutableStateOf(false) }
        }
    }
    OrderTypeEnum.values().forEach {
        if (it == OrderTypeEnum.DESCENDING) {
            it.mutableState = rememberSaveable { mutableStateOf(true) }
        } else {
            it.mutableState = rememberSaveable { mutableStateOf(false) }
        }
    }

    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        SortDialog(openDialog, viewModel)
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Times & Paces") },
                actions = {
                    IconButton(onClick = {
                        openDialog.value = true
                    }) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                    }

                }
            )

        },
        content = { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                items(state.trainingPaces) { trainingPace ->
                    TrainingPaceItem(
                        trainingPace = trainingPace,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                onClick = {
                    navController.navigate(Screen.AddRun.route)
                }
            ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add") }
        }
    )

}