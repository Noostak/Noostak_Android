package com.sopt.presentation.mypage.editProfile

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.sopt.core.designsystem.component.textfield.NoostakTextField
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.launchImagePicker
import com.sopt.core.type.TextFieldType
import com.sopt.core.util.permission.ImagePickerLaunchers
import com.sopt.domain.entity.UserEntity
import com.sopt.presentation.R
import timber.log.Timber

@Composable
fun EditProfileRoute(
    navigateUp: () -> Unit,
    navigateToMyPage: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val editProfileState by viewModel.editProfileState.collectAsStateWithLifecycle()
    val userInfoState by viewModel.userInfoState.collectAsStateWithLifecycle()

    var isGalleryPermission by remember { mutableStateOf(false) }

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

    LaunchedEffect(Unit) {
        val permission = when {
            Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU -> Manifest.permission.READ_MEDIA_IMAGES
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> Manifest.permission.READ_EXTERNAL_STORAGE
            else -> return@LaunchedEffect
        }

        permissionLauncher.launch(permission)
    }

    val galleryLauncher = ImagePickerLaunchers().rememberGalleryLauncher { uri ->
        viewModel.onImageSelected(uri.toString())
    }

    val photoPickerLauncher = ImagePickerLaunchers().rememberPhotoPickerLauncher { uri ->
        viewModel.onImageSelected(uri.toString())
    }

    LaunchedEffect(lifecycleOwner) {
        viewModel.sideEffects.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is EditProfileSideEffect.NavigateUp -> navigateUp()

                    is EditProfileSideEffect.NavigateToMyPage -> navigateToMyPage()

                    is EditProfileSideEffect.ShowGalleryToast ->
                        isGalleryPermission = true

                    is EditProfileSideEffect.RequestImagePicker -> context.launchImagePicker(
                        galleryLauncher,
                        photoPickerLauncher
                    )
                }
            }
    }

    if (isGalleryPermission) {
        Toast.makeText(
            context,
            stringResource(R.string.toast_permission_gallery),
            Toast.LENGTH_SHORT
        ).show()
        isGalleryPermission = false
    }

    EditProfileScreen(
        onBackButtonClick = viewModel::navigateUp,
        userInfoState = userInfoState,
        editProfileState = editProfileState,
        onProfileCameraBtnClick = { viewModel.requestGalleryPicker() },
        onNameChange = { newName ->
            viewModel.onNickNameChanged(newName)
        },
        onNextBtnClick = {
            viewModel.navigateToMyPage()
        }
    )
}

@Composable
fun EditProfileScreen(
    onBackButtonClick: () -> Unit,
    userInfoState: UserEntity,
    editProfileState: EditProfileState,
    onProfileCameraBtnClick: () -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onNextBtnClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = stringResource(R.string.btn_my_page_profile_edit),
                modifier = Modifier,
                isIconVisible = true,
                onBackButtonClick = onBackButtonClick
            )
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
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
            ) {
                Spacer(modifier = Modifier.height(37.dp))
                ProfileImagePicker(
                    selectedImageUri = userInfoState.profileImage,
                    onCameraBtnClick = onProfileCameraBtnClick,
                    modifier = Modifier
                        .padding(top = 46.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(32.dp))
                NoostakTextField(
                    textFieldType = TextFieldType.SIGNUP,
                    value = userInfoState.nickName,
                    onValueChange = { onNameChange(it) },
                    maxLength = 10,
                    lengthTextStyle = NoostakTheme.typography.c3Regular
                )
            }
            NoostakBottomButton(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.vertical_padding)),
                text = stringResource(R.string.btn_group_create_next),
                activateColor = NoostakTheme.colors.blue600,
                deactivateColor = NoostakTheme.colors.gray500,
                isEnabled = editProfileState.isNameCheck,
                onButtonClick = onNextBtnClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCreateScreenPreview() {
    NoostakAndroidTheme {
        EditProfileScreen(
            onBackButtonClick = {},
            userInfoState = UserEntity(
                nickName = "누스탁",
                profileImage = null
            ),
            editProfileState = EditProfileState(),
            onProfileCameraBtnClick = {},
            onNameChange = {},
            onNextBtnClick = {}
        )
    }
}
