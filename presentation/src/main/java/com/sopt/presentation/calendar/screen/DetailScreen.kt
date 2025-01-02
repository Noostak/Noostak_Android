package com.sopt.presentation.calendar.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.component.button.NoostakButton
import com.sopt.core.designsystem.component.calendar.NoostakCalendar
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R

@Composable
fun DetailScreen(onNextClick: () -> Unit, onBackClick: () -> Unit) {
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var isSingleDateMode by remember { mutableStateOf(false) }

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
            ProgressBar(progressBar = listOf(false, true, false))

            HeaderText(text = "약속을 생성할 기간을\n선택해주세요")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 23.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "하루씩 설정하기",
                    style = typography.b2Regular,
                    textAlign = TextAlign.Start,
                    color = colors.gray900
                )
                Image(
                    painter = painterResource(
                        id = if (isSingleDateMode) R.drawable.ic_calendar_toggle_on
                        else R.drawable.ic_calendar_toggle_off
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clickable {
                            isSingleDateMode = !isSingleDateMode
                        }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.gray200)
            )
            Spacer(modifier = Modifier.height(17.dp))

            NoostakCalendar(
                start = startDate,
                end = endDate,
                isSingleDate = isSingleDateMode,
                isRangeSelected = { start, end ->
                    startDate = start
                    endDate = end
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            NoostakButton(
                text = "다음",
                textColor = colors.white,
                buttonColor = if (startDate.isNotEmpty() && endDate.isNotEmpty()) colors.black else colors.gray500,
                onButtonClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                isEnabled = startDate.isNotEmpty() && endDate.isNotEmpty()
            )
        }
    }
}

