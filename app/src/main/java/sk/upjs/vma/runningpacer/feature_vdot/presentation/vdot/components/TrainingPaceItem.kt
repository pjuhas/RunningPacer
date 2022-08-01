package sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingPace

@Composable
fun TrainingPaceItem(
    trainingPace: TrainingPace,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Column {
                Text(
                    text = trainingPace.timestamp.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                TrainingPace.distances.subList(0, TrainingPace.distances.size / 2 + 1).forEach {
                    Text(
                        text = it.toString() + "m | ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
            Column {
                Spacer(modifier = Modifier.height(26.dp))
                TrainingPace.distances.subList(
                    TrainingPace.distances.size / 2 + 1,
                    TrainingPace.distances.size
                ).forEach {
                    Text(
                        text = it.toString() + "m | ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}