package com.sopt.core.designsystem.component.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.domain.entity.IdentityEntity

@Composable
fun UnavailableUserChips(
    members: List<String>,
    myIdentity: IdentityEntity
) {
    members.forEachIndexed { index, member ->
        val isMeUnavailable = index == 0 && myIdentity.availability == "UNAVAILABLE" && myIdentity.name == member
        NoostakUserChip(
            text = if (isMeUnavailable) stringResource(R.string.user_chip_me) else member,
            textColor = NoostakTheme.colors.gray800,
            backgroundColor = if (isMeUnavailable) NoostakTheme.colors.blue200 else NoostakTheme.colors.gray200,
            borderColor = if (isMeUnavailable) NoostakTheme.colors.blue200 else NoostakTheme.colors.gray200
        )
    }
}
