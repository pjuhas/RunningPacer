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
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.DistanceSerializer
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TableData
import sk.upjs.vma.runningpacer.common.presentation.components.MainScreen
import sk.upjs.vma.runningpacer.ui.theme.RunningPacerTheme


val Context.dataStore by dataStore("runningPace.json", DistanceSerializer)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RunningPacerTheme {
                val dataStorePace = dataStore.data.collectAsState(
                    initial = TableData()
                ).value
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(dataStorePace)
                }
            }
        }
    }
}