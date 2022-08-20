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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import sk.upjs.vma.runningpacer.common.presentation.Screen
import sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns.components.TrainingPaceItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingPaceScreen(
    navController: NavController,
    viewModel: TrainingPacesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberTopAppBarState()
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.fillMaxHeight(0.9f),
        topBar = {
            SmallTopAppBar(
                title = { Text("Times & Paces") },
                //scrollBehavior = ,
                actions = {
                    IconButton(onClick = {
                        /*TODO*/
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                onClick = {
                    navController.navigate(Screen.addRun.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add") }
        }
    )

}