package com.sopt.noostak

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setTimber()
        initKakao()
    }

    private fun setTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKakao() {
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }
}
