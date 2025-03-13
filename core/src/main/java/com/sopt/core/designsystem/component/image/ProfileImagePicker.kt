package com.sopt.core.designsystem.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.sopt.core.R
import com.sopt.core.designsystem.theme.NoostakAndroidTheme
import com.sopt.core.extension.noRippleClickable
import com.sopt.core.type.ProfileType

@Composable
fun ProfileImagePicker(
    selectedImageUri: String?,
    onCameraBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
    profileType: ProfileType = ProfileType.PROFILE
) {
    Box(
        modifier = modifier.noRippleClickable {
            onCameraBtnClick()
        }
    ) {
        GlideImage(
            imageModel = {
                selectedImageUri?.takeIf { it.isNotBlank() } ?: profileType.defaultImage
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .size(profileType.imageSize)
                .aspectRatio(1f)
                .clip(
                    when (profileType) {
                        ProfileType.PROFILE -> CircleShape
                        ProfileType.GROUP -> RoundedCornerShape(23.86.dp)
                    }
                ),
            previewPlaceholder = painterResource(id = profileType.defaultImage)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_profile_camera),
            contentDescription = stringResource(R.string.image_profile_image_picker_description),
            modifier = Modifier
                .size(35.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Preview
@Composable
fun ProfileImagePickerPreview() {
    NoostakAndroidTheme {
        ProfileImagePicker(
            selectedImageUri = null,
            onCameraBtnClick = {},
            profileType = ProfileType.PROFILE
        )
    }
}

@Preview
@Composable
fun GroupImagePickerPreview() {
    NoostakAndroidTheme {
        ProfileImagePicker(
            selectedImageUri = null,
            onCameraBtnClick = {},
            profileType = ProfileType.GROUP
        )
    }
}
