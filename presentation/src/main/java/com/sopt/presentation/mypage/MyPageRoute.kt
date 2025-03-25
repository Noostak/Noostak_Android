package com.sopt.presentation.mypage

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.sopt.core.designsystem.component.dialog.NoostakDialog
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.screen.NoostakFailureScreen
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.state.UiState
import com.sopt.core.type.DialogType
import com.sopt.domain.entity.ProfileEntity
import com.sopt.presentation.mypage.component.MyPageItem
import com.sopt.presentation.mypage.component.MyPageProfileEditButton
import com.sopt.presentation.mypage.component.MyPageProfileImage

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    navigateToEditProfile: (String, String?) -> Unit,
    navigateToLogin: () -> Unit,
    myPageViewModel: MyPageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val userInfoState by myPageViewModel.userInfoState.collectAsStateWithLifecycle()
    val showLogoutDialog by myPageViewModel.showLogoutDialog.collectAsStateWithLifecycle()
    val showWithdrawalDialog by myPageViewModel.showWithdrawalDialog.collectAsStateWithLifecycle()

    val getProfileState by myPageViewModel.getProfileState.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        myPageViewModel.sideEffects.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyPageSideEffect.NavigateToEditProfile -> navigateToEditProfile(
                        sideEffect.nickname,
                        sideEffect.profileImage
                    )

                    is MyPageSideEffect.NavigateToLogin -> navigateToLogin()

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
                myPageViewModel.postLogout()
            },
            onDismissRequest = { myPageViewModel.showDialog(DialogType.LOGOUT, false) }
        )
    }

    if (showWithdrawalDialog) {
        NoostakDialog(
            dialogType = DialogType.WITHDRAWAL,
            onClick = {
                myPageViewModel.deleteWithdraw()
            },
            onDismissRequest = { myPageViewModel.showDialog(DialogType.WITHDRAWAL, false) }
        )
    }

    when (getProfileState) {
        is UiState.Success -> {
            MyPageScreen(
                paddingValues = paddingValues,
                data = userInfoState,
                onProfileEditBtnClick = { myPageViewModel.navigateToEditProfile() },
                onPolicyBtnClick = {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://tough-sled-044.notion.site/5a1ad92b5b484747a6ddd97e939e86f7?pvs=4")
                    ).let { context.startActivity(it) }
                },
                onLogoutBtnClick = { myPageViewModel.triggerDialog(DialogType.LOGOUT) },
                onWithdrawalBtnClick = { myPageViewModel.triggerDialog(DialogType.WITHDRAWAL) }
            )
        }

        is UiState.Failure -> NoostakFailureScreen()
        else -> Unit
    }
}

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues = PaddingValues(),
    data: ProfileEntity,
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
                    bottom = 19.dp
                )
            ) {
                MyPageProfileImage(
                    imageUrl = data.memberProfileImage,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(61.dp)
                        .aspectRatio(1f)
                )
                Text(
                    text = data.memberName,
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
            data = ProfileEntity(
                memberName = "정해인",
                memberProfileImage = null
            )
        )
    }
}
