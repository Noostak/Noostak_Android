package com.sopt.presentation.auth.signup.checkInvite

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.presentation.R

@Composable
fun CheckInviteRoute(
    name: String,
    navigateToGroup: () -> Unit,
    navigateToInputGroupCode: () -> Unit,
    checkInviteViewModel: CheckInviteViewModel = hiltViewModel()
) {
    LaunchedEffect(checkInviteViewModel.sideEffects) {
        checkInviteViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is CheckInviteSideEffect.NavigateToGroup -> navigateToGroup()
                is CheckInviteSideEffect.NavigateToInputGroupCode -> navigateToInputGroupCode()
            }
        }
    }
    CheckInviteScreen(
        name = name,
        onNoInViteTextClick = checkInviteViewModel::navigateToGroup,
        onInputGroupCodeClick = checkInviteViewModel::navigateToInputGroupCode
    )
}

@Composable
fun CheckInviteScreen(
    name: String,
    onNoInViteTextClick: () -> Unit,
    onInputGroupCodeClick: () -> Unit
) {
    val offsetY = remember { Animatable(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.horizontal_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.tv_invite_description, name),
            textAlign = TextAlign.Center,
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.t1SemiBold
        )
        Image(
            painter = painterResource(id = R.drawable.ic_invite_code),
            contentDescription = stringResource(R.string.btn_invite_code),
            modifier = Modifier
                .offset(y = offsetY.value.dp)
                .size(265.dp)
                .padding(top = 24.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.tv_invite_empty),
            color = NoostakTheme.colors.gray800,
            style = NoostakTheme.typography.c3Regular,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .noRippleClickable { onNoInViteTextClick() }
                .padding(12.dp)
        )
        NoostakBottomButton(
            text = stringResource(R.string.btn_invite_code),
            isEnabled = true,
            onButtonClick = onInputGroupCodeClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    NoostakAndroidTheme {
        CheckInviteScreen(
            name = "이름",
            onNoInViteTextClick = {},
            onInputGroupCodeClick = {}
        )
    }
}
