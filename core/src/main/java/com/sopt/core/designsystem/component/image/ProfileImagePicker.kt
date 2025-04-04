package com.sopt.core.designsystem.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
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
import com.sopt.core.type.ImagePickerType

@Composable
fun ProfileImagePicker(
    imagePickerType: ImagePickerType,
    selectedImageUri: String?,
    onCameraBtnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.noRippleClickable {
            onCameraBtnClick()
        }
    ) {
        GlideImage(
            imageModel = {
                selectedImageUri?.takeIf { it.isNotBlank() } ?: imagePickerType.profileImage
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .size(imagePickerType.size)
                .aspectRatio(1f)
                .clip(imagePickerType.shape),
            previewPlaceholder = painterResource(id = imagePickerType.profileImage),
            failure = {
                Image(
                    painter = painterResource(id = imagePickerType.profileImage),
                    contentDescription = null,
                    modifier = modifier
                        .size(imagePickerType.size)
                        .aspectRatio(1f)
                        .clip(imagePickerType.shape)
                )
            }
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

@Preview(showBackground = true)
@Composable
fun ProfileImagePickerPreview() {
    NoostakAndroidTheme {
        ProfileImagePicker(
            imagePickerType = ImagePickerType.USER,
            selectedImageUri = null,
            onCameraBtnClick = {}
        )
    }
}
