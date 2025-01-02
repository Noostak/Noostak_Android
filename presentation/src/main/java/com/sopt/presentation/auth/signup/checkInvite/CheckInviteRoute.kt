package com.sopt.presentation.auth.signup.checkInvite

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R
import com.sopt.presentation.auth.component.AuthButton

@Composable
fun CheckInviteRoute(
    navigateToGroup: () -> Unit,
    navigateToInputGroupCode: () -> Unit,
    checkInviteViewModel: CheckInviteViewModel = hiltViewModel(),
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
        onNoInViteTextClick = {
            checkInviteViewModel.navigateToGroup()
        },
        onInputGroupCodeClick = {
            checkInviteViewModel.navigateToInputGroupCode()
        }
    )
}

@Composable
fun CheckInviteScreen(
    onNoInViteTextClick: () -> Unit,
    onInputGroupCodeClick: () -> Unit
) {
    val offsetY = remember { Animatable(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.tv_invite_description, "박유진"),
            textAlign = TextAlign.Center,
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.t1SemiBold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.btn_invite_code),
            modifier = Modifier
                .offset(y = offsetY.value.dp)
                .size(265.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.clickable { onNoInViteTextClick() },
            text = stringResource(R.string.tv_invite_empty),
            color = NoostakTheme.colors.gray800,
            style = NoostakTheme.typography.c3Regular,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthButton(
            padding = PaddingValues(vertical = 15.dp),
            onClick = onInputGroupCodeClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.btn_invite_code),
                style = NoostakTheme.typography.b1SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    NoostakAndroidTheme {
        CheckInviteScreen(
            onNoInViteTextClick = {},
            onInputGroupCodeClick = {}
        )
    }
}