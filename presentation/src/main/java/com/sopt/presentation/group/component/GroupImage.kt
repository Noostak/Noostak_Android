package com.sopt.presentation.group.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.presentation.R

@Composable
fun GroupImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = dimensionResource(id = R.dimen.image_radius)
) {
    if (!imageUrl.isNullOrBlank()) {
        GlideImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = modifier.clip(RoundedCornerShape(cornerRadius)),
            previewPlaceholder = painterResource(id = R.drawable.ic_group_profile)
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_group_profile),
            contentDescription = null,
            modifier = modifier.clip(RoundedCornerShape(cornerRadius))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupImagePreview() {
    NoostakAndroidTheme {
        GroupImage(
            imageUrl = null,
            modifier = Modifier
        )
    }
}
