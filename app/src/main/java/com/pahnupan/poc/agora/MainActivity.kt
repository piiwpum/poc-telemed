package com.pahnupan.poc.agora

import HomeRoute
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.pahnupan.poc.agora.composable.theme.POCAgoraTheme
import com.pahnupan.poc.agora.presentation.list.ListRoute
import com.pahnupan.poc.agora.presentation.login.LoginRoute
import com.pahnupan.poc.agora.presentation.login.LoginUiState
import com.pahnupan.poc.agora.presentation.video.TelemedRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable


@Serializable
data object NavigateLogin

@Serializable
data object NavigateHomeScreen

@Serializable
data object NavigateListScreen

@Serializable
data class NavigateTelemedScreen(val isHost: Boolean, val chanelName: String)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            POCAgoraTheme {
                Surface(color = Color.Red) {
                    NavHost(
                        navController = navController,
                        startDestination = if (viewModel.sharePref.token != null) {
                            if (viewModel.sharePref.role == LoginUiState.Role.PATIENT.name) {
                                NavigateHomeScreen
                            } else {
                                NavigateListScreen
                            }
                        } else {
                            NavigateLogin
                        }
                    ) {

                        composable<NavigateLogin> {
                            LoginRoute(
                                viewModel = hiltViewModel(),
                                navigateToLanding = {
                                    if (it == LoginUiState.Role.PATIENT) {
                                        navController.navigate(NavigateHomeScreen)
                                    } else {
                                        navController.navigate(NavigateListScreen)
                                    }
                                }
                            )
                        }
                        composable<NavigateHomeScreen> {
                            HomeRoute(
                                viewModel = hiltViewModel(),
                            )
                        }

                        composable<NavigateListScreen> {
                            ListRoute(
                                viewModel = hiltViewModel(),
                            )
                        }
                        composable<NavigateTelemedScreen> {
                            TelemedRoute(
                                viewModel = hiltViewModel(),
                                navController = navController,
                                bundle = it.toRoute<NavigateTelemedScreen>()
                            )
                        }
                    }
                }
            }
        }
    }
}
