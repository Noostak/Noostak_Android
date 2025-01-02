package com.sopt.presentation.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.domain.entity.AppointmentEntity
import com.sopt.presentation.R

@Composable
fun AppointmentRoute(
    groupId: Long,
    appointmentsId: Long,
    appointmentName: String,
    navigateUp: () -> Unit,
    appointmentViewModel: AppointmentViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = appointmentViewModel.sideEffects) {
        appointmentViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is AppointmentSideEffect.NavigateUp -> navigateUp()
            }
        }
    }
    AppointmentScreen(
        appointmentName = appointmentName,
        onBackButtonClick = appointmentViewModel::navigateUp,
        recommendations = appointmentViewModel.mockRecommendations
    )
}

@Composable
fun AppointmentScreen(
    appointmentName: String,
    onBackButtonClick: () -> Unit,
    recommendations: List<AppointmentEntity>
) {
    var selectedItemIndex by remember { mutableIntStateOf(-1) }
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "추천 시간",
                    color = NoostakTheme.colors.black,
                    style = NoostakTheme.typography.b1SemiBold
                )
                Row {
                    Text(
                        text = "전체보기",
                        color = NoostakTheme.colors.gray800,
                        style = NoostakTheme.typography.c3Regular
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_appointment_right_arrow),
                        contentDescription = null,
                        tint = NoostakTheme.colors.gray800
                    )
                }
            }
            LazyRow(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(recommendations.size) { index ->
                    RecommendationHeaderItem(
                        selectedItemIndex = selectedItemIndex,
                        onHeaderItemClick = { selectedIndex ->
                            selectedItemIndex = selectedIndex // 선택된 아이템의 인덱스 업데이트
                        },
                        itemIndex = index // 현재 아이템의 인덱스 전달
                    )
                }
            }
            if (selectedItemIndex == -1) {
                CurrentStatusScreen()
            } else {
                RecommendationScreen(
                    selectedItemIndex = selectedItemIndex,
                    data = recommendations
                )
            }
        }
    }
}

@Composable
fun RecommendationHeaderItem(
    selectedItemIndex: Int, // 현재 선택된 아이템 인덱스
    onHeaderItemClick: (Int) -> Unit,
    itemIndex: Int // 현재 아이템의 인덱스
) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = when (selectedItemIndex) {
                    -1 -> NoostakTheme.colors.gray200
                    itemIndex -> NoostakTheme.colors.blue700
                    else -> NoostakTheme.colors.gray50
                },
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = when (selectedItemIndex) {
                    -1 -> NoostakTheme.colors.white
                    itemIndex -> NoostakTheme.colors.blue50
                    else -> NoostakTheme.colors.gray50
                },
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                top = 10.dp,
                start = 16.dp,
                bottom = 10.dp,
                end = 34.dp
            )
            .noRippleClickable {
                if (itemIndex == selectedItemIndex) {
                    onHeaderItemClick(-1) // 이미 선택된 아이템을 다시 클릭하면 선택 해제
                } else {
                    onHeaderItemClick(itemIndex) // 선택되지 않은 아이템을 클릭하면 선택
                }
            }
    ) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = "Best${itemIndex + 1}",
            color = when (selectedItemIndex) {
                -1 -> NoostakTheme.colors.black
                itemIndex -> NoostakTheme.colors.blue700
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
                            itemIndex -> NoostakTheme.colors.blue700
                            else -> NoostakTheme.colors.gray700
                        }
                    )
                ) {
                    append("6명")
                }
                withStyle(
                    style = SpanStyle(
                        color = when (selectedItemIndex) {
                            -1 -> NoostakTheme.colors.gray700
                            itemIndex -> NoostakTheme.colors.gray700
                            else -> NoostakTheme.colors.gray500
                        }
                    )
                ) {
                    append(" / 10명")
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
            appointmentName = "3차 회의",
            onBackButtonClick = {},
            recommendations = appointmentViewModel.mockRecommendations
        )
    }
}
