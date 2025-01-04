package com.sopt.presentation.appointment.appointmentCheck

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakButton
import com.sopt.core.designsystem.component.timetable.NoostakTimeTableClickable
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R

@Composable
fun AppointmentCheckRoute(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    navigateUp: () -> Unit,
    navigateToAppointment: (Long, Long, String) -> Unit,
    appointmentCheckViewModel: AppointmentCheckViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = appointmentCheckViewModel.sideEffects) {
        appointmentCheckViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentCheckSideEffect.NavigateUp -> navigateUp()
                is AppointmentCheckSideEffect.NavigateToAppointment -> {
                    navigateToAppointment(
                        sideEffect.groupId,
                        sideEffect.appointmentsId,
                        sideEffect.appointmentName
                    )
                }
            }
        }
    }
    AppointmentCheckScreen(
        groupId = groupId,
        appointmentsId = appointmentsId,
        appointmentName = appointmentName,
        onBackButtonClick = appointmentCheckViewModel::navigateUp,
        onConfirmButtonClick = appointmentCheckViewModel::navigateToAppointment
    )
}

@Composable
fun AppointmentCheckScreen(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    onBackButtonClick: () -> Unit,
    onConfirmButtonClick: (Long, Long, String) -> Unit
) {
    val days = 6
    val time = 18

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
                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding))
        ) {
            Text(
                modifier = Modifier.padding(top = 11.dp, start = 6.dp, bottom = 16.dp),
                text = stringResource(R.string.title_appointment_check),
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.h4Bold,
                textAlign = TextAlign.Start
            )
            NoostakTimeTableClickable(
                days = days,
                time = time,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            NoostakButton(
                text = stringResource(R.string.btn_appointment_check),
                onButtonClick = { onConfirmButtonClick(groupId, appointmentsId, appointmentName) },
                isEnabled = true
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
            onBackButtonClick = {},
            onConfirmButtonClick = { _, _, _ -> }
        )
    }
}
