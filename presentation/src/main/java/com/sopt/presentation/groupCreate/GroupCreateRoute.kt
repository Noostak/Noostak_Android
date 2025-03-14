package com.sopt.presentation.groupCreate

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.sopt.core.designsystem.component.dialog.NoostakDialog
import com.sopt.core.designsystem.component.image.ProfileImagePicker
import com.sopt.core.designsystem.component.snackbar.NoostakSnackBar
import com.sopt.core.designsystem.component.snackbar.SNACK_BAR_DURATION
import com.sopt.core.designsystem.component.textfield.NoostakTextField
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.launchImagePicker
import com.sopt.core.type.DialogType
import com.sopt.core.type.ImagePickerType
import com.sopt.core.type.TextFieldType
import com.sopt.core.util.permission.ImagePickerLaunchers
import com.sopt.domain.entity.GroupProfileEntity
import com.sopt.presentation.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun GroupCreateRoute(
    paddingValues: PaddingValues,
    navigateToGroupCreateSuccess: () -> Unit,
    groupCreateViewModel: GroupCreateViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val groupProfileState by groupCreateViewModel.groupProfileState.collectAsStateWithLifecycle()

    var isGalleryPermission by remember { mutableStateOf(false) }

    val showDialog by groupCreateViewModel.showDialog.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val snackBarVisible = remember { mutableStateOf(false) }

    val onShowPermissionGallerySnackBar: (message: String) -> Unit = {
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
                groupCreateViewModel.updateGalleryPermissionState(true)
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
        groupCreateViewModel.onImageSelected(uri.toString())
    }

    val photoPickerLauncher = ImagePickerLaunchers().rememberPhotoPickerLauncher { uri ->
        groupCreateViewModel.onImageSelected(uri.toString())
    }

    LaunchedEffect(lifecycleOwner) {
        groupCreateViewModel.sideEffects.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is GroupCreateSideEffect.NavigateToGroupCreateSuccess -> navigateToGroupCreateSuccess()

                    is GroupCreateSideEffect.ShowSnackBar ->
                        isGalleryPermission = true

                    is GroupCreateSideEffect.ShowDialog -> groupCreateViewModel.showDialog(true)

                    is GroupCreateSideEffect.RequestImagePicker -> context.launchImagePicker(
                        galleryLauncher,
                        photoPickerLauncher
                    )
                }
            }
    }

    if (isGalleryPermission) {
        onShowPermissionGallerySnackBar(context.getString(R.string.sb_permission_gallery))
        isGalleryPermission = false
    }

    if (showDialog) {
        NoostakDialog(
            dialogType = DialogType.NETWORK_GROUP_CREATE_FAILURE,
            onClick = {
                groupCreateViewModel.navigateToGroupCreateSuccess()
            },
            onDismissRequest = { groupCreateViewModel.showDialog(false) }
        )
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
                    .padding(bottom = dimensionResource(id = R.dimen.bottom_padding_snack_bar_gallery_permission)),
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

    GroupCreateScreen(
        paddingValues = paddingValues,
        groupProfileState = groupProfileState,
        onProfileCameraBtnClick = { groupCreateViewModel.requestGalleryPicker() },
        onNameChange = { newName ->
            groupCreateViewModel.onGroupNameChanged(newName)
        },
        onNextBtnClick = { nickname, imageUri ->
            groupCreateViewModel.navigateToGroupCreateSuccess()
        }
    )
}

@Composable
fun GroupCreateScreen(
    paddingValues: PaddingValues = PaddingValues(),
    groupProfileState: GroupProfileEntity,
    onProfileCameraBtnClick: () -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onNextBtnClick: (String, String?) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
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
            Text(
                text = stringResource(R.string.text_group_create_title),
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.h2Bold,
                modifier = Modifier.padding(top = 70.dp)
            )
            ProfileImagePicker(
                imagePickerType = ImagePickerType.GROUP,
                selectedImageUri = groupProfileState.selectedImageUri,
                onCameraBtnClick = onProfileCameraBtnClick,
                modifier = Modifier
                    .padding(top = 46.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(27.dp))
            NoostakTextField(
                textFieldType = TextFieldType.GROUP,
                value = groupProfileState.groupName,
                onValueChange = { onNameChange(it) },
                lengthTextStyle = NoostakTheme.typography.c3Regular
            )
        }
        NoostakBottomButton(
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.vertical_padding)),
            text = stringResource(R.string.btn_group_create_next),
            activateColor = NoostakTheme.colors.blue600,
            deactivateColor = NoostakTheme.colors.gray500,
            isEnabled = groupProfileState.isGroupNameCheck,
            onButtonClick = {
                onNextBtnClick(
                    groupProfileState.groupName,
                    groupProfileState.selectedImageUri
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCreateScreenPreview() {
    NoostakAndroidTheme {
        GroupCreateScreen(
            groupProfileState = GroupProfileEntity(
                groupName = "누스탁",
                selectedImageUri = null
            ),
            onProfileCameraBtnClick = {},
            onNameChange = {},
            onNextBtnClick = { _, _ -> }
        )
    }
}
