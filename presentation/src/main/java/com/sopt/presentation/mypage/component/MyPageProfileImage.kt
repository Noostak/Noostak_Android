package com.sopt.presentation.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme

@Composable
fun MyPageProfileImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    if (!imageUrl.isNullOrBlank()) {
        GlideImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = modifier.clip(CircleShape),
            previewPlaceholder = painterResource(id = R.drawable.ic_user_profile),
            failure = {
                Image(
                    painter = painterResource(id = R.drawable.ic_user_profile),
                    contentDescription = null,
                    modifier = modifier.clip(CircleShape)
                )
            }
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_user_profile),
            contentDescription = null,
            modifier = modifier.clip(CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageProfileImagePreview() {
    NoostakAndroidTheme {
        MyPageProfileImage(
            imageUrl = null,
            modifier = Modifier
        )
    }
}
