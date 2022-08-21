package com.won983212.mongle.data.remote.source

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.remote.api.RequestErrorType
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.api.UserApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

internal class UserDataSourceTest {

    private val api: UserApi = FakeUserApi()
    private val callback: RequestLifecycleCallback = FakeRequestLifecycleCallback()

    @Test
    fun login() = runBlocking {
        val dataSource = RemoteUserDataSource(api)
        val token = dataSource.login(callback, OAuthLoginToken("abcd", "abcd"))

        Assert.assertEquals(137, token?.accessToken?.length)
        Assert.assertEquals(137, token?.refreshToken?.length)
    }

    class FakeRequestLifecycleCallback : RequestLifecycleCallback {
        override fun onStart() {
        }

        override fun onComplete() {
        }

        override fun onError(requestErrorType: RequestErrorType, msg: String) {
        }
    }

    class FakeUserApi : UserApi {
        override suspend fun login(token: OAuthLoginToken): OAuthLoginToken =
            OAuthLoginToken(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMzc0MzE2MjE5IiwiaWF0IjoxNjYxMDA2NDA3LCJleHAiOjE2NjEwMDgyMDd9.UEGjHWVzIxMcR4MmDBmgfT7LzS3IgXqNnkjCODWumt0",
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMzc0MzE2MjE5IiwiaWF0IjoxNjYxMDA2NDA3LCJleHAiOjE2NjEwMDgyMDd9.UEGjHWVzIxMcR4MmDBmgfT7LzS3IgXqNnkjCODWumt0"
            )

        override suspend fun getUserInfo(token: OAuthLoginToken): User =
            User("USERNAME")
    }
}