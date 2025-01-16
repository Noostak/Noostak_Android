package com.sopt.presentation.mypage.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme

@Composable
fun MyPageProfileEditButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = com.sopt.presentation.R.dimen.horizontal_padding)),
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(vertical = 10.dp),
        border = BorderStroke(1.dp, NoostakTheme.colors.gray200)
    ) {
        Text(
            text = text,
            style = NoostakTheme.typography.c3SemiBold,
            color = NoostakTheme.colors.gray900
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageProfileEditButtonPreview() {
    NoostakAndroidTheme {
        MyPageProfileEditButton(
            text = "프로필 수정",
            onClick = {}
        )
    }
}
