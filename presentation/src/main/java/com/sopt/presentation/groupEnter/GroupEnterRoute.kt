package com.sopt.presentation.groupEnter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.dialog.NoostakDialog
import com.sopt.core.designsystem.component.snackbar.NoostakSnackBar
import com.sopt.core.designsystem.component.snackbar.SNACK_BAR_DURATION
import com.sopt.core.designsystem.component.textfield.OtpInputField
import com.sopt.core.designsystem.component.topappbar.NoostakCloseAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.DialogType
import com.sopt.presentation.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GroupEnterRoute(
    navigateToGroup: () -> Unit,
    navigateUp: () -> Unit,
    groupEnterViewModel: GroupEnterViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val showErrorDialog by groupEnterViewModel.showErrorDialog.collectAsStateWithLifecycle()

    var groupCode by remember { mutableStateOf("") }

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val snackBarVisible = remember { mutableStateOf(false) }

    val onShowFailureSnackBar: (message: String) -> Unit = {
        coroutineScope.launch {
            snackBarVisible.value = true
            val job = launch { snackBarHostState.showSnackbar(message = it) }
            delay(SNACK_BAR_DURATION)
            job.cancel()
            snackBarVisible.value = false
        }
    }

    LaunchedEffect(groupEnterViewModel.sideEffects) {
        groupEnterViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is GroupEnterSideEffect.NavigateUp -> navigateUp()
                is GroupEnterSideEffect.NavigateToGroup -> navigateToGroup()
                is GroupEnterSideEffect.ShowErrorDialog -> groupEnterViewModel.showErrorDialog(true)
                is GroupEnterSideEffect.ShowSnackBar -> onShowFailureSnackBar(
                    context.getString(sideEffect.message)
                )
            }
        }
    }

    if (showErrorDialog) {
        NoostakDialog(
            dialogType = DialogType.NETWORK_FAILURE,
            onClick = {
                groupEnterViewModel.postGroupCode(groupCode)
            },
            onDismissRequest = { groupEnterViewModel.showErrorDialog(false) }
        )
    }

    GroupEnterScreen(
        onBackButtonClick = groupEnterViewModel::navigateUp,
        onCheckGroupCodeClick = {
            groupCode = it
            groupEnterViewModel.postGroupCode(it)
        },
        snackBarHostState = snackBarHostState,
        snackBarVisible = snackBarVisible
    )
}

@Composable
fun GroupEnterScreen(
    onBackButtonClick: () -> Unit,
    onCheckGroupCodeClick: (String) -> Unit,
    snackBarHostState: SnackbarHostState,
    snackBarVisible: MutableState<Boolean>
) {
    val focusManager = LocalFocusManager.current
    var groupCode by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        topBar = {
            NoostakCloseAppBar(
                modifier = Modifier,
                onBackButtonClick = {
                    onBackButtonClick()
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
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.bottom_padding_snack_bar_non_exist_code)),
                    hostState = snackBarHostState,
                    snackbar = { snackBarData ->
                        NoostakSnackBar(
                            message = snackBarData.visuals.message,
                            textStyle = NoostakTheme.typography.c3SemiBold,
                            textColor = NoostakTheme.colors.red01,
                            backgroundColor = NoostakTheme.colors.pink
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(id = R.dimen.horizontal_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(147.dp))
            Text(
                text = stringResource(R.string.tv_group_enter_description_1),
                color = NoostakTheme.colors.gray900,
                style = NoostakTheme.typography.t1SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.tv_group_enter_description_2),
                color = NoostakTheme.colors.gray900,
                style = NoostakTheme.typography.t1SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            OtpInputField(
                otpText = groupCode,
                onOtpTextChange = { otp, isComplete ->
                    groupCode = otp
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            NoostakBottomButton(
                text = stringResource(R.string.btn_group_enter_confirm),
                isEnabled = groupCode.length == 6,
                onButtonClick = { onCheckGroupCodeClick(groupCode) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupEnterPreview() {
    NoostakAndroidTheme {
        GroupEnterScreen(
            onBackButtonClick = {},
            onCheckGroupCodeClick = {},
            snackBarHostState = SnackbarHostState(),
            snackBarVisible = remember { mutableStateOf(true) }
        )
    }
}
