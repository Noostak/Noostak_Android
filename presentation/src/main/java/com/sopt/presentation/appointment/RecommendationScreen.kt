package com.sopt.presentation.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.component.button.NoostakButton
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.util.NoRippleInteractionSource
import com.sopt.presentation.R

@Composable
fun RecommendationScreen() {
    var isSelected by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(2) {
                RecommendationItem(
                    onItemClick = { isSelected = !isSelected }
                )
            }
        }
        NoostakButton(
            text = "확정",
            onButtonClick = { /*TODO*/ },
            isEnabled = isSelected
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

}

@Composable
fun RecommendationItem(
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = NoostakTheme.colors.blue50,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = NoostakTheme.colors.blue100,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "9월 7일 (일) 10:00 - 12:00",
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
                Icon(
                    modifier = Modifier.noRippleClickable { },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart),
                    contentDescription = null,
                    tint = NoostakTheme.colors.red02
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "1",
                    color = NoostakTheme.colors.black,
                    style = NoostakTheme.typography.c4Regular
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RecommendationScreenPreview() {
    NoostakAndroidTheme {
        RecommendationScreen()
    }
}