package sk.upjs.vma.runningpacer.common.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.InvalidTrainingPaceException
import sk.upjs.vma.runningpacer.feature_vdot.presentation.addRun.AddRunScreen
import sk.upjs.vma.runningpacer.feature_vdot.presentation.listOfRuns.TrainingPaceScreen
import java.lang.IllegalStateException

@ExperimentalAnimationApi
@Composable
fun BottomNavGraph(navController: NavHostController) {
    val tweenSpec = tween<IntOffset>(
        durationMillis = 300,
        easing = CubicBezierEasing(0.08f, 0.93f, 0.68f, 1.27f)
    )

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Calculate.route
    ) {
        composable(route = Screen.Calculate.route, enterTransition = {
            when (initialState.destination.route) {
                Screen.Profile.route -> slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tweenSpec
                )
                else -> {
                    slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tweenSpec
                    )
                }
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Profile.route -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tweenSpec
                    )
                    else -> {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tweenSpec
                        )
                    }
                }

            }) {
            TrainingPaceScreen(navController)
        }
        composable(route = Screen.Profile.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.Calculate.route -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tweenSpec
                    )
                    Screen.AddRun.route -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Down,
                        animationSpec = tweenSpec
                    )

                    else -> {
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tweenSpec
                        )
                    }

                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.Calculate.route -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tweenSpec
                    )
                    Screen.AddRun.route -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Up,
                        animationSpec = tweenSpec
                    )
                    else -> {
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tweenSpec
                        )
                    }
                }
            })
        {
            TrainingPaceScreen(navController)
        }
        composable(
            route = Screen.AddRun.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up, animationSpec = tweenSpec
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down, animationSpec = tweenSpec
                )
            }
        ) {
            AddRunScreen(navController)
        }
    }
}