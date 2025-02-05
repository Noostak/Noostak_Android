package com.sopt.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.core.designsystem.component.button.NoostakBottomButton
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.designsystem.theme.NoostakTheme
import com.sopt.presentation.R

@Composable
fun OnboardingRoute(
    authId: String,
    navigateToSignUp: (String) -> Unit
) {
    OnboardingScreen(
        onNextButtonClick = { navigateToSignUp(authId) }
    )
}

@Composable
fun OnboardingScreen(
    onNextButtonClick: () -> Unit
) {
    val pages = listOf(
        BoardingPage(
            imageRes = R.drawable.ic_onboarding_1,
            subtitle = stringResource(R.string.tv_onboarding_subtitle_1),
            title = stringResource(R.string.tv_onboarding_title_1)
        ),
        BoardingPage(
            imageRes = R.drawable.ic_onboarding_2,
            subtitle = stringResource(R.string.tv_onboarding_subtitle_2),
            title = stringResource(R.string.tv_onboarding_title_2)
        ),
        BoardingPage(
            imageRes = R.drawable.ic_onboarding_3,
            subtitle = stringResource(R.string.tv_onboarding_subtitle_3),
            title = stringResource(R.string.tv_onboarding_title_3)
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NoostakTheme.colors.white),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        HorizontalPager(
            state = pagerState
        ) { page ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    imageVector = ImageVector.vectorResource(id = pages[page].imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    modifier = Modifier
                        .padding(top = 30.dp),
                    text = pages[page].subtitle,
                    color = NoostakTheme.colors.blue700,
                    style = NoostakTheme.typography.b1SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            bottom = 26.3.dp
                        ),
                    text = pages[page].title,
                    color = NoostakTheme.colors.gray900,
                    style = NoostakTheme.typography.h322SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            Modifier
                .weight(1f)
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) NoostakTheme.colors.blue600 else NoostakTheme.colors.gray200
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
        NoostakBottomButton(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.horizontal_padding),
                vertical = dimensionResource(id = R.dimen.vertical_padding)
            ),
            text = stringResource(R.string.btn_group_create_next),
            activateColor = NoostakTheme.colors.blue600,
            deactivateColor = NoostakTheme.colors.gray500,
            onButtonClick = onNextButtonClick
        )
    }
}

@Immutable
data class BoardingPage(
    val imageRes: Int,
    val subtitle: String,
    val title: String
)

@Preview
@Composable
fun BoardingScreenPreview() {
    NoostakAndroidTheme {
        OnboardingScreen(
            onNextButtonClick = {}
        )
    }
}
