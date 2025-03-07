package com.sopt.presentation.auth.signup

import android.Manifest
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.image.ProfileImagePicker
import com.sopt.core.designsystem.component.snackbar.NoostakSnackBar
import com.sopt.core.designsystem.component.snackbar.SNACK_BAR_DURATION
import com.sopt.core.designsystem.component.textfield.NoostakTextField
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.launchImagePicker
import com.sopt.core.type.TextFieldType
import com.sopt.core.util.permission.ImagePickerLaunchers
import com.sopt.presentation.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun SignUpRoute(
    authId: String,
    navigateToCheckInvite: (String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val signUpState by viewModel.signUpState.collectAsStateWithLifecycle()

    var isGalleryPermission by remember { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val snackBarVisible = remember { mutableStateOf(false) }

    val onShowGalleryPermissionSnackBar: (message: String) -> Unit = {
        coroutineScope.launch {
            snackBarVisible.value = true
            val job = launch { snackBarHostState.showSnackbar(message = it) }
            delay(SNACK_BAR_DURATION)
            job.cancel()
            snackBarVisible.value = false
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        try {
            if (isGranted) {
                viewModel.updateGalleryPermissionState(true)
            } else {
                isGalleryPermission = true
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    if (isGalleryPermission) {
        onShowGalleryPermissionSnackBar(context.getString(R.string.sb_permission_gallery))
        isGalleryPermission = false
    }

    val galleryLauncher = ImagePickerLaunchers().rememberGalleryLauncher { uri ->
        viewModel.updateProfileImage(uri.toString())
    }

    val photoPickerLauncher = ImagePickerLaunchers().rememberPhotoPickerLauncher { uri ->
        viewModel.updateProfileImage(uri.toString())
    }

    LaunchedEffect(lifecycleOwner) {
        viewModel.sideEffects.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SignUpSideEffect.NavigateToCheckInvite -> navigateToCheckInvite(sideEffect.name)

                    is SignUpSideEffect.ShowSnackBar -> isGalleryPermission = true

                    is SignUpSideEffect.RequestImagePicker -> context.launchImagePicker(
                        galleryLauncher,
                        photoPickerLauncher
                    )
                }
            }
    }

    AnimatedVisibility(
        visible = snackBarVisible.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SnackbarHost(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 96.dp),
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

    SignUpScreen(
        signUpState = signUpState,
        onProfileSettingBtnClick = {
            handleProfileBtnClick(viewModel, permissionLauncher)
        },
        onNameChange = { viewModel.onNicknameChanged(it) },
        onSignUpClick = { viewModel.navigateToCheckInvite() }
    )
}

fun handleProfileBtnClick(
    viewModel: SignUpViewModel,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    if (viewModel.isGalleryPermissionGranted()) {
        viewModel.requestGalleryPicker()
    } else {
        permissionLauncher.launch(permission)
    }
}

@Composable
fun SignUpScreen(
    signUpState: SignUpState,
    onProfileSettingBtnClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.horizontal_padding))
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Text(
            text = stringResource(R.string.tv_signup_profile),
            color = NoostakTheme.colors.black,
            style = NoostakTheme.typography.h2Bold,
            modifier = Modifier.padding(top = 70.dp)
        )
        ProfileImagePicker(
            selectedImageUri = signUpState.profileImageUri,
            onCameraBtnClick = onProfileSettingBtnClick,
            modifier = Modifier
                .padding(top = 46.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(27.dp))
        NoostakTextField(
            textFieldType = TextFieldType.SIGNUP,
            value = signUpState.nickname,
            maxLength = 10,
            onValueChange = onNameChange
        )
        Spacer(modifier = Modifier.weight(1f))
        NoostakBottomButton(
            text = stringResource(R.string.btn_next),
            isEnabled = signUpState.isNameCheck,
            onButtonClick = onSignUpClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    NoostakAndroidTheme {
        SignUpScreen(
            signUpState = SignUpState(
                nickname = stringResource(R.string.app_name),
                profileImageUri = null
            ),
            onProfileSettingBtnClick = {},
            onNameChange = {},
            onSignUpClick = {}
        )
    }
}
