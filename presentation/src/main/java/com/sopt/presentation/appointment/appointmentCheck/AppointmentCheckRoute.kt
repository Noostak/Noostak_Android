package com.sopt.presentation.appointment.appointmentCheck

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.timetable.NoostakEditableTimeTable
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.TimeEntity
import com.sopt.domain.entity.TimeTableScheduleEntity
import com.sopt.presentation.R
import timber.log.Timber

@Composable
fun AppointmentCheckRoute(
    groupId: Long,
    appointmentId: Long,
    appointmentName: String,
    navigateUp: () -> Unit,
    navigateToAppointment: (Long, Long, String) -> Unit,
    navigateToGroupDetail: (Long) -> Unit,
    appointmentCheckViewModel: AppointmentCheckViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = appointmentCheckViewModel.sideEffects) {
        appointmentCheckViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentCheckSideEffect.NavigateUp -> navigateUp()
                is AppointmentCheckSideEffect.NavigateToAppointment -> {
                    navigateToAppointment(
                        sideEffect.groupId,
                        sideEffect.appointmentId,
                        sideEffect.appointmentName
                    )
                }

                is AppointmentCheckSideEffect.NavigateToGroupDetail -> {
                    navigateToGroupDetail(sideEffect.groupId)
                }
            }
        }
    }
    AppointmentCheckScreen(
        groupId = groupId,
        appointmentsId = appointmentId,
        appointmentName = appointmentName,
        availablePeriods = appointmentCheckViewModel.mockAvailablePeriods,
        onBackButtonClick = appointmentCheckViewModel::navigateToGroupDetail,
        onConfirmButtonClick = appointmentCheckViewModel::navigateToAppointment
    )
}

@Composable
fun AppointmentCheckScreen(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    availablePeriods: TimeTableScheduleEntity,
    onBackButtonClick: (Long) -> Unit,
    onConfirmButtonClick: (Long, Long, String) -> Unit
) {
    var selectedData by remember { mutableStateOf(emptyList<TimeEntity>()) }
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = appointmentName,
                isIconVisible = true,
                onBackButtonClick = { onBackButtonClick(groupId) }
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(id = R.dimen.default_padding))
        ) {
            val (title, timeTable, button) = createRefs()

            // 제목
            Text(
                modifier = Modifier
                    .padding(top = 11.dp, start = 6.dp, bottom = 16.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                text = stringResource(R.string.title_appointment_check),
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.h4Bold,
                textAlign = TextAlign.Start
            )

            // 타임테이블 (스크롤 가능)
            NoostakEditableTimeTable(
                availablePeriods = availablePeriods,
                modifier = Modifier
                    .constrainAs(timeTable) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
            ) {
                Timber.d("selectedData: $it")
            }

            // 버튼 (항상 하단 고정)
            NoostakBottomButton(
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(
                        top = 7.dp,
                        bottom = dimensionResource(id = R.dimen.vertical_padding)
                    ),
                text = stringResource(R.string.btn_appointment_check),
                onButtonClick = { onConfirmButtonClick(groupId, appointmentsId, appointmentName) },
                isEnabled = true,
                activateColor = NoostakTheme.colors.gray900
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppointmentConfirmScreen() {
    NoostakAndroidTheme {
        AppointmentCheckScreen(
            groupId = 1,
            appointmentsId = 1,
            appointmentName = "3차 회의",
            availablePeriods = TimeTableScheduleEntity(
                listOf(
                    TimeEntity(
                        date = "2024-09-05T10:00:00",
                        startTime = "2024-09-05T10:00:00",
                        endTime = "2024-09-05T18:00:00"
                    ),
                    TimeEntity(
                        date = "2024-09-06T10:00:00",
                        startTime = "2024-09-06T10:00:00",
                        endTime = "2024-09-06T18:00:00"
                    ),
                    TimeEntity(
                        date = "2024-09-07T10:00:00",
                        startTime = "2024-09-07T10:00:00",
                        endTime = "2024-09-07T18:00:00"
                    )
                )
            ),
            onBackButtonClick = {},
            onConfirmButtonClick = { _, _, _ -> }
        )
    }
}
