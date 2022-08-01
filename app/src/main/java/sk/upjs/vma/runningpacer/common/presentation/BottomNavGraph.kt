package sk.upjs.vma.runningpacer.common.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot.TrainingPaceScreen

@ExperimentalAnimationApi
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Vdot.route
    ) {
        composable(route = Screen.Vdot.route) {
            TrainingPaceScreen()
        }
        composable(route = Screen.Profile.route) {
            TrainingPaceScreen()
        }
    }
}