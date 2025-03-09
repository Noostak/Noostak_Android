package com.sopt.presentation.appointment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sopt.core.designsystem.component.dialog.NoostakDialog
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.screen.NoostakLoadingScreen
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.extension.scrollToItem
import com.sopt.core.extension.showIf
import com.sopt.core.state.UiState
import com.sopt.core.type.DialogType
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.domain.entity.AppointmentMembersInfoEntity
import com.sopt.domain.entity.TimeEntity
import com.sopt.presentation.R
import com.sopt.presentation.appointment.screen.CurrentStatusScreen
import com.sopt.presentation.appointment.screen.RecommendationScreen

@Composable
fun AppointmentRoute(
    groupId: Long,
    appointmentId: Long,
    appointmentName: String,
    navigateUp: () -> Unit,
    navigateToAppointmentCheck: (Long, Long, String, List<TimeEntity>) -> Unit,
    navigateToAppointmentConfirm: (Long, Long, Long, String) -> Unit,
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    val showDialog by appointmentViewModel.showDialog.collectAsStateWithLifecycle()
    val getOptionsState by appointmentViewModel.getOptionsState.collectAsStateWithLifecycle()
    val getTimeTableState by appointmentViewModel.getTimeTableState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = appointmentViewModel.sideEffects) {
        appointmentViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentSideEffect.NavigateUp -> navigateUp()
                is AppointmentSideEffect.NavigateToAppointmentCheck -> {
                    navigateToAppointmentCheck(
                        sideEffect.groupId,
                        sideEffect.appointmentsId,
                        sideEffect.appointmentName,
                        sideEffect.availablePeriods
                    )
                }

                is AppointmentSideEffect.NavigateToAppointmentConfirm -> {
                    navigateToAppointmentConfirm(
                        sideEffect.groupId,
                        sideEffect.appointmentsId,
                        sideEffect.optionId,
                        sideEffect.appointmentName
                    )
                }

                is AppointmentSideEffect.ShowDialog -> {
                    appointmentViewModel.showDialog(sideEffect.show)
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        appointmentViewModel.getOptions(appointmentId = appointmentId)
        appointmentViewModel.getTimeTable(appointmentId = appointmentId)
    }

    if (showDialog) {
        NoostakDialog(
            dialogType = DialogType.APPOINTMENT,
            onClick = {
                appointmentViewModel.apply {
                    showDialog(false)
                    if (getTimeTableState is UiState.Success) {
                        navigateToAppointmentCheck(
                            groupId,
                            appointmentId,
                            appointmentName,
                            (getTimeTableState as UiState.Success).data.appointmentSchedule.appointmentHostSelectionTimes
                        )
                    } else {
                        navigateToAppointmentCheck(
                            groupId,
                            appointmentId,
                            appointmentName,
                            mockAvailablePeriods
                        )
                    }
                }
            },
            onDismissRequest = {
                appointmentViewModel.apply {
                    showDialog(false)
                    navigateUp()
                }
            }
        )
    }
    if (getOptionsState is UiState.Success && getTimeTableState is UiState.Success) {
        AppointmentScreen(
            groupId = groupId,
            appointmentsId = appointmentId,
            appointmentName = appointmentName,
            onBackButtonClick = appointmentViewModel::navigateUp,
            onConfirmButtonClick = appointmentViewModel::navigateToAppointmentConfirm,
            availablePeriods = (getTimeTableState as UiState.Success).data.appointmentSchedule.appointmentHostSelectionTimes,
            availableTimes = (getTimeTableState as UiState.Success).data.appointmentSchedule.appointmentMembersInfo,
            recommendations = (getOptionsState as UiState.Success).data,
            onLikeClick = { appointmentOptionId, isLiked ->
                if (isLiked) {
                    appointmentViewModel.postLike(appointmentId, appointmentOptionId)
                } else {
                    appointmentViewModel.deleteLike(appointmentId, appointmentOptionId)
                }
            }
        )
    } else if (getOptionsState is UiState.Loading || getTimeTableState is UiState.Loading) {
        NoostakLoadingScreen()
    }
}

@Composable
fun AppointmentScreen(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    onBackButtonClick: () -> Unit,
    onConfirmButtonClick: (Long, Long, Long, String) -> Unit,
    availablePeriods: List<TimeEntity>,
    availableTimes: List<AppointmentMembersInfoEntity>,
    recommendations: AppointmentEntity,
    onLikeClick: (Long, Boolean) -> Unit = { _, _ -> }
) {
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    var selectedItemIndex by remember { mutableIntStateOf(-1) }

    BackHandler {
        when (selectedItemIndex) {
            -1 -> onBackButtonClick()
            else -> {
                selectedItemIndex = -1
                listState.scrollToItem(coroutineScope, density)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = appointmentName,
                isIconVisible = true,
                onBackButtonClick = {
                    when (selectedItemIndex) {
                        -1 -> onBackButtonClick()
                        else -> {
                            selectedItemIndex = -1
                            listState.scrollToItem(coroutineScope, density)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, bottom = 14.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.header_appointment_recommendation),
                    color = NoostakTheme.colors.black,
                    style = NoostakTheme.typography.b1SemiBold
                )
                Row(
                    modifier = Modifier
                        .showIf(selectedItemIndex == -1)
                        .noRippleClickable { selectedItemIndex = 0 },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.btn_appointment_total),
                        color = NoostakTheme.colors.gray800,
                        style = NoostakTheme.typography.c3Regular
                    )
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_appointment_right_arrow),
                        contentDescription = null,
                        tint = NoostakTheme.colors.gray800
                    )
                }
            }

            if (recommendations.recommendationPriority.isEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 27.dp, bottom = 41.dp),
                    text = stringResource(R.string.text_appointment_recommendations_blank),
                    color = NoostakTheme.colors.gray900,
                    style = NoostakTheme.typography.b2Regular,
                    textAlign = TextAlign.Center
                )
            } else {
                LazyRow(
                    state = listState,
                    modifier = Modifier.padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    itemsIndexed(recommendations.recommendationPriority) { index, recommendationPriority ->
                        val option = recommendationPriority.options.firstOrNull()
                        RecommendationHeaderItem(
                            availableMemberCount = option?.availableMemberCount ?: 0,
                            totalMemberCount = option?.totalMemberCount ?: 0,
                            selectedItemIndex = selectedItemIndex,
                            onHeaderItemClick = {
                                selectedItemIndex = it
                                listState.scrollToItem(coroutineScope, density, index)
                            },
                            priority = index
                        )
                    }
                }
            }
            if (selectedItemIndex == -1) {
                CurrentStatusScreen(
                    availablePeriods = availablePeriods,
                    availableTimes = availableTimes
                )
            } else {
                RecommendationScreen(
                    isHost = recommendations.isHost,
                    selectedItemIndex = selectedItemIndex,
                    data = recommendations.recommendationPriority,
                    onConfirmButtonClick = { optionId ->
                        onConfirmButtonClick(groupId, appointmentsId, optionId, appointmentName)
                    },
                    onLikeClick = onLikeClick
                )
            }
        }
    }
}

@Composable
fun RecommendationHeaderItem(
    availableMemberCount: Int,
    totalMemberCount: Int,
    selectedItemIndex: Int,
    onHeaderItemClick: (Int) -> Unit,
    priority: Int
) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = when (selectedItemIndex) {
                    -1 -> NoostakTheme.colors.gray200
                    priority -> NoostakTheme.colors.blue700
                    else -> NoostakTheme.colors.gray50
                },
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = when (selectedItemIndex) {
                    -1 -> NoostakTheme.colors.white
                    priority -> NoostakTheme.colors.blue50
                    else -> NoostakTheme.colors.gray50
                },
                shape = RoundedCornerShape(12.dp)
            )
            .width(116.dp)
            .padding(
                top = 10.dp,
                start = 16.dp,
                bottom = 10.dp,
                end = 21.dp
            )
            .noRippleClickable {
                if (priority == selectedItemIndex) {
                    onHeaderItemClick(-1)
                } else {
                    onHeaderItemClick(priority)
                }
            }
    ) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = stringResource(R.string.text_appointment_priority, priority + 1),
            color = when (selectedItemIndex) {
                -1 -> NoostakTheme.colors.black
                priority -> NoostakTheme.colors.blue700
                else -> NoostakTheme.colors.gray500
            },
            style = NoostakTheme.typography.t4Bold
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = when (selectedItemIndex) {
                            -1 -> NoostakTheme.colors.black
                            priority -> NoostakTheme.colors.blue700
                            else -> NoostakTheme.colors.gray700
                        }
                    )
                ) {
                    append(
                        stringResource(
                            R.string.tv_appointment_availableMembersCount,
                            availableMemberCount
                        )
                    )
                }
                withStyle(
                    style = SpanStyle(
                        color = when (selectedItemIndex) {
                            -1 -> NoostakTheme.colors.gray700
                            priority -> NoostakTheme.colors.gray700
                            else -> NoostakTheme.colors.gray500
                        }
                    )
                ) {
                    append(
                        stringResource(
                            R.string.tv_appointment_totalMembersCount,
                            totalMemberCount
                        )
                    )
                }
            },
            style = NoostakTheme.typography.b4SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppointmentScreenPreview() {
    NoostakAndroidTheme {
        val appointmentViewModel: AppointmentViewModel = hiltViewModel()
        AppointmentScreen(
            groupId = 1,
            appointmentsId = 1,
            appointmentName = "3차 회의",
            onBackButtonClick = {},
            onConfirmButtonClick = { _, _, _, _ -> },
            availablePeriods = appointmentViewModel.mockAvailablePeriods,
            availableTimes = appointmentViewModel.mockAvailableTimes,
            recommendations = appointmentViewModel.mockRecommendations
        )
    }
}
