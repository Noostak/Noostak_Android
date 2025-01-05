package com.sopt.presentation.groupCreate.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.presentation.R

@Composable
fun GroupProfileImagePicker(
    selectedImageUri: String?,
    onCameraBtnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        GlideImage(
            imageModel = { selectedImageUri ?: R.drawable.ic_group_create_profile },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .size(112.dp)
                .aspectRatio(1f)
                .clip(CircleShape),
            previewPlaceholder = painterResource(id = R.drawable.ic_group_create_profile)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_group_create_camera),
            contentDescription = stringResource(R.string.image_group_profile_image_picker_description),
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.BottomEnd)
                .noRippleClickable {
                    onCameraBtnClick()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GroupProfileImagePickerPreview() {
    NoostakAndroidTheme {
        GroupProfileImagePicker(selectedImageUri = null, onCameraBtnClick = {})
    }
}
