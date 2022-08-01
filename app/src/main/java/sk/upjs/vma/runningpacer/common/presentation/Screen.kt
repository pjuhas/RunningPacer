package sk.upjs.vma.runningpacer.common.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String,
                    val title: String,
                    val icon: ImageVector
) {
    object Vdot : Screen(
        route = "calculate",
        title = "Calculate",
        icon = Icons.Default.Calculate
    )

    object Profile : Screen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

}
