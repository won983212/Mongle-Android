package com.rtsoju.mongle

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.rtsoju.mongle.domain.model.CachePolicy
import dagger.hilt.android.HiltAndroidApp

// TODO 폴더 모듈화하기
@Suppress("unused")
@HiltAndroidApp
class Mongle : Application(), LifecycleEventObserver {

    override fun onCreate() {
        super.onCreate()

        // Initialize kakao sdk
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        // Get key hash
        // logKeyHash()

        // Get FCM Token
        // logFCMToken()

        logApiUrl()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_STOP) {
            CachePolicy.FETCHED_RESOURCES.clear()
        }
    }

    private fun logApiUrl() {
        Log.d("API", BASE_URL)
    }

    private fun logKeyHash() {
        Log.d("KeyHash", Utility.getKeyHash(this))
    }

    private fun logFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            Log.d("FCM", task.result)
        })
    }
}