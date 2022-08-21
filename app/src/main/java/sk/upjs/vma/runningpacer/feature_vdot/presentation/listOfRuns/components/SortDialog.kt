package sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import sk.upjs.vma.runningpacer.common.enum.OrderByEnum
import sk.upjs.vma.runningpacer.common.enum.OrderTypeEnum
import sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns.TrainingPacesEvent
import sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns.TrainingPacesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDialog(
    openDialog: MutableState<Boolean>,
    viewModel: TrainingPacesViewModel,
) {
    Dialog(
        onDismissRequest = { openDialog.value = false },
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.inverseOnSurface
        ) {
            Box(modifier = Modifier) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sort items",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OrderByEnum.values().forEach {
                            ElevatedFilterChip(
                                selected = it.mutableState!!.value,
                                onClick = {
                                    it.mutableState!!.value = !it.mutableState!!.value
                                    OrderByEnum.values().map { otherIt ->
                                        if (otherIt != it) otherIt.mutableState!!.value = false
                                    }
                                },
                                leadingIcon = if (it.mutableState!!.value) {
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = "Localized Description",
                                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                                        )
                                    }
                                } else {
                                    null
                                },
                                label = { Text(it.type) },
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OrderTypeEnum.values().forEach {
                            ElevatedFilterChip(
                                selected = it.mutableState!!.value,
                                onClick = {
                                    it.mutableState!!.value = !it.mutableState!!.value
                                    OrderTypeEnum.values().map { otherIt ->
                                        if (otherIt != it) otherIt.mutableState!!.value = false
                                    }
                                },
                                leadingIcon = if (it.mutableState!!.value) {
                                    {
                                        Icon(
                                            imageVector = Icons.Filled.Done,
                                            contentDescription = "Localized Description",
                                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                                        )
                                    }
                                } else {
                                    null
                                },
                                label = { Text(it.type) },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { openDialog.value = false }) { Text("Cancel") }
                        TextButton(onClick = {
                            val type = OrderTypeEnum.values().firstOrNull { it.mutableState!!.value }
                            val order = OrderByEnum.values().firstOrNull { it.mutableState!!.value }

                            if (type != null && order != null) {
                                viewModel.onEvent(TrainingPacesEvent.Order(type, order))
                            }
                            openDialog.value = false
                        }) {
                            Text("Sort")
                        }
                    }
                }
            }
        }
    }
}

