package com.sopt.presentation.group.groupCreate

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.GroupProfileEntity
import com.sopt.presentation.R
import com.sopt.presentation.group.groupCreate.component.GroupProfileImagePicker
import com.sopt.presentation.group.groupCreate.component.GroupProfileNameTextField
import com.sopt.presentation.group.groupCreate.permission.launchImagePicker
import com.sopt.presentation.group.groupCreate.permission.rememberGalleryLauncher
import com.sopt.presentation.group.groupCreate.permission.rememberPhotoPickerLauncher
import timber.log.Timber

@Composable
fun GroupCreateRoute(
    paddingValues: PaddingValues,
    navigateToGroupCreateSuccess: () -> Unit,
    viewModel: GroupCreateViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val groupProfileState by viewModel.groupProfileState.collectAsStateWithLifecycle()

    var isGalleryPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        try {
            if (isGranted) viewModel.updateGalleryPermissionState(true)
            else isGalleryPermission = true
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

    val galleryLauncher = rememberGalleryLauncher { uri ->
        viewModel.onImageSelected(uri.toString())
    }

    val photoPickerLauncher = rememberPhotoPickerLauncher { uri ->
        viewModel.onImageSelected(uri.toString())
    }

    LaunchedEffect(lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is GroupCreateSideEffect.NavigateToGroupCreateSuccess -> navigateToGroupCreateSuccess()

                    is GroupCreateSideEffect.ShowPermissionDeniedDialog -> isGalleryPermission =
                        true

                    is GroupCreateSideEffect.RequestImagePicker -> launchImagePicker(
                        galleryLauncher,
                        photoPickerLauncher
                    )
                }
            }
    }

    GroupCreateScreen(
        paddingValues = paddingValues,
        groupProfileState = groupProfileState,
        onProfileCameraBtnClick = { viewModel.requestGalleryPicker() },
        onNameChange = { newName ->
            viewModel.onGroupNameChanged(newName)
        },
        onNextBtnClick = { nickname, imageUri ->
            viewModel.navigateToGroupCreateSuccess()
        },
    )
}

@Composable
fun GroupCreateScreen(
    paddingValues: PaddingValues = PaddingValues(),
    groupProfileState: GroupProfileEntity,
    onProfileCameraBtnClick: () -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onNextBtnClick: (String, String?) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
        ) {
            Text(
                text = stringResource(R.string.text_group_create_title),
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.h2Bold,
                modifier = Modifier.padding(top = 70.dp)
            )
            GroupProfileImagePicker(
                selectedImageUri = groupProfileState.selectedImageUri,
                onCameraBtnClick = onProfileCameraBtnClick,
                modifier = Modifier
                    .padding(top = 46.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.height(27.dp))
            GroupProfileNameTextField(
                placeholder = stringResource(R.string.tf_group_create_placeholder),
                onValueChange = onNameChange
            )
        }
        NoostakBottomButton(
            text = stringResource(R.string.btn_group_create_next),
            activateColor = NoostakTheme.colors.blue600,
            deactivateColor = NoostakTheme.colors.gray500,
            isEnabled = groupProfileState.isGroupNameCheck,
            onButtonClick = {
                onNextBtnClick(
                    groupProfileState.groupName,
                    groupProfileState.selectedImageUri,
                )
            })
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCreateScreenPreview() {
    NoostakAndroidTheme {
        GroupCreateScreen(
            groupProfileState = GroupProfileEntity(
                groupName = "누스탁",
                selectedImageUri = null,
            ),
            onProfileCameraBtnClick = {}, onNameChange = {}, onNextBtnClick = { _, _ -> }
        )
    }
}