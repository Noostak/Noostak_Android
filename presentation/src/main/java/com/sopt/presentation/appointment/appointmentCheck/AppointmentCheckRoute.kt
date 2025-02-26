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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.timetable.NoostakEditableTimeTable
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.state.UiState
import com.sopt.domain.entity.TimeEntity
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
    val postTimeTableState by appointmentCheckViewModel.postTimeTableState.collectAsStateWithLifecycle()
    var selectedData by remember { mutableStateOf(emptyList<TimeEntity>()) }
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
        appointmentName = appointmentName,
        availablePeriods = appointmentCheckViewModel.mockAvailablePeriods,
        onSelectedDataChange = { selectedData = it },
        onBackButtonClick = appointmentCheckViewModel::navigateToGroupDetail,
        onConfirmButtonClick = {
            appointmentCheckViewModel.postTimeTable(appointmentId, selectedData)
            when (postTimeTableState) {
                is UiState.Success -> appointmentCheckViewModel.navigateToAppointment(
                    groupId,
                    appointmentId,
                    appointmentName
                )

                is UiState.Failure -> {
                    Timber.e("postTimeTable 실패: ${(postTimeTableState as UiState.Failure).msg}")
                    appointmentCheckViewModel.navigateToAppointment(
                        groupId,
                        appointmentId,
                        appointmentName
                    )
                }

                else -> Timber.d("postTimeTable 로딩 중")
            }
        }
    )
}

@Composable
fun AppointmentCheckScreen(
    groupId: Long,
    appointmentName: String,
    availablePeriods: List<TimeEntity>,
    onSelectedDataChange: (List<TimeEntity>) -> Unit = {},
    onBackButtonClick: (Long) -> Unit,
    onConfirmButtonClick: () -> Unit
) {
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
                onSelectedDataChange(it)
                Timber.d("selectedData: $it")
            }
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
                onButtonClick = onConfirmButtonClick,
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
            appointmentName = "3차 회의",
            availablePeriods = listOf(
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
            ),
            onBackButtonClick = {},
            onConfirmButtonClick = {}
        )
    }
}
