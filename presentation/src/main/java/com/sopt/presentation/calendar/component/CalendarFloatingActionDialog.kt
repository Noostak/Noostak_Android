package com.sopt.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.presentation.R
import com.sopt.presentation.group.component.GroupFloatingActionButtonItem

@Composable
fun CalendarFloatingActionDialog(
    onClick: () -> Unit,
    onDismissRequest: () -> Unit = {},
    onCreateGroupClick: () -> Unit,
    onEnterGroupClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable { onDismissRequest() }
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        end = dimensionResource(id = R.dimen.horizontal_padding),
                        top = 99.dp
                    )
                    .align(Alignment.TopEnd)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(46.dp)
                        .background(
                            color = NoostakTheme.colors.white,
                            shape = CircleShape
                        )
                        .noRippleClickable { onClick() }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(18.4.dp)
                            .align(Alignment.Center),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_group_fab_close),
                        contentDescription = null,
                        tint = NoostakTheme.colors.black
                    )
                }
                Spacer(modifier = Modifier.height(22.dp))
                Box(
                    modifier = Modifier.background(
                        color = NoostakTheme.colors.white,
                        shape = RoundedCornerShape(12.dp)
                    )
                ) {
                    Column(modifier = Modifier.padding(horizontal = 11.dp, vertical = 13.dp)) {
                        GroupFloatingActionButtonItem(
                            painter = painterResource(id = R.drawable.ic_group_fab_create),
                            text = stringResource(R.string.text_group_create_title)
                        ) {
                            onCreateGroupClick()
                        }
                        GroupFloatingActionButtonItem(
                            painter = painterResource(id = R.drawable.ic_group_fab_enter),
                            text = stringResource(R.string.text_group_fab_enter)
                        ) {
                            onEnterGroupClick()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupFloatingActionDialogPreview() {
    NoostakAndroidTheme {
        CalendarFloatingActionDialog(
            onClick = {},
            onCreateGroupClick = {},
            onEnterGroupClick = {}
        )
    }
}
