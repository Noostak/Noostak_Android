package com.sopt.presentation.calendar.calendarInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.component.chip.NoostakChip
import com.sopt.core.designsystem.component.textfield.NoostakTextField
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.type.TextFieldType
import com.sopt.presentation.R
import com.sopt.core.designsystem.component.progressbar.NoostakProgressBar
import com.sopt.core.designsystem.component.text.NoostakHeaderText
import com.sopt.core.designsystem.component.text.NoostakSubHeaderText

@Composable
fun CalendarInfoRoute(
    navigateToPeriod: (String, String, Int) -> Unit,
    calendarInfoViewModel: CalendarInfoViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = calendarInfoViewModel.sideEffects) {
        calendarInfoViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is CalendarInfoSideEffect.NavigateToPeriod -> {
                    navigateToPeriod(
                        sideEffect.appointmentName,
                        sideEffect.category,
                        sideEffect.time
                    )
                }
            }
        }
    }
    CalendarInfoScreen(
        onButtonClick = calendarInfoViewModel::navigateToCalendarPeriod,
        categories = calendarInfoViewModel.categories
    )
}

@Composable
fun CalendarInfoScreen(
    onButtonClick: (String, String, Int) -> Unit,
    categories: List<String>
) {
    var appointmentName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    val typography = NoostakTheme.typography
    val colors = NoostakTheme.colors

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
        topBar = {
            NoostakTopAppBar(
                title = "약속 만들기",
                isIconVisible = false
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            NoostakProgressBar(progressBar = listOf(true, false, false))

            NoostakHeaderText(text = "약속 정보를 입력해주세요")

            NoostakSubHeaderText("약속 이름", Modifier.padding(top = 22.dp, bottom = 10.dp))

            NoostakTextField(
                textFieldType = TextFieldType.CALENDAR,
                value = appointmentName,
                textStyle = typography.b1SemiBold,
                cursorColor = colors.gray500,
                shape = RoundedCornerShape(10.dp),
                focusedBorderColor = colors.blue600,
                unfocusedBorderColor = colors.gray200,
                onValueChange = { newValue ->
                    if (newValue.length <= 20) appointmentName = newValue
                },
                maxLength = 20
            )

            NoostakSubHeaderText("약속 카테고리", Modifier.padding(top = 32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(11.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .clickable { selectedCategory = category }
                    ) {
                        NoostakChip(
                            text = category,
                            textStyle = typography.b4SemiBold,
                            textColor = if (isSelected) Color.White else colors.gray900,
                            backgroundColor = if (isSelected) colors.black else Color.White,
                            borderColor = if (isSelected) Color.Transparent else colors.gray200,
                            horizontalPaddingValues = 20.dp,
                            verticalPaddingValues = 8.dp
                        )
                    }
                }
            }

            NoostakSubHeaderText("소요시간", Modifier.padding(top = 41.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(Color.White),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.vertical_padding)),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = duration,
                        onValueChange = { duration = it },
                        modifier = Modifier.width(50.dp),
                        textStyle = typography.b1SemiBold,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "시간",
                        style = typography.b1SemiBold,
                        color = colors.gray700,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            NoostakBottomButton(
                text = "다음",
                onButtonClick = {
                    val time = duration.toIntOrNull() ?: 0
                    onButtonClick(appointmentName, selectedCategory, time)
                },
                isEnabled = appointmentName.isNotBlank() && selectedCategory.isNotBlank() && duration.isNotBlank(),
                deactivateColor = NoostakTheme.colors.gray500,
                activateColor = NoostakTheme.colors.gray900,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
