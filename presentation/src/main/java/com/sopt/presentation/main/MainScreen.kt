package com.sopt.presentation.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sopt.core.designsystem.component.snackbar.NoostakSnackBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.util.NoRippleInteractionSource
import com.sopt.presentation.appointment.navigation.appointmentNavGraph
import com.sopt.presentation.appointmentCreate.navigation.appointmentCreateNavGraph
import com.sopt.presentation.auth.login.navigation.loginNavGraph
import com.sopt.presentation.auth.signup.checkInvite.navigation.checkInviteNavGraph
import com.sopt.presentation.auth.signup.inputGroupCode.navigation.inputGroupCodeNavGraph
import com.sopt.presentation.auth.signup.navigation.signUpNavGraph
import com.sopt.presentation.calendar.navigation.calendarNavGraph
import com.sopt.presentation.example.navigation.exampleNavGraph
import com.sopt.presentation.group.navigation.groupNavGraph
import com.sopt.presentation.groupCreate.groupCreateSuccess.navigation.groupCreateSuccessNavGraph
import com.sopt.presentation.groupCreate.navigation.groupCreateNavGraph
import com.sopt.presentation.groupDetail.navigation.groupDetailNavGraph
import com.sopt.presentation.mypage.navigation.myPageNavGraph
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator()
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val lifecycleOwner = LocalLifecycleOwner.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = White
        )
    }

    DisposableEffect(key1 = lifecycleOwner) {
        onDispose {
            systemUiController.setStatusBarColor(
                color = Color.Transparent
            )
        }
    }

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 3000) {
            (context as Activity).finish()
        } else {
            backPressedState = true
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = "버튼을 한 번 더 누르면 종료돼요",
                    duration = SnackbarDuration.Short
                )
            }
        }
        backPressedTime = System.currentTimeMillis()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(bottom = 10.dp)
            ) { snackBarData ->
                NoostakSnackBar(message = snackBarData.visuals.message)
            }
        },
        bottomBar = {
            MainBottomBar(
                isVisible = navigator.showBottomBar(),
                tabs = MainTab.entries.toList(),
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NavHost(
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
                popEnterTransition = {
                    EnterTransition.None
                },
                popExitTransition = {
                    ExitTransition.None
                },
                navController = navigator.navController,
                startDestination = navigator.startDestination
            ) {
                exampleNavGraph(navHostController = navigator.navController)
                calendarNavGraph(
                    paddingValues = paddingValues,
                    navHostController = navigator.navController
                )
                groupNavGraph(navHostController = navigator.navController)
                groupCreateNavGraph(
                    paddingValues = paddingValues,
                    navHostController = navigator.navController
                )
                groupCreateSuccessNavGraph(navHostController = navigator.navController)
                groupDetailNavGraph(navHostController = navigator.navController)
                myPageNavGraph(
                    paddingValues = paddingValues,
                    navHostController = navigator.navController
                )
                loginNavGraph(navHostController = navigator.navController)
                signUpNavGraph(navHostController = navigator.navController)
                checkInviteNavGraph(navHostController = navigator.navController)
                inputGroupCodeNavGraph(navHostController = navigator.navController)
                appointmentNavGraph(navHostController = navigator.navController)
                appointmentCreateNavGraph(navHostController = navigator.navController)
            }
        }
    }
}

@Composable
private fun MainBottomBar(
    isVisible: Boolean,
    tabs: List<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit
) {
    AnimatedVisibility(
        visible = isVisible
    ) {
        Column {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = NoostakTheme.colors.gray200
            )
            NavigationBar(
                containerColor = NoostakTheme.colors.white
            ) {
                tabs.forEach { itemType ->
                    NavigationBarItem(
                        interactionSource = NoRippleInteractionSource,
                        selected = currentTab == itemType,
                        onClick = {
                            onTabSelected(itemType)
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = if (currentTab == itemType) {
                                        itemType.selectedIcon
                                    } else {
                                        itemType.unselectedIcon
                                    }
                                ),
                                contentDescription = stringResource(id = itemType.contentDescription),
                                tint = Color.Unspecified
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = itemType.contentDescription),
                                style = NoostakTheme.typography.c4Regular
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = NoostakTheme.colors.gray900,
                            unselectedTextColor = NoostakTheme.colors.gray500,
                            indicatorColor = NoostakTheme.colors.white
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainBottomBarPreview() {
    NoostakAndroidTheme {
        MainBottomBar(
            isVisible = true,
            tabs = MainTab.entries,
            currentTab = MainTab.CALENDAR,
            onTabSelected = {}
        )
    }
}
