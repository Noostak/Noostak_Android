package com.sopt.presentation.appointment.appointmentConfirm

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.chip.AvailableUserChips
import com.sopt.core.designsystem.component.chip.NoostakCategoryChip
import com.sopt.core.designsystem.component.chip.UnavailableUserChips
import com.sopt.core.designsystem.component.dialog.NoostakDialog
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.screen.NoostakFailureScreen
import com.sopt.core.designsystem.screen.NoostakLoadingScreen
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.showIf
import com.sopt.core.state.UiState
import com.sopt.core.util.CalculateTime
import com.sopt.core.util.RearrangeList
import com.sopt.domain.entity.AppointmentDetailEntity
import com.sopt.presentation.R
import com.sopt.presentation.groupDetail.confirmedDetail.CompleteDetailInfo
import timber.log.Timber

@Composable
fun AppointmentConfirmRoute(
    groupId: Long,
    appointmentId: Long,
    optionId: Long,
    appointmentName: String,
    navigateUp: () -> Unit,
    navigateToGroupDetail: (Long) -> Unit,
    appointmentConfirmViewModel: AppointmentConfirmViewModel = hiltViewModel()
) {
    val showErrorDialog by appointmentConfirmViewModel.showErrorDialog.collectAsStateWithLifecycle()
    val getConfirmedState by appointmentConfirmViewModel.getConfirmedState.collectAsStateWithLifecycle()
    val postConfirmedState by appointmentConfirmViewModel.postConfirmedState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(key1 = appointmentConfirmViewModel.sideEffects) {
        appointmentConfirmViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentConfirmSideEffect.NavigateUp -> navigateUp()
                is AppointmentConfirmSideEffect.NavigateToGroupDetail -> {
                    navigateToGroupDetail(sideEffect.groupId)
                }

                is AppointmentConfirmSideEffect.ShowErrorDialog -> appointmentConfirmViewModel.showErrorDialog(
                    sideEffect.show, sideEffect.dialogType
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        appointmentConfirmViewModel.getConfirmed(optionId)
    }

    LaunchedEffect(key1 = postConfirmedState) {
        when (postConfirmedState) {
            is UiState.Success -> appointmentConfirmViewModel.navigateToGroupDetail(
                groupId
            )

            else -> {}
        }
    }

    when (getConfirmedState) {
        is UiState.Loading -> NoostakLoadingScreen()
        is UiState.Success -> {
            AppointmentConfirmScreen(
                groupId = groupId,
                appointmentName = appointmentName,
                onBackButtonClick = appointmentConfirmViewModel::navigateUp,
                onConfirmButtonClick = {
                    appointmentConfirmViewModel.postConfirmed(optionId)
                },
                data = (getConfirmedState as UiState.Success).data
            )
        }

        is UiState.Failure -> {
            Timber.e("getConfirmedState is failure $getConfirmedState")
            NoostakFailureScreen(
                onBackButtonClick = appointmentConfirmViewModel::navigateUp,
                onRetryButtonClick = {
                    appointmentConfirmViewModel.getConfirmed(optionId)
                }
            )
        }

        else -> {}
    }

    if (showErrorDialog.first) {
        NoostakDialog(
            dialogType = showErrorDialog.second,
            onClick = {
                appointmentConfirmViewModel.showErrorDialog(false, showErrorDialog.second)
                appointmentConfirmViewModel.postConfirmed(optionId)
            },
            onDismissRequest = {
                appointmentConfirmViewModel.showErrorDialog(false, showErrorDialog.second)
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppointmentConfirmScreen(
    groupId: Long,
    appointmentName: String,
    onBackButtonClick: () -> Unit,
    onConfirmButtonClick: (Long) -> Unit,
    data: AppointmentDetailEntity
) {
    val calculateTime = CalculateTime()
    val date = calculateTime.extractDateWithKorean(data.date)
    val dayOfWeek = calculateTime.extractDayOfWeekWithBraces(data.date)
    val startHour = calculateTime.extractHourWithZero(data.startTime)
    val rearrangeList = RearrangeList()
    val availableMembers = rearrangeList.rearrangeMembersBasedOnAvailability(
        data.myIdentity,
        data.availableMembers
    )
    val unavailableMembers = rearrangeList.rearrangeMembersBasedOnAvailability(
        data.myIdentity,
        data.unavailableMembers
    )
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
                .padding(dimensionResource(id = R.dimen.horizontal_padding))
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.title_appointment_confirm),
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.h4Bold
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.subtitle_appointment_confirm),
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.c3Regular
            )
            Text(
                modifier = Modifier.padding(top = 20.dp, start = 3.dp, bottom = 12.dp),
                text = stringResource(R.string.tv_appointment_confirm_info),
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.t4Bold
            )
            Column(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(20.dp),
                        color = NoostakTheme.colors.gray200
                    )
                    .padding(dimensionResource(id = R.dimen.default_padding)),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CompleteDetailInfo(text = stringResource(R.string.tv_complete_detail_time)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(13.dp)
                    ) {
                        Text(
                            text = "$date $dayOfWeek",
                            color = NoostakTheme.colors.black,
                            style = NoostakTheme.typography.b4SemiBold
                        )
                        Text(
                            text = startHour,
                            color = NoostakTheme.colors.black,
                            style = NoostakTheme.typography.b4SemiBold
                        )
                    }
                }
                CompleteDetailInfo(text = stringResource(R.string.tv_complete_detail_category)) {
                    NoostakCategoryChip(text = data.category)
                }
                Column {
                    CompleteDetailInfo(
                        text = stringResource(
                            R.string.tv_complete_detail_available,
                            data.availableMembersCount
                        )
                    )
                    FlowRow(
                        modifier = Modifier.padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        AvailableUserChips(
                            members = availableMembers,
                            myIdentity = data.myIdentity
                        )
                    }
                }
                Column {
                    CompleteDetailInfo(
                        text = stringResource(
                            R.string.tv_complete_detail_unavailable,
                            data.unavailableMembersCount
                        )
                    )
                    FlowRow(
                        modifier = Modifier.padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        UnavailableUserChips(
                            members = unavailableMembers,
                            myIdentity = data.myIdentity
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            NoostakBottomButton(
                modifier = Modifier.showIf(data.isHost),
                text = stringResource(R.string.btn_appointment_confirm_complete),
                onButtonClick = { onConfirmButtonClick(groupId) },
                isEnabled = true,
                activateColor = NoostakTheme.colors.gray900
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentConfirmScreenPreview() {
    val appointmentConfirmViewModel: AppointmentConfirmViewModel = hiltViewModel()
    NoostakAndroidTheme {
        AppointmentConfirmScreen(
            groupId = 1,
            appointmentName = "약속 이름",
            onBackButtonClick = {},
            onConfirmButtonClick = {},
            data = appointmentConfirmViewModel.mockAppointmentDetail
        )
    }
}
