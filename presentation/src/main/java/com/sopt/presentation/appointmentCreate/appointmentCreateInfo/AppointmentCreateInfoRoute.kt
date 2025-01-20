package com.sopt.presentation.appointmentCreate.appointmentCreateInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.chip.NoostakCalendarChip
import com.sopt.core.designsystem.component.progressbar.NoostakProgressBar
import com.sopt.core.designsystem.component.text.NoostakHeaderText
import com.sopt.core.designsystem.component.text.NoostakSubHeaderText
import com.sopt.core.designsystem.component.textfield.NoostakTextField
import com.sopt.core.designsystem.component.textfield.TimeTextField
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.type.TextFieldType
import com.sopt.presentation.R

@Composable
fun AppointmentCreateInfoRoute(
    groupId: Long,
    navigateUp: () -> Unit,
    navigateToPeriod: (Long, String, String, Int) -> Unit,
    calendarInfoViewModel: AppointmentCreateInfoViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = calendarInfoViewModel.sideEffects) {
        calendarInfoViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentCreateInfoSideEffect.NavigateToPeriod -> {
                    navigateToPeriod(
                        sideEffect.groupId,
                        sideEffect.appointmentName,
                        sideEffect.appointmentCategory,
                        sideEffect.appointmentDuration
                    )
                }

                is AppointmentCreateInfoSideEffect.NavigateUp -> {
                    navigateUp()
                }
            }
        }
    }
    AppointmentCreateInfoScreen(
        onBackButtonClick = calendarInfoViewModel::navigateUp,
        onButtonClick = calendarInfoViewModel::navigateToAppointmentCreatePeriod,
        categories = calendarInfoViewModel.categories,
        groupId = groupId
    )
}

@Composable
fun AppointmentCreateInfoScreen(
    groupId: Long,
    onButtonClick: (Long, String, String, Int) -> Unit,
    onBackButtonClick: () -> Unit,
    categories: List<String>
) {
    var appointmentName by remember { mutableStateOf("") }
    var appointmentCategory by remember { mutableStateOf("") }
    var appointmentDuration by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = stringResource(R.string.text_calendar_appointment),
                isIconVisible = true,
                onBackButtonClick = { onBackButtonClick() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(id = R.dimen.horizontal_padding))
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            NoostakProgressBar(progressBar = listOf(true, false, false))
            NoostakHeaderText(text = stringResource(R.string.text_calendar_appointment_write))
            NoostakSubHeaderText(
                stringResource(R.string.text_calendar_appointment_name),
                Modifier.padding(top = 22.dp, bottom = 10.dp)
            )
            NoostakTextField(
                textFieldType = TextFieldType.CALENDAR,
                cursorColor = NoostakTheme.colors.gray500,
                shape = RoundedCornerShape(10.dp),
                focusedBorderColor = NoostakTheme.colors.blue600,
                unfocusedBorderColor = NoostakTheme.colors.gray200,
                maxLength = 20,
                placeholderColor = NoostakTheme.colors.gray500,
                textStyle = NoostakTheme.typography.b1SemiBold,
                maxLengthColor = NoostakTheme.colors.gray500,
                onValueChange = { appointmentName = it },
                value = appointmentName
            )
            NoostakSubHeaderText(
                stringResource(R.string.text_calendar_appointment_category),
                Modifier.padding(top = 32.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(11.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = appointmentCategory == category
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .noRippleClickable { appointmentCategory = category }
                    ) {
                        NoostakCalendarChip(
                            text = category,
                            textStyle = NoostakTheme.typography.b4SemiBold,
                            textColor = if (isSelected) NoostakTheme.colors.white else NoostakTheme.colors.gray900,
                            backgroundColor = if (isSelected) NoostakTheme.colors.black else NoostakTheme.colors.white,
                            borderColor = if (isSelected) Color.Transparent else NoostakTheme.colors.gray200,
                            horizontalPaddingValues = 20.dp,
                            verticalPaddingValues = 8.dp
                        )
                    }
                }
            }
            NoostakSubHeaderText(
                stringResource(R.string.text_calendar_appointment_time),
                Modifier.padding(top = 41.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                TimeTextField(
                    onValueChange = { newDuration ->
                        appointmentDuration = newDuration
                    }
                )
                Text(
                    text = stringResource(R.string.text_calendar_appointment_duration),
                    style = NoostakTheme.typography.b1SemiBold,
                    color = NoostakTheme.colors.gray700,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 12.dp, top = 15.dp, bottom = 15.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            NoostakBottomButton(
                text = stringResource(R.string.text_calendar_appointment_next),
                onButtonClick = {
                    val time = appointmentDuration.toIntOrNull() ?: 0
                    onButtonClick(groupId, appointmentName, appointmentCategory, time)
                },
                isEnabled = appointmentName.isNotBlank() &&
                        appointmentCategory.isNotBlank() &&
                        appointmentDuration.isNotBlank() &&
                        (appointmentDuration.toIntOrNull()?.let { it in 1..10 } == true),
                deactivateColor = NoostakTheme.colors.gray500,
                activateColor = NoostakTheme.colors.gray900
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentCreateInfoScreenPreview() {
    NoostakAndroidTheme {
        AppointmentCreateInfoScreen(
            groupId = 0,
            onButtonClick = { _, _, _, _ -> },
            onBackButtonClick = { },
            categories = listOf("중요", "일정", "취미", "기타")
        )
    }
}
