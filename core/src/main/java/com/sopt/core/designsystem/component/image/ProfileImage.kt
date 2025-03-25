package com.sopt.core.designsystem.component.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme

@Composable
fun ProfileImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    @DrawableRes placeholder: Int
) {
    if (!imageUrl.isNullOrBlank()) {
        GlideImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = modifier.clip(shape),
            previewPlaceholder = painterResource(id = placeholder),
            failure = {
                Image(
                    painter = painterResource(id = placeholder),
                    contentDescription = null,
                    modifier = modifier.clip(shape)
                )
            }
        )
    } else {
        Image(
            painter = painterResource(id = placeholder),
            contentDescription = null,
            modifier = modifier.clip(shape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileImagePreview() {
    NoostakAndroidTheme {
        ProfileImage(
            imageUrl = null,
            placeholder = R.drawable.ic_user_profile
        )
    }
}
