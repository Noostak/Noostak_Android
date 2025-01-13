package com.sopt.presentation.groupDetail.groupMember

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sopt.core.designsystem.component.topappbar.NoostakTopAppBar
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.core.extension.showIf
import com.sopt.domain.entity.GroupMembersEntity
import com.sopt.presentation.R
import com.sopt.presentation.groupDetail.GroupDetailHeader

@Composable
fun GroupMemberRoute(
    groupId: Long,
    navigateUp: () -> Unit,
    groupMemberViewModel: GroupMemberViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = groupMemberViewModel.sideEffects) {
        groupMemberViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is GroupMemberSideEffect.NavigateUp -> navigateUp()
            }
        }
    }
    GroupMemberScreen(
        groupId = groupId,
        groupMembers = groupMemberViewModel.mockGroupMembers,
        onBackButtonClick = groupMemberViewModel::navigateUp
    )
}

@Composable
fun GroupMemberScreen(
    groupId: Long,
    groupMembers: GroupMembersEntity,
    onBackButtonClick: () -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            NoostakTopAppBar(
                title = stringResource(R.string.appbar_group_member),
                modifier = Modifier,
                isIconVisible = false,
                onBackButtonClick = onBackButtonClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding))
        ) {
            GroupDetailHeader(
                groupId = groupId,
                groupImage = groupMembers.groupImage,
                groupName = groupMembers.groupName
            )
            Text(
                modifier = Modifier.padding(top = 9.dp),
                text = stringResource(
                    R.string.tv_group_detail_member,
                    groupMembers.groupMemberCount
                ),
                color = NoostakTheme.colors.gray800,
                style = NoostakTheme.typography.b2Regular
            )
            GroupMemberHeader(text = stringResource(R.string.header_group_member_leader))
            GroupMemberItem(
                size = 72.dp,
                profileImage = groupMembers.groupLeader.groupLeaderImage,
                name = groupMembers.groupLeader.groupLeaderName
            )
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(
                modifier = Modifier.clip(CircleShape),
                thickness = 1.5.dp,
                color = NoostakTheme.colors.gray200
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                GroupMemberHeader(text = stringResource(R.string.header_group_member_member))
                GroupMemberHeader(text = "(${groupMembers.groupMembers.size}/50)")
            }
            Text(
                modifier = Modifier
                    .showIf(groupMembers.groupMembers.isEmpty())
                    .fillMaxWidth()
                    .padding(top = 42.dp),
                text = stringResource(R.string.placeholder_group_member),
                color = NoostakTheme.colors.gray900,
                style = NoostakTheme.typography.b2Regular,
                textAlign = TextAlign.Center
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .showIf(groupMembers.groupMembers.isNotEmpty())
                    .fillMaxSize(),
                columns = GridCells.Fixed(5),
                verticalArrangement = Arrangement.spacedBy(17.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(groupMembers.groupMembers) {
                    GroupMemberItem(
                        size = 61.dp,
                        profileImage = it.groupMemberImage,
                        name = it.groupMemberName
                    )
                }
            }
        }
    }
}

@Composable
fun GroupMemberHeader(
    text: String
) {
    Text(
        modifier = Modifier.padding(top = 20.dp, bottom = 14.dp),
        text = text,
        color = NoostakTheme.colors.gray700,
        style = NoostakTheme.typography.b4SemiBold
    )
}

@Composable
fun GroupMemberItem(
    size: Dp,
    profileImage: String,
    name: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(size),
            model = profileImage,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_profile),
            error = painterResource(id = R.drawable.ic_profile),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = name,
            color = NoostakTheme.colors.gray900,
            style = NoostakTheme.typography.c2SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupMemberScreenPreview() {
    NoostakAndroidTheme {
        val groupMemberViewModel: GroupMemberViewModel = hiltViewModel()
        GroupMemberScreen(
            groupId = 1,
            groupMembers = groupMemberViewModel.mockGroupMembers,
            onBackButtonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupMemberItemPreview() {
    NoostakAndroidTheme {
        GroupMemberItem(
            size = 61.dp,
            profileImage = "https://avatars.githubusercontent.com/u/91470334?v=4",
            name = "이가을"
        )
    }
}
