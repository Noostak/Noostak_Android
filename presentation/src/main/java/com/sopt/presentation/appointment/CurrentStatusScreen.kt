package com.sopt.presentation.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.component.timetable.NoostakTimeTable
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R

@Composable
fun CurrentStatusScreen(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "실시간 현황",
            color = NoostakTheme.colors.black,
            style = NoostakTheme.typography.b1SemiBold
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = NoostakTheme.colors.blue200,
                contentColor = NoostakTheme.colors.gray700
            ),
            onClick = { /*TODO*/ },
            contentPadding = PaddingValues(
                start = 8.dp,
                bottom = 6.dp,
                end = 10.dp,
                top = 6.dp
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_appointment_add),
                    contentDescription = null
                )
                Text(
                    text = "일정 수정",
                    style = NoostakTheme.typography.c4Regular
                )
            }
        }
    }
    NoostakTimeTable(days = 7, time = 16)
}
