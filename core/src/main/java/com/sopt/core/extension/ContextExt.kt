package com.sopt.core.extension

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes

fun Context.toast(@StringRes message: Int) {
    Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
}

fun Context.stringToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(@StringRes message: Int) {
    Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
}

fun Context.stringOf(@StringRes message: Int): String {
    return getString(message)
}

fun Context.launchImagePicker(
    galleryLauncher: ActivityResultLauncher<String>,
    photoPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        // API 33 미만: 갤러리 실행
        galleryLauncher.launch("image/*")
    } else {
        // API 33 이상: 포토피커 실행 (이미지만 선택)
        photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}
