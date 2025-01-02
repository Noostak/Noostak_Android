package com.sopt.presentation.appointment.appointmentCheck

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.timetable.NoostakTimeTable
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun AppointmentCheckRoute(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    navigateUp: () -> Unit,
    appointmentCheckViewModel: AppointmentCheckViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = appointmentCheckViewModel.sideEffects) {
        appointmentCheckViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentCheckSideEffect.NavigateUp -> navigateUp()
            }
        }
    }
    AppointmentCheckScreen(
        appointmentName = appointmentName,
        onBackButtonClick = appointmentCheckViewModel::navigateUp
    )
}

@Composable
fun AppointmentCheckScreen(
    appointmentName: String,
    onBackButtonClick: () -> Unit
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
                .padding(horizontal = 16.dp)
                .padding(bottom = 11.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 11.dp, start = 6.dp, bottom = 16.dp),
                text = "가능한 시간을\n모두 선택해주세요",
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.h4Bold,
                textAlign = TextAlign.Start
            )
            NoostakTimeTable(
                days = days,
                time = time,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 15.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NoostakTheme.colors.gray900
                ),
                onClick = { }
            ) {
                Text(
                    text = "확인",
                    style = NoostakTheme.typography.t3Bold,
                    color = NoostakTheme.colors.white
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppointmentConfirmScreen() {
    NoostakAndroidTheme {
        AppointmentCheckScreen(
            appointmentName = "3차 회의",
            onBackButtonClick = {}
        )
    }
}
