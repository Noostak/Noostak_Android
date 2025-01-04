package com.sopt.presentation.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.dialog.AppointmentDialog
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.presentation.R
import com.sopt.presentation.appointment.screen.CurrentStatusScreen
import com.sopt.presentation.appointment.screen.RecommendationScreen

@Composable
fun AppointmentRoute(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    navigateUp: () -> Unit,
    navigateToAppointmentCheck: (Long, Long, String) -> Unit,
    navigateToAppointmentConfirm: (Long, Long, Long, String) -> Unit,
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = appointmentViewModel.sideEffects) {
        appointmentViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentSideEffect.NavigateUp -> navigateUp()
                is AppointmentSideEffect.NavigateToAppointmentCheck -> {
                    navigateToAppointmentCheck(
                        sideEffect.groupId,
                        sideEffect.appointmentsId,
                        sideEffect.appointmentName
                    )
                }
                is AppointmentSideEffect.NavigateToAppointmentConfirm -> {
                    navigateToAppointmentConfirm(
                        sideEffect.groupId,
                        sideEffect.appointmentsId,
                        sideEffect.optionId,
                        sideEffect.appointmentName
                    )
                }
            }
        }
    }
    AppointmentScreen(
        groupId = groupId,
        appointmentsId = appointmentsId,
        appointmentName = appointmentName,
        onBackButtonClick = appointmentViewModel::navigateUp,
        onSubmitButtonClick = appointmentViewModel::navigateToAppointmentCheck,
        onConfirmButtonClick = appointmentViewModel::navigateToAppointmentConfirm,
        recommendations = appointmentViewModel.mockRecommendations
    )
}

@Composable
fun AppointmentScreen(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    onBackButtonClick: () -> Unit,
    onSubmitButtonClick: (Long, Long, String) -> Unit,
    onConfirmButtonClick: (Long, Long, Long, String) -> Unit,
    recommendations: AppointmentEntity
) {
    var selectedItemIndex by remember { mutableIntStateOf(-1) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        showDialog = !recommendations.isSubmitted
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = appointmentName,
                isIconVisible = true,
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (showDialog) {
                AppointmentDialog(
                    onDismissRequest = {
                        showDialog = false
                        onBackButtonClick()
                    },
                    onConfirmButtonClick = {
                        showDialog = false
                        onSubmitButtonClick(groupId, appointmentsId, appointmentName)
                    },
                    description = "이미 일정을 등록하지 않았어요!\n일정을 등록하러 가볼까요?",
                    dismissText = "나중에 등록하기",
                    confirmButtonText = "가능일정 등록하기"
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.header_appointment_recommendation),
                    color = NoostakTheme.colors.black,
                    style = NoostakTheme.typography.b1SemiBold
                )
                Row {
                    Text(
                        text = stringResource(R.string.btn_appointment_total),
                        color = NoostakTheme.colors.gray800,
                        style = NoostakTheme.typography.c3Regular
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_appointment_right_arrow),
                        contentDescription = null,
                        tint = NoostakTheme.colors.gray800
                    )
                }
            }
            LazyRow(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(recommendations.priorities, key = { it.priority }) { priority ->
                    RecommendationHeaderItem(
                        availableMembersCount = priority.availableMembersCount,
                        totalMembersCount = priority.totalMembersCount,
                        selectedItemIndex = selectedItemIndex,
                        onHeaderItemClick = { selectedIndex ->
                            selectedItemIndex = selectedIndex
                        },
                        priority = priority.priority
                    )
                }
            }
            if (selectedItemIndex == -1) {
                CurrentStatusScreen()
            } else {
                RecommendationScreen(
                    selectedItemIndex = selectedItemIndex,
                    data = recommendations.priorities,
                    onConfirmButtonClick = { optionId ->
                        onConfirmButtonClick(groupId, appointmentsId, optionId, appointmentName)
                    }
                )
            }
        }
    }
}

@Composable
fun RecommendationHeaderItem(
    availableMembersCount: Int, // 가능한 멤버 수
    totalMembersCount: Int, // 전체 멤버 수
    selectedItemIndex: Int, // 현재 선택된 아이템 인덱스
    onHeaderItemClick: (Int) -> Unit,
    priority: Int // 현재 아이템의 인덱스
) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = when (selectedItemIndex) {
                    -1 -> NoostakTheme.colors.gray200
                    priority -> NoostakTheme.colors.blue700
                    else -> NoostakTheme.colors.gray50
                },
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = when (selectedItemIndex) {
                    -1 -> NoostakTheme.colors.white
                    priority -> NoostakTheme.colors.blue50
                    else -> NoostakTheme.colors.gray50
                },
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                top = 10.dp,
                start = 16.dp,
                bottom = 10.dp,
                end = 34.dp
            )
            .noRippleClickable {
                if (priority == selectedItemIndex) {
                    onHeaderItemClick(-1) // 이미 선택된 아이템을 다시 클릭하면 선택 해제
                } else {
                    onHeaderItemClick(priority) // 선택되지 않은 아이템을 클릭하면 선택
                }
            }
    ) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = "Best${priority}",
            color = when (selectedItemIndex) {
                -1 -> NoostakTheme.colors.black
                priority -> NoostakTheme.colors.blue700
                else -> NoostakTheme.colors.gray500
            },
            style = NoostakTheme.typography.t4Bold
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = when (selectedItemIndex) {
                            -1 -> NoostakTheme.colors.black
                            priority -> NoostakTheme.colors.blue700
                            else -> NoostakTheme.colors.gray700
                        }
                    )
                ) {
                    append("${availableMembersCount}명")
                }
                withStyle(
                    style = SpanStyle(
                        color = when (selectedItemIndex) {
                            -1 -> NoostakTheme.colors.gray700
                            priority -> NoostakTheme.colors.gray700
                            else -> NoostakTheme.colors.gray500
                        }
                    )
                ) {
                    append(" / ${totalMembersCount}명")
                }
            },
            style = NoostakTheme.typography.b4SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentScreenPreview() {
    NoostakAndroidTheme {
        val appointmentViewModel: AppointmentViewModel = hiltViewModel()
        AppointmentScreen(
            groupId = 1,
            appointmentsId = 1,
            appointmentName = "3차 회의",
            onBackButtonClick = {},
            onSubmitButtonClick = { _, _, _ -> },
            onConfirmButtonClick = { _, _, _, _ -> },
            recommendations = appointmentViewModel.mockRecommendations
        )
    }
}
