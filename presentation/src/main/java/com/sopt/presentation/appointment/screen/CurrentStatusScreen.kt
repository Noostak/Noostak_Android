package com.sopt.presentation.appointment.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.timetable.NoostakTimeTable
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.util.NoRippleInteractionSource
import com.sopt.domain.entity.AppointmentMembersInfoEntity
import com.sopt.domain.entity.TimeEntity
import com.sopt.presentation.R
import com.sopt.presentation.appointment.AppointmentViewModel

@Composable
fun CurrentStatusScreen(
    modifier: Modifier = Modifier,
    availablePeriods: List<TimeEntity>,
    availableTimes: List<AppointmentMembersInfoEntity>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding))
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.header_appointment_current_status),
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.b1SemiBold
            )
        }
        NoostakTimeTable(
            availablePeriods = availablePeriods,
            availableTimes = availableTimes,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CurrentStatusScreenPreview() {
    val appointmentViewModel: AppointmentViewModel = hiltViewModel()
    CurrentStatusScreen(
        availablePeriods = appointmentViewModel.mockAvailablePeriods,
        availableTimes = appointmentViewModel.mockAvailableTimes
    )
}
