package com.sopt.presentation.appointmentCreate.appointmentCreateTimePicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.platform.LocalContext
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
import com.sopt.core.designsystem.component.snackbar.SNACK_BAR_DURATION
import com.sopt.core.designsystem.component.text.NoostakHeaderText
import com.sopt.core.designsystem.component.text.NoostakSubHeaderText
import com.sopt.core.designsystem.component.timepicker.NoostakTimePicker
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.presentation.R
import kotlinx.coroutines.delay
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
        groupId = groupId,
        appointmentName = appointmentName,
        appointmentCategory = appointmentCategory,
        appointmentDuration = appointmentTime,
        isSingleDateMode = isSingleDateMode,
        appointmentDate = appointmentDate,
        onBackButtonClick = calendarTimePickerViewModel::navigateUp,
        onButtonClick = calendarTimePickerViewModel::navigateToCalendarCheck
    )
}

@Composable
fun AppointmentCreateTimePickerScreen(
    groupId: Long,
    appointmentName: String,
    appointmentCategory: String,
    appointmentDuration: Int,
    isSingleDateMode: Boolean,
    appointmentDate: List<String>,
    onButtonClick: (Long, String, String, Int, Boolean, List<String>, String) -> Unit,
    onBackButtonClick: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var isChecked by remember { mutableStateOf(false) }
    var showPicker by remember { mutableStateOf(true) }
    var selectedStartHour by remember { mutableStateOf<Int?>(0) }
    var selectedEndHour by remember { mutableStateOf<Int?>(23) }
    var showMessage by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val onShowSnackBar: (String) -> Unit = { msg ->
        coroutineScope.launch {
            showMessage = true
            val job = launch { snackBarHostState.showSnackbar(message = msg) }
            delay(SNACK_BAR_DURATION)
            job.cancel()
            showMessage = false
        }
    }

    LaunchedEffect(showMessage) {
        if (showMessage) {
            coroutineScope.launch {
                onShowSnackBar(context.getString(R.string.sb_appointment_create_time_picker, appointmentDuration))
            }
        }
    }

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
            AnimatedVisibility(
                visible = showMessage,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                SnackbarHost(
                    modifier = Modifier.padding(bottom = 96.dp),
                    hostState = snackBarHostState,
                    snackbar = { snackBarData ->
                        NoostakSnackBar(
                            message = snackBarData.visuals.message,
                            textStyle = NoostakTheme.typography.c2SemiBold,
                            textColor = NoostakTheme.colors.red01,
                            backgroundColor = NoostakTheme.colors.pink
                        )
                    }
                )
            }
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
                        color = NoostakTheme.colors.gray500,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(
                        color = if (isChecked) NoostakTheme.colors.gray50 else NoostakTheme.colors.white,
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
                    style = NoostakTheme.typography.b4SemiBold,
                    color = NoostakTheme.colors.gray900
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
                        if (selectedStartHour == selectedEndHour) {
                            0
                        } else if (startHour < adjustedEndHour) {
                            adjustedEndHour - startHour
                        } else {
                            adjustedEndHour + 24 - startHour
                        }
                    } ?: 24

                    if (duration < appointmentDuration) {
                        showMessage = true
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
            groupId = 0,
            appointmentName = "약속 이름",
            appointmentCategory = "약속 카테고리",
            appointmentDuration = 1,
            isSingleDateMode = false,
            appointmentDate = emptyList(),
            onButtonClick = { _, _, _, _, _, _, _ -> },
            onBackButtonClick = { }
        )
    }
}
