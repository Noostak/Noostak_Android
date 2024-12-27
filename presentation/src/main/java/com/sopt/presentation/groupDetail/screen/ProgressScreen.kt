package com.sopt.presentation.groupDetail.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.ProgressEntity
import com.sopt.presentation.R

@Composable
fun ProgressScreen(
    data: List<ProgressEntity>
) {
    if (data.isEmpty()) {
        Text(
            modifier = Modifier.padding(top = 103.dp),
            text = stringResource(R.string.tv_group_detail_no_progress),
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.b2Regular
        )
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            items(data.size) {
                ProgressItem(data = data[it])
            }
        }
    }
}

@Composable
fun ProgressItem(
    data: ProgressEntity
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(15.dp),
                color = NoostakTheme.colors.gray200
            )
            .padding(
                top = 15.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 19.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.title,
                color = NoostakTheme.colors.gray900,
                style = NoostakTheme.typography.t4Bold
            )
            Icon(
                modifier = Modifier.padding(end = 10.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_group_detail_arrow),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(11.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.date,
                color = NoostakTheme.colors.gray800,
                style = NoostakTheme.typography.b5Regular
            )
            Text(
                text = "${data.number}명/${data.total}명",
                color = NoostakTheme.colors.gray700,
                style = NoostakTheme.typography.b5Regular
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = { 0.7f },
            modifier = Modifier.fillMaxWidth(),
            color = NoostakTheme.colors.blue300,
            trackColor = NoostakTheme.colors.gray200,
            gapSize = 0.dp,
            strokeCap = StrokeCap.Round,
            drawStopIndicator = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressScreenPreview() {
    NoostakAndroidTheme {
        ProgressScreen(
            data = listOf(
                ProgressEntity(
                    id = 1,
                    title = "약속 제목",
                    date = "09/07 (일) 11시~14시",
                    number = 3,
                    total = 5
                )
            )
        )
    }
}