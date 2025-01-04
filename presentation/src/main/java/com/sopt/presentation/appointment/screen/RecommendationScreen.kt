package com.sopt.presentation.appointment.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakButton
import com.sopt.core.designsystem.component.chip.NoostakUserChip
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.domain.entity.PriorityEntity
import com.sopt.domain.entity.RecommendationEntity
import com.sopt.presentation.R
import com.sopt.presentation.appointment.AppointmentViewModel

@Composable
fun RecommendationScreen(
    selectedItemIndex: Int,
    data: List<PriorityEntity>,
    onConfirmButtonClick: (Long) -> Unit
) {
    val filteredData = data.find { it.priority == selectedItemIndex }?.recommendations.orEmpty()
    var selectedItemId by remember { mutableStateOf<Long?>(null) }

    // filteredData가 변경될 때마다 초기화
    LaunchedEffect(filteredData) {
        selectedItemId = null
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(filteredData, key = { it.id }) { recommendation ->
                RecommendationItem(
                    data = recommendation,
                    isSelected = selectedItemId == recommendation.id,
                    onItemClick = {
                        selectedItemId =
                            if (selectedItemId == recommendation.id) null else recommendation.id
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        NoostakButton(
            text = stringResource(R.string.btn_appointment_confirm),
            onButtonClick = { selectedItemId?.let { onConfirmButtonClick(it) } },
            isEnabled = selectedItemId != null
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.vertical_padding)))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendationItem(
    data: RecommendationEntity,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    var isLiked by remember { mutableStateOf(data.isLiked) }
    var likes by remember { mutableStateOf(data.likes) }

    Column(
        modifier = Modifier
            .background(
                color = if (isSelected) NoostakTheme.colors.blue200 else NoostakTheme.colors.blue50,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Transparent else NoostakTheme.colors.blue100,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(dimensionResource(id = R.dimen.default_padding))
            .noRippleClickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${data.date} (일) ${data.startTime} - ${data.endTime}",
                color = NoostakTheme.colors.black,
                style = NoostakTheme.typography.t4Bold
            )
            Row(
                modifier = Modifier
                    .background(
                        color = NoostakTheme.colors.white,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = NoostakTheme.colors.gray100,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 13.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.noRippleClickable {
                        isLiked = !isLiked
                        likes = if (isLiked) likes + 1 else likes - 1
                    },
                    imageVector = if (isLiked) {
                        ImageVector.vectorResource(id = R.drawable.ic_heart_on)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.ic_heart_off)
                    },
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = likes.toString(),
                    color = if (isLiked) NoostakTheme.colors.black else NoostakTheme.colors.gray600,
                    style = NoostakTheme.typography.c2SemiBold
                )
            }
        }
        Text(
            text = stringResource(
                R.string.header_appointment_available,
                data.availableMembersCount
            ),
            color = NoostakTheme.colors.black,
            style = NoostakTheme.typography.c2SemiBold
        )
        FlowRow(
            modifier = Modifier.padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            data.availableMembers.forEach { member ->
                NoostakUserChip(
                    text = member,
                    textColor = NoostakTheme.colors.black,
                    backgroundColor = if (member == "나") NoostakTheme.colors.blue200 else NoostakTheme.colors.white,
                    borderColor = NoostakTheme.colors.blue200
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = stringResource(
                R.string.header_appointment_unavailable,
                data.unavailableMembersCount
            ),
            color = NoostakTheme.colors.black,
            style = NoostakTheme.typography.c2SemiBold
        )
        FlowRow(
            modifier = Modifier.padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            data.unavailableMembers.forEach { member ->
                NoostakUserChip(
                    text = member,
                    textColor = NoostakTheme.colors.gray800,
                    backgroundColor = if (member == "나") NoostakTheme.colors.blue200 else NoostakTheme.colors.gray200,
                    borderColor = if (member == "나") NoostakTheme.colors.blue200 else NoostakTheme.colors.gray200
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendationScreenPreview() {
    NoostakAndroidTheme {
        val appointmentViewModel: AppointmentViewModel = hiltViewModel()
        RecommendationScreen(
            selectedItemIndex = 1,
            data = appointmentViewModel.mockRecommendations.priorities,
            onConfirmButtonClick = {}
        )
    }
}
