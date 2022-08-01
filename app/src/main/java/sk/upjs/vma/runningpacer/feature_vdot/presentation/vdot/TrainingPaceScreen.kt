package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot.components.TrainingPaceItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingPaceScreen(
    viewModel: TrainingPacesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.fillMaxHeight(0.9f),
        topBar = {
            SmallTopAppBar(
                title = { Text("Times & Paces") },
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
                            .clickable { })
                }
            }
        }
    )

}