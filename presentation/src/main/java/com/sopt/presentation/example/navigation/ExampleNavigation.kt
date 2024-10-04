package com.sopt.presentation.example.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sopt.core.navigation.Route
import com.sopt.presentation.example.ExampleRoute
import kotlinx.serialization.Serializable

fun NavController.navigateExample(
    text: String,
    navOptions: NavOptions? = null
) {
    navigate(
        route = Example(text = text),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.exampleNavGraph(
    navHostController: NavHostController
) {
    composable<Example> {
        val args = it.toRoute<Example>() // 이전 화면에서 데이터 전달 받기
        ExampleRoute(
            text = args.text,
            navigateUp = navHostController::navigateUp
        )
    }
}

@Serializable
data class Example(
    val text: String
) : Route