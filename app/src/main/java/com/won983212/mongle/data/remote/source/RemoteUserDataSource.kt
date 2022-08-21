package com.won983212.mongle.data.remote.source

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.api.UserApi
import com.won983212.mongle.data.remote.api.safeApiCall
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val api: UserApi
) {

    // TODO mock data
    @Suppress("UNUSED_PARAMETER")
    fun getUserInfo(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): User = User("소마")

    /*suspend fun getUserInfo(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): User? {
        return safeApiCall(callback) {
            api.getUserInfo(kakaoToken)
        }
    }*/

    suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? {
        return safeApiCall(callback) {
            api.login(kakaoToken)
        }
    }
}