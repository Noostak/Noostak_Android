package com.sopt.presentation.calendar.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.core.designsystem.component.button.NoostakButton
import com.sopt.core.designsystem.component.chip.NoostakCategoryChip2
import com.sopt.core.designsystem.component.textfield.NoostakTextField
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.designsystem.theme.PretendardSemiBold
import com.sopt.presentation.R
import com.sopt.presentation.calendar.CalendarViewModel

@Composable
fun InfoScreen(viewModel: CalendarViewModel, onNextClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    val typography = NoostakTheme.typography
    val colors = NoostakTheme.colors

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        topBar = {
            NoostakTopAppBar(
                title = "약속 만들기",
                modifier = Modifier.fillMaxWidth(),
                isIconVisible = false
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            ProgressBar(progressBar = listOf(true, false, false))

            HeaderText(text = "약속 정보를 입력해주세요")

            SubHeaderText("약속 이름", Modifier.padding(top = 22.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Column {
                    NoostakTextField(
                        value = uiState.appointName,
                        onValueChange = { newValue ->
                            if (newValue.length <= 20) viewModel.updateAppointName(newValue)
                        },
                        placeholder = "이름을 입력해주세요.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 4.dp, top = 4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "${uiState.appointName.length}/20",
                            style = typography.b5Regular,
                            color = colors.gray500
                        )
                    }
                }
            }

            SubHeaderText("약속 카테고리", Modifier.padding(top = 41.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(11.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                listOf("중요", "일정", "취미", "기타").forEach { category ->
                    val isSelected = uiState.category == category
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .clickable { viewModel.updateCategory(category) }
                    ) {
                        NoostakCategoryChip2(
                            text = category,
                            textColor = if (isSelected) Color.White else colors.black,
                            backgroundColor = if (isSelected) colors.black else Color.White,
                            borderColor = if (isSelected) Color.Transparent else colors.gray200
                        )
                    }
                }
            }

            SubHeaderText("소요시간", Modifier.padding(top = 41.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(Color.White)
                    .height(48.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = if (uiState.time == 0) "" else uiState.time.toString(),
                        onValueChange = { newValue ->
                            val timeValue = newValue.toIntOrNull() ?: 0
                            viewModel.updateTime(timeValue)
                        },
                        modifier = Modifier.width(50.dp),
                        textStyle = TextStyle(
                            fontFamily = PretendardSemiBold,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            lineHeight = 22.sp,
                            textAlign = TextAlign.End,
                            color = colors.black
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "시간",
                        style = typography.b1SemiBold,
                        color = colors.gray700
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(colors.gray200)
            )

            Spacer(modifier = Modifier.weight(1f))

            NoostakButton(
                text = "다음",
                textColor = colors.white,
                buttonColor = if (uiState.appointName.isNotBlank() &&
                    uiState.category.isNotBlank() &&
                    uiState.time > 0
                ) colors.black else colors.gray500,
                onButtonClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                isEnabled = uiState.appointName.isNotBlank() &&
                        uiState.category.isNotBlank() &&
                        uiState.time > 0
            )
        }
    }
}

@Composable
fun HeaderText(text: String, modifier: Modifier = Modifier) {
    val typography = NoostakTheme.typography
    val colors = NoostakTheme.colors

    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 26.dp),
        text = text,
        style = typography.h4SemiBold,
        textAlign = TextAlign.Start,
        color = colors.gray900
    )
}

@Composable
fun SubHeaderText(text: String, modifier: Modifier) {
    val typography = NoostakTheme.typography
    val colors = NoostakTheme.colors

    Text(
        modifier = modifier,
        text = text,
        style = typography.c2SemiBold,
        color = colors.gray900
    )
}

@Composable
fun ProgressBar(progressBar: List<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp)
    ) {
        progressBar.forEachIndexed { index, isLong ->
            Image(
                painter = painterResource(
                    id = if (isLong) R.drawable.ic_calendar_progress_long else R.drawable.ic_calendar_progress_short
                ),
                contentDescription = null,
                modifier = if (isLong) Modifier.height(16.dp) else Modifier.size(16.dp)
            )
            if (index < progressBar.size - 1) Spacer(modifier = Modifier.width(10.dp))
        }
    }
}