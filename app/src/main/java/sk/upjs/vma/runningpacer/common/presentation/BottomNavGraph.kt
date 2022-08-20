package sk.upjs.vma.runningpacer.common.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun.AddRunScreen
import sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns.TrainingPaceScreen

@ExperimentalAnimationApi
@Composable
fun BottomNavGraph(navController: NavHostController) {
    val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
    val tweenSpec = tween<IntOffset>(durationMillis = 2000, easing = CubicBezierEasing(0.08f,0.93f,0.68f,1.27f))

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Vdot.route
    ) {
        composable(route = Screen.Vdot.route) {
            TrainingPaceScreen(navController)
        }
        composable(route = Screen.Profile.route,
            enterTransition = { initial, _ ->
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
            },
            exitTransition = { _, target ->
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
            },
            popEnterTransition = { initial, _ ->
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = springSpec)
            },
            popExitTransition = { _, target ->
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = springSpec)
            }) {
            TrainingPaceScreen(navController)
        }
        composable(route = Screen.addRun.route){
            AddRunScreen(navController)
        }
    }
}