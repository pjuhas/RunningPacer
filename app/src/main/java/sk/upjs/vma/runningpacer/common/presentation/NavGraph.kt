package sk.upjs.vma.runningpacer.common.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTableData
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingTableData
import sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun.AddRunScreen
import sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns.TrainingPaceScreen
import sk.upjs.vma.runningpacer.feature_vdot.presentation.vdot.VdotScreen

@ExperimentalAnimationApi
@Composable
fun BottomNavGraph(navController: NavHostController, dataStoreRace: RaceTableData, dataStoreTraining: TrainingTableData) {
    val tweenSpec = tween<IntOffset>(
        durationMillis = 300
    )

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Profile.route
    ) {
        composable(route = Screen.Profile.route, enterTransition = {
            when (initialState.destination.route) {
                Screen.Calculate.route -> slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tweenSpec
                )
                else -> {
                    null
                }
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Calculate.route -> slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tweenSpec
                    )
                    else -> {
                        null
                    }
                }
            }) {
            TrainingPaceScreen(navController)
        }
        composable(route = Screen.Calculate.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Profile.route -> slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tweenSpec
                    )
                    else -> {
                        null
                    }
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Profile.route -> slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tweenSpec
                    )
                    else -> {
                        null
                    }
                }
            })
        {
            VdotScreen(dataStoreRace = dataStoreRace, dataStoreTraining = dataStoreTraining)
        }
        composable(route = Screen.AddRun.route) {
            AddRunScreen(navController)
        }
    }
}