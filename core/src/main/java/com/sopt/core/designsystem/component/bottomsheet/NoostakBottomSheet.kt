package com.sopt.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.theme.NoostakTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoostakBottomSheet(
    content: @Composable () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    val sheetHeight = LocalConfiguration.current.screenHeightDp.dp * 47 / 65

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = NoostakTheme.colors.white,
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetHeight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp, bottom = 7.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 36.dp, height = 4.dp)
                        .background(NoostakTheme.colors.gray500, shape = RoundedCornerShape(50))
                )
            }
            content()
        }
    }
}
