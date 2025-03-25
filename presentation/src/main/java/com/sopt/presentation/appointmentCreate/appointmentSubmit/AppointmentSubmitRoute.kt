package com.sopt.presentation.appointmentCreate.appointmentSubmit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.chip.NoostakCategoryChip
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R

@Composable
fun AppointmentSubmitRoute(
    groupId: Long,
    appointmentName: String,
    isConsecutive: Boolean,
    appointmentDate: List<String>,
    appointmentTime: String? = null,
    appointmentCategory: String,
    appointmentDuration: Int,
    navigateUp: () -> Unit,
    navigateToAppointmentSubmitConfirm: (Long, String, Boolean, List<String>, String?, String, Int) -> Unit,
    appointmentSubmitViewModel: AppointmentSubmitViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = appointmentSubmitViewModel.sideEffects) {
        appointmentSubmitViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentSubmitSideEffect.NavigateUp -> navigateUp()
                is AppointmentSubmitSideEffect.NavigateToAppointmentSubmitConfirm -> {
                    navigateToAppointmentSubmitConfirm(
                        sideEffect.groupId,
                        sideEffect.appointmentName,
                        sideEffect.isConsecutive,
                        sideEffect.appointmentDate,
                        sideEffect.appointmentTime,
                        sideEffect.appointmentCategory,
                        sideEffect.appointmentDuration
                    )
                }
            }
        }
    }
    AppointmentSubmitScreen(
        groupId = groupId,
        appointmentName = appointmentName,
        isConsecutive = isConsecutive,
        appointmentDate = appointmentDate,
        appointmentTime = appointmentTime,
        appointmentCategory = appointmentCategory,
        appointmentDuration = appointmentDuration,
        onBackButtonClick = appointmentSubmitViewModel::navigateUp,
        onConfirmButtonClick = { gId, aName, isCons, aDate, aTime, aCategory, aDuration ->
            appointmentSubmitViewModel.postAppointmentCreate(
                groupId = groupId,
                appointmentName = aName,
                appointmentCategory = aCategory,
                appointmentDuration = aDuration,
                appointmentDate = aDate,
                appointmentTime = aTime ?: "00:00 ~ 23:00"
            )
            appointmentSubmitViewModel.navigateToAppointmentSubmitConfirm(
                groupId = groupId,
                appointmentName = aName,
                appointmentCategory = aCategory,
                appointmentDuration = aDuration,
                appointmentDate = aDate,
                appointmentTime = aTime ?: "00:00 ~ 23:00",
                isConsecutive = isCons
            )
        }
    )
}

@Composable
fun AppointmentSubmitScreen(
    groupId: Long,
    appointmentName: String,
    isConsecutive: Boolean,
    appointmentDate: List<String>,
    appointmentTime: String? = null,
    appointmentCategory: String,
    appointmentDuration: Int,
    onBackButtonClick: () -> Unit,
    onConfirmButtonClick: (Long, String, Boolean, List<String>, String?, String, Int) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = stringResource(R.string.appbar_appointment_submit),
                isIconVisible = false,
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.default_padding))
        ) {
            Text(
                modifier = Modifier.padding(start = 1.dp, top = 21.dp),
                text = stringResource(R.string.header_appointment_submit),
                style = NoostakTheme.typography.h2Bold,
                color = NoostakTheme.colors.gray900
            )
            Text(
                modifier = Modifier.padding(start = 1.dp, top = 5.dp, bottom = 34.dp),
                text = stringResource(R.string.subheader_appointment_submit),
                style = NoostakTheme.typography.b2Regular,
                color = NoostakTheme.colors.gray800
            )
            AppointmentInfoBox(
                appointmentName = appointmentName,
                isConsecutive = isConsecutive,
                appointmentDate = appointmentDate,
                appointmentTime = appointmentTime,
                appointmentCategory = appointmentCategory,
                appointmentDuration = appointmentDuration
            )
            Spacer(modifier = Modifier.weight(1f))
            NoostakBottomButton(
                text = stringResource(R.string.btn_appointment_submit),
                isEnabled = true,
                activateColor = NoostakTheme.colors.gray900,
                onButtonClick = {
                    onConfirmButtonClick(
                        groupId,
                        appointmentName,
                        isConsecutive,
                        appointmentDate,
                        appointmentTime,
                        appointmentCategory,
                        appointmentDuration
                    )
                }
            )
        }
    }
}

@Composable
fun AppointmentInfoBox(
    appointmentName: String,
    isConsecutive: Boolean,
    appointmentDate: List<String>,
    appointmentTime: String? = null,
    appointmentCategory: String,
    appointmentDuration: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = NoostakTheme.colors.gray50,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 25.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Text(
            text = stringResource(R.string.text_appointment_submit_info),
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.b4SemiBold
        )
        AppointmentInfoRow(
            icon = R.drawable.ic_appointment_name,
            label = stringResource(R.string.text_appointment_submit_name),
            content = appointmentName
        )
        AppointmentInfoRow(
            icon = R.drawable.ic_appointment_calendar,
            label = stringResource(R.string.text_appointment_submit_time),
            content = if (!isConsecutive) {
                val firstDateParts = appointmentDate.first().split("-")
                val lastDateParts = appointmentDate.last().split("-")
                "${firstDateParts[1].toInt()}/${firstDateParts[2].toInt()} ~ ${lastDateParts[1].toInt()}/${lastDateParts[2].toInt()}"
            } else {
                appointmentDate.joinToString(", ") { date ->
                    val parts = date.split("-")
                    "${parts[1].toInt()}/${parts[2].toInt()}"
                }
            },
            additionalContent = appointmentTime
        )
        AppointmentInfoRow(
            icon = R.drawable.ic_appointment_category,
            label = stringResource(R.string.text_appointment_submit_category),
            content = appointmentCategory,
            isChip = true
        )
        AppointmentInfoRow(
            icon = R.drawable.ic_appointment_clock,
            label = stringResource(R.string.text_appointment_submit_duration),
            content = stringResource(R.string.text_appointment_submit_hour, appointmentDuration)
        )
    }
}

@Composable
fun AppointmentInfoRow(
    @DrawableRes icon: Int,
    label: String,
    content: String,
    additionalContent: String? = null,
    isChip: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 3.dp),
                text = label,
                color = NoostakTheme.colors.gray700,
                style = NoostakTheme.typography.b4Regular
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (isChip) {
            NoostakCategoryChip(text = content)
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = content,
                    style = NoostakTheme.typography.b4SemiBold,
                    color = NoostakTheme.colors.gray900,
                    textAlign = TextAlign.End
                )
                if (additionalContent != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = additionalContent,
                        style = NoostakTheme.typography.b4SemiBold,
                        color = NoostakTheme.colors.gray900,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppointmentSubmitScreen() {
    NoostakAndroidTheme {
        AppointmentSubmitScreen(
            groupId = 1,
            appointmentName = "누스탁 3차 회의",
            isConsecutive = false,
            appointmentDate = listOf("9/25", "9/26", "9/27", "9/28", "9/29", "09/30"),
            appointmentTime = "10:00 ~ 18:00",
            appointmentCategory = "중요",
            appointmentDuration = 2,
            onBackButtonClick = {},
            onConfirmButtonClick = { _, _, _, _, _, _, _ -> }
        )
    }
}
