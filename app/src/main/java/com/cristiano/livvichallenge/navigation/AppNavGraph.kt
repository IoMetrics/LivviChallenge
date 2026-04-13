package com.cristiano.livvichallenge.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cristiano.livvichallenge.presentation.auth.signin.SignInEmailScreen
import com.cristiano.livvichallenge.presentation.auth.signin.SignInPasswordScreen
import com.cristiano.livvichallenge.presentation.auth.signin.SignInViewModel
import com.cristiano.livvichallenge.presentation.auth.signup.SignUpScreen
import com.cristiano.livvichallenge.presentation.auth.signup.SignUpViewModel
import com.cristiano.livvichallenge.presentation.doors.DoorsScreen
import com.cristiano.livvichallenge.presentation.doors.DoorsViewModel
import com.cristiano.livvichallenge.presentation.doors.events.DoorEventsScreen
import com.cristiano.livvichallenge.presentation.doors.events.DoorEventsViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LoginEmail.route
    ) {
        composable(Routes.LoginEmail.route) {
            SignInEmailScreen(
                onContinue = { email ->
                    navController.navigate(Routes.LoginPassword.createRoute(email))
                },
                onNavigateToSignUp = {
                    navController.navigate(Routes.SignUp.route)
                }
            )
        }

        composable(
            route = Routes.LoginPassword.route,
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val signInViewModel: SignInViewModel = hiltViewModel()

            val encodedEmail = backStackEntry.arguments?.getString("email").orEmpty()
            val email = URLDecoder.decode(
                encodedEmail,
                StandardCharsets.UTF_8.toString()
            )

            SignInPasswordScreen(
                email = email,
                viewModel = signInViewModel,
                onEditEmail = {
                    navController.popBackStack()
                },
                onSignInSuccess = {
                    navController.navigate(Routes.Doors.route) {
                        popUpTo(Routes.LoginEmail.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.SignUp.route) {
            val signUpViewModel: SignUpViewModel = hiltViewModel()

            SignUpScreen(
                viewModel = signUpViewModel,
                onBackToSignIn = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.Doors.route) {
            val doorsViewModel: DoorsViewModel = hiltViewModel()

            DoorsScreen(
                viewModel = doorsViewModel,
                onDoorClick = { doorId ->
                    navController.navigate(Routes.DoorEvents.createRoute(doorId))
                },
                onLogout = {
                    navController.navigate(Routes.LoginEmail.route) {
                        popUpTo(Routes.Doors.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onSessionExpired = {
                    navController.navigate(Routes.LoginEmail.route) {
                        popUpTo(Routes.Doors.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Routes.DoorEvents.route,
            arguments = listOf(
                navArgument("doorId") {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: DoorEventsViewModel = hiltViewModel()

            DoorEventsScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                },
                onSessionExpired = {
                    navController.navigate(Routes.LoginEmail.route) {
                        popUpTo(Routes.Doors.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}