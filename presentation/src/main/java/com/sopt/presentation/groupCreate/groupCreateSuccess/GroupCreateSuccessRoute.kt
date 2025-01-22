package com.sopt.presentation.groupCreate.groupCreateSuccess

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.snackbar.NoostakSnackBar
import com.sopt.core.designsystem.component.snackbar.SNACK_BAR_DURATION
import com.sopt.core.designsystem.component.topappbar.NoostakCloseAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.presentation.R
import com.sopt.presentation.groupCreate.groupCreateSuccess.regex.Regex
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun GroupCreateSuccessRoute(
    groupCreateSuccessViewModel: GroupCreateSuccessViewModel = hiltViewModel(),
    navigateToGroupDetail: (Long) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val snackBarVisible = remember { mutableStateOf(false) }

    val groupCode = Regex().generateRandomCode()

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, groupCode)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    val onShowCopySnackBar: (message: String) -> Unit = {
        coroutineScope.launch {
            snackBarVisible.value = true
            val job = launch { snackBarHostState.showSnackbar(message = it) }
            delay(SNACK_BAR_DURATION)
            job.cancel()
            snackBarVisible.value = false
        }
    }

    LaunchedEffect(lifecycleOwner) {
        groupCreateSuccessViewModel.sideEffects.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is GroupCreateSuccessSideEffect.NavigateToGroupDetail -> navigateToGroupDetail(
                        sideEffect.groupId
                    )

                    is GroupCreateSuccessSideEffect.ShowSnackBar -> onShowCopySnackBar(
                        context.getString(
                            sideEffect.message
                        )
                    )
                }
            }
    }

    GroupCreateSuccessScreen(
        groupCode = groupCode,
        snackBarHostState = snackBarHostState,
        snackBarVisible = snackBarVisible,
        onCloseBtnClick = groupCreateSuccessViewModel::navigateToGroupDetail,
        onCopyBtnClick = {
            coroutineScope.launch {
                groupCreateSuccessViewModel.onCodeCopyBtnClick()
            }
        },
        onSendBtnClick = {
            startActivity(context, shareIntent, null)
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GroupCreateSuccessScreen(
    groupCode: String,
    snackBarHostState: SnackbarHostState,
    snackBarVisible: MutableState<Boolean>,
    onCloseBtnClick: (Long) -> Unit,
    onCopyBtnClick: () -> Unit,
    onSendBtnClick: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            NoostakCloseAppBar(
                modifier = Modifier,
                onBackButtonClick = {
                    onCloseBtnClick(
                        // 임의 id - api 통신에서 변경해야 함
                        0
                    )
                }
            )
        },
        snackbarHost = {
            AnimatedVisibility(
                visible = snackBarVisible.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                SnackbarHost(
                    modifier = Modifier.padding(bottom = 78.dp),
                    hostState = snackBarHostState,
                    snackbar = { snackBarData ->
                        NoostakSnackBar(
                            message = snackBarData.visuals.message,
                            textStyle = NoostakTheme.typography.c3Regular,
                            textColor = NoostakTheme.colors.white,
                            backgroundColor = NoostakTheme.colors.gray800
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_group_create_success),
                    contentDescription = stringResource(R.string.image_group_create_success_description),
                    modifier = Modifier
                        .padding(top = 51.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = stringResource(R.string.text_group_create_success_title),
                    color = NoostakTheme.colors.gray900,
                    style = NoostakTheme.typography.t1SemiBold,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = stringResource(R.string.text_group_create_success_content),
                    color = NoostakTheme.colors.gray900,
                    style = NoostakTheme.typography.b4Regular,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = groupCode,
                    color = NoostakTheme.colors.gray800,
                    style = NoostakTheme.typography.codeMedium,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Text(
                text = stringResource(R.string.text_group_create_success_code_copy),
                color = NoostakTheme.colors.gray800,
                style = NoostakTheme.typography.c3Regular.copy(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .noRippleClickable {
                        clipboardManager.setText(AnnotatedString(groupCode))
                        onCopyBtnClick()
                    }
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
            )
            NoostakBottomButton(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.vertical_padding)),
                text = stringResource(R.string.btn_group_create_success_code_send),
                activateColor = NoostakTheme.colors.blue600,
                deactivateColor = NoostakTheme.colors.gray500,
                isEnabled = true,
                onButtonClick = {
                    onSendBtnClick()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCreateSuccessScreenPreview() {
    NoostakAndroidTheme {
        GroupCreateSuccessScreen(
            groupCode = Regex().generateRandomCode(),
            snackBarHostState = SnackbarHostState(),
            snackBarVisible = remember { mutableStateOf(true) },
            onCloseBtnClick = {},
            onCopyBtnClick = {},
            onSendBtnClick = {}
        )
    }
}
