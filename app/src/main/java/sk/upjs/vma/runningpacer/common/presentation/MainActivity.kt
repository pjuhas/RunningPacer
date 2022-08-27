package sk.upjs.vma.runningpacer.common.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.datastore.dataStore
import dagger.hilt.android.AndroidEntryPoint
import sk.upjs.vma.runningpacer.common.RaceSerializer
import sk.upjs.vma.runningpacer.common.TrainingSerializer
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTableData
import sk.upjs.vma.runningpacer.common.presentation.components.MainScreen
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingTableData
import sk.upjs.vma.runningpacer.ui.theme.RunningPacerTheme


val Context.dataStoreRace by dataStore("runningPace.json", RaceSerializer)
val Context.dataStoreTraining by dataStore("trainingPace.json", TrainingSerializer)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RunningPacerTheme {
                val dataStoreRace = dataStoreRace.data.collectAsState(
                    initial = RaceTableData()
                ).value
                val dataStoreTraining = dataStoreTraining.data.collectAsState(
                    initial = TrainingTableData()
                ).value

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(dataStoreRace, dataStoreTraining)
                }
            }
        }
    }
}