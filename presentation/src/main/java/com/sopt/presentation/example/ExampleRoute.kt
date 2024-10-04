package com.sopt.presentation.example

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.core.designsystem.component.appbar.BaseTopAppBar
import com.sopt.core.designsystem.component.button.BaseButton
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.extension.toast
import com.sopt.core.state.UiState
import com.sopt.domain.entity.ExampleEntity
import com.sopt.presentation.R

@Composable
fun ExampleRoute(
    text: String,
    navigateUp: () -> Unit,
    exampleViewModel: ExampleViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by exampleViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        exampleViewModel.getFollowers(2)
    }

    LaunchedEffect(exampleViewModel.sideEffects) {
        exampleViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is ExampleSideEffect.ShowToast -> {
                    context.toast(sideEffect.message)
                }

                is ExampleSideEffect.NavigateUp -> navigateUp()
            }
        }
    }

    when (state.followers) {
        is UiState.Empty -> {
            // Show empty
        }

        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )
            }
        }

        is UiState.Success -> {
            ExampleScreen(
                text = text,
                followers = (state.followers as UiState.Success<List<ExampleEntity>>).data,
                onBackButtonClick = exampleViewModel::navigateUp
            )
        }

        is UiState.Failure -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "서버 연결에 실패했어요 ㅠ"
                )
            }
        }
    }
}

@Composable
fun ExampleScreen(
    text: String = "",
    followers: List<ExampleEntity>,
    onBackButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BaseTopAppBar(
            title = stringResource(R.string.appbar_example_title),
            modifier = Modifier.fillMaxWidth(),
            onBackButtonClick = { onBackButtonClick() },
        )
        if (text.isNotBlank()) {
            Text(
                text = text,
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(followers) { follower ->
                ExampleItem(follower)
            }
        }
        BaseButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(30.dp),
            style = TextStyle.Default,
            paddingVertical = 10.dp,
            text = stringResource(R.string.btn_example_back),
            onButtonClick = { onBackButtonClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExampleRoutePreview() {
    NoostakAndroidTheme {
        ExampleScreen(
            followers = emptyList(),
            onBackButtonClick = {}
        )
    }
}