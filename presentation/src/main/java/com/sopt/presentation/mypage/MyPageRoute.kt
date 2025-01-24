package com.sopt.presentation.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.sopt.core.R
import com.sopt.core.designsystem.component.dialog.NoostakDialog
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.DialogType
import com.sopt.presentation.mypage.component.MyPageItem
import com.sopt.presentation.mypage.component.MyPageProfileEditButton

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateToEditProfile: (String, String?) -> Unit,
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val userInfoState by myPageViewModel.userInfoState.collectAsStateWithLifecycle()
    val showLogoutDialog by myPageViewModel.showLogoutDialog.collectAsStateWithLifecycle()
    val showWithdrawalDialog by myPageViewModel.showWithdrawalDialog.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        myPageViewModel.sideEffects.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyPageSideEffect.NavigateToEditProfile -> navigateToEditProfile(
                        sideEffect.nickname,
                        sideEffect.profileImage
                    )

                    is MyPageSideEffect.ShowDialog -> {
                        when (sideEffect.dialogType) {
                            DialogType.LOGOUT -> myPageViewModel.showDialog(DialogType.LOGOUT, true)
                            DialogType.WITHDRAWAL -> myPageViewModel.showDialog(
                                DialogType.WITHDRAWAL,
                                true
                            )

                            else -> Unit
                        }
                    }
                }
            }
    }

    if (showLogoutDialog) {
        NoostakDialog(
            dialogType = DialogType.LOGOUT,
            onClick = {
                // 추가해야 함
            },
            onDismissRequest = { myPageViewModel.showDialog(DialogType.LOGOUT, false) }
        )
    }

    if (showWithdrawalDialog) {
        NoostakDialog(
            dialogType = DialogType.WITHDRAWAL,
            onClick = {
                // 추가해야 함
            },
            onDismissRequest = { myPageViewModel.showDialog(DialogType.WITHDRAWAL, false) }
        )
    }

    MyPageScreen(
        paddingValues = paddingValues,
        nickname = userInfoState.nickname,
        profileImage = userInfoState.profileImage,
        onProfileEditBtnClick = { myPageViewModel.navigateToEditProfile() },
        onPolicyBtnClick = {
            // browser intent 추가해야 함
        },
        onLogoutBtnClick = { myPageViewModel.triggerDialog(DialogType.LOGOUT) },
        onWithdrawalBtnClick = { myPageViewModel.triggerDialog(DialogType.WITHDRAWAL) }
    )
}

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues = PaddingValues(),
    nickname: String,
    profileImage: String?,
    onProfileEditBtnClick: () -> Unit = {},
    onPolicyBtnClick: () -> Unit = {},
    onLogoutBtnClick: () -> Unit = {},
    onWithdrawalBtnClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(paddingValues),
        topBar = {
            NoostakTopAppBar(
                title = stringResource(com.sopt.presentation.R.string.appbar_mu_page_title),
                modifier = Modifier,
                isIconVisible = false
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    top = 24.dp,
                    start = dimensionResource(id = com.sopt.presentation.R.dimen.horizontal_padding),
                    end = dimensionResource(id = com.sopt.presentation.R.dimen.horizontal_padding),
                    bottom = 20.dp
                )
            ) {
                GlideImage(
                    imageModel = { profileImage?.takeIf { it.isNotBlank() } ?: R.drawable.ic_profile },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(60.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    previewPlaceholder = painterResource(id = R.drawable.ic_profile)
                )
                Text(
                    text = nickname,
                    color = NoostakTheme.colors.gray900,
                    style = NoostakTheme.typography.t4Bold
                )
            }
            MyPageProfileEditButton(
                text = stringResource(com.sopt.presentation.R.string.btn_my_page_profile_edit),
                onClick = { onProfileEditBtnClick() }
            )
            Spacer(modifier = Modifier.height(24.dp))
            MyPageItem(
                text = stringResource(com.sopt.presentation.R.string.text_my_page_item_policy),
                onClick = onPolicyBtnClick
            )
            Spacer(modifier = Modifier.height(8.dp))
            MyPageItem(
                text = stringResource(com.sopt.presentation.R.string.text_my_page_item_logout),
                onClick = onLogoutBtnClick
            )
            Spacer(modifier = Modifier.height(8.dp))
            MyPageItem(
                text = stringResource(com.sopt.presentation.R.string.text_my_page_item_withdrawal),
                onClick = onWithdrawalBtnClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    NoostakAndroidTheme {
        MyPageScreen(
            nickname = "정해인",
            profileImage = null
        )
    }
}
