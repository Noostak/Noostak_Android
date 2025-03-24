package com.sopt.core.designsystem.component.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.IdentityEntity

@Composable
fun AvailableUserChips(
    members: List<String>,
    myIdentity: IdentityEntity
) {
    members.forEachIndexed { index, member ->
        val isMeAvailable = index == 0 && myIdentity.availability == "AVAILABLE" && myIdentity.name == member
        NoostakUserChip(
            text = if (isMeAvailable) stringResource(id = R.string.user_chip_me) else member,
            textColor = NoostakTheme.colors.black,
            backgroundColor = if (isMeAvailable) NoostakTheme.colors.blue200 else NoostakTheme.colors.white,
            borderColor = NoostakTheme.colors.blue200
        )
    }
}
