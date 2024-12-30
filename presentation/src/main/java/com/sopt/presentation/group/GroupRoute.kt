package com.sopt.presentation.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import timber.log.Timber

@Composable
fun GroupRoute(
    paddingValues: PaddingValues,
    navigateToGroupDetail: (Long) -> Unit,
    groupViewModel: GroupViewModel = hiltViewModel()
) {

    LaunchedEffect(groupViewModel.sideEffects) {
        groupViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is GroupSideEffect.NavigateToGroupDetail -> {
                    navigateToGroupDetail(sideEffect.id)
                    Timber.d("group id: ${sideEffect.id}")
                }
            }
        }
    }

    GroupScreen(
        paddingValues = paddingValues,
        onGroupClick = groupViewModel::navigateToGroupDetail
    )
}

@Composable
fun GroupScreen(
    paddingValues: PaddingValues = PaddingValues(),
    onGroupClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Appointment Screen")
        Button(onClick = { onGroupClick(2) }) {
            Text(text = "그룹 상세 페이지로 이동")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupScreenPreview() {
    NoostakAndroidTheme {
        GroupScreen(
            onGroupClick = {}
        )
    }
}
