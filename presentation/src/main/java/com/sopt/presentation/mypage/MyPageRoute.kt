package com.sopt.presentation.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.BaseButton
import com.sopt.core.designsystem.component.textfield.BaseTextField
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.presentation.R

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateToExample: (String) -> Unit,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(myPageViewModel.sideEffects) {
        myPageViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is MyPageSideEffect.NavigateToExample -> {
                    navigateToExample(sideEffect.text)
                }
            }
        }

    }

    MyPageScreen(
        paddingValues = paddingValues,
        onExampleClick = myPageViewModel::navigateToExample
    )
}

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues = PaddingValues(),
    onExampleClick: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "MyPage Screen")
        BaseTextField(
            value = text,
            onValueChange = { text = it },
            label = "텍스트 입력칸",
            placeholder = "텍스트를 적어주세요",
            isError = false,
            errorMessage = "",
            modifier = Modifier.padding(bottom = 10.dp)
        )
        BaseButton(
            shape = RoundedCornerShape(30.dp),
            style = TextStyle.Default,
            paddingVertical = 9.dp,
            paddingHorizontal = 20.dp,
            text = stringResource(R.string.btn_my_page),
            onButtonClick = { onExampleClick(text) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    NoostakAndroidTheme {
        MyPageScreen(
            onExampleClick = {}
        )
    }
}