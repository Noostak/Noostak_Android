package com.sopt.presentation.appointmentCreate.appointmentCreateTimePicker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.checkbox.CircularCheckbox
import com.sopt.core.designsystem.component.progressbar.NoostakProgressBar
import com.sopt.core.designsystem.component.snackbar.NoostakSnackBar
import com.sopt.core.designsystem.component.text.NoostakHeaderText
import com.sopt.core.designsystem.component.text.NoostakSubHeaderText
import com.sopt.core.designsystem.component.timepicker.NoostakTimePicker
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.presentation.R
import kotlinx.coroutines.launch

@Composable
fun AppointmentCreateTimePickerRoute(
    groupId: Long,
    appointmentName: String,
    appointmentCategory: String,
    appointmentTime: Int,
    isSingleDateMode: Boolean,
    appointmentDate: List<String>,
    navigateUp: () -> Unit,
    navigateToCheck: (Long, String, String, Int, Boolean, List<String>, String) -> Unit,
    calendarTimePickerViewModel: AppointmentCreateTimePickerViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = calendarTimePickerViewModel.sideEffects) {
        calendarTimePickerViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentCreateTimePickerSideEffect.NavigateToCheck -> {
                    navigateToCheck(
                        sideEffect.groupId,
                        sideEffect.appointmentName,
                        sideEffect.appointmentCategory,
                        sideEffect.appointmentDuration,
                        sideEffect.isSingleDateMode,
                        sideEffect.appointmentDate,
                        sideEffect.appointmentTime
                    )
                }

                is AppointmentCreateTimePickerSideEffect.NavigateUp -> {
                    navigateUp()
                }
            }
        }
    }

    AppointmentCreateTimePickerScreen(
        onBackButtonClick = calendarTimePickerViewModel::navigateUp,
        onButtonClick = calendarTimePickerViewModel::navigateToCalendarCheck,
        appointmentName = appointmentName,
        appointmentCategory = appointmentCategory,
        appointmentDuration = appointmentTime,
        isSingleDateMode = isSingleDateMode,
        appointmentDate = appointmentDate,
        groupId = groupId
    )
}

@Composable
fun AppointmentCreateTimePickerScreen(
    onButtonClick: (Long, String, String, Int, Boolean, List<String>, String) -> Unit,
    onBackButtonClick: () -> Unit,
    appointmentName: String,
    appointmentCategory: String,
    appointmentDuration: Int,
    isSingleDateMode: Boolean,
    appointmentDate: List<String>,
    groupId: Long
) {
    val typography = NoostakTheme.typography
    val colors = NoostakTheme.colors
    var isChecked by remember { mutableStateOf(false) }
    var showPicker by remember { mutableStateOf(true) }

    var selectedStartHour by remember { mutableStateOf<Int?>(0) }
    var selectedEndHour by remember { mutableStateOf<Int?>(23) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(bottom = 96.dp),
                hostState = snackbarHostState,
                snackbar = { snackBarData ->
                    NoostakSnackBar(
                        message = snackBarData.visuals.message,
                        textStyle = typography.c2SemiBold,
                        textColor = colors.red01,
                        backgroundColor = colors.pink
                    )
                }
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
            NoostakProgressBar(progressBar = listOf(false, false, true))
            NoostakHeaderText(text = stringResource(R.string.text_calendar_appointment_time_choose))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .height(54.dp)
                    .border(
                        width = 0.5.dp,
                        color = colors.gray200,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(
                        color = if (isChecked) colors.gray50 else colors.white,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .noRippleClickable {
                        isChecked = !isChecked
                        showPicker = !isChecked
                    }
                    .padding(horizontal = 12.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.text_calendar_appointment_time_select),
                    modifier = Modifier.weight(1f),
                    style = typography.b4SemiBold,
                    color = colors.gray900
                )
                CircularCheckbox(
                    isChecked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        showPicker = !it
                        if (isChecked) {
                            selectedStartHour = null
                            selectedEndHour = null
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NoostakSubHeaderText(
                    stringResource(R.string.text_calendar_appointment_time_check),
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier.size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(
                            id = if (isChecked) R.drawable.ic_calendar_off else R.drawable.ic_calendar_on
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            if (showPicker) {
                NoostakTimePicker { startHour, endHour ->
                    selectedStartHour = startHour
                    selectedEndHour = endHour
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            NoostakBottomButton(
                text = stringResource(R.string.text_calendar_appointment_next),
                onButtonClick = {
                    val adjustedEndHour = if (selectedEndHour == 0) 24 else selectedEndHour ?: 24
                    val duration = selectedStartHour?.let { startHour ->
                        if (startHour < adjustedEndHour) {
                            adjustedEndHour - startHour
                        } else {
                            adjustedEndHour + 24 - startHour
                        }
                    } ?: 0

                    if (selectedStartHour == selectedEndHour) {
                        scope.launch {
                            snackbarHostState.showSnackbar("24시간 이하로 선택해주세요")
                        }
                        return@NoostakBottomButton
                    }

                    if (duration < appointmentDuration) {
                        scope.launch {
                            snackbarHostState.showSnackbar("${appointmentDuration}시간 이상을 선택해주세요.")
                        }
                        return@NoostakBottomButton
                    }

                    val selectTime = if (isChecked) {
                        null
                    } else {
                        "${
                        selectedStartHour?.toString()?.padStart(2, '0')
                        }:00 ~ ${adjustedEndHour.toString().padStart(2, '0')}:00"
                    }
                    onButtonClick(
                        groupId,
                        appointmentName,
                        appointmentCategory,
                        appointmentDuration,
                        isSingleDateMode,
                        appointmentDate,
                        selectTime ?: "null"
                    )
                },
                deactivateColor = NoostakTheme.colors.gray500,
                activateColor = NoostakTheme.colors.gray900
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentCreateTimePickerScreenPreview() {
    NoostakAndroidTheme {
        AppointmentCreateTimePickerScreen(
            onButtonClick = { _, _, _, _, _, _, _ -> },
            onBackButtonClick = { },
            appointmentName = "약속 이름",
            appointmentCategory = "약속 카테고리",
            appointmentDuration = 1,
            isSingleDateMode = false,
            appointmentDate = emptyList(),
            groupId = 0
        )
    }
}
