package sk.upjs.vma.runningpacer.common.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import sk.upjs.vma.runningpacer.common.presentation.BottomNavGraph
import sk.upjs.vma.runningpacer.common.presentation.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                BottomNavGraph(navController = navController)
            }
        }
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Calculate,
        Screen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

            )
        }
    }
}













