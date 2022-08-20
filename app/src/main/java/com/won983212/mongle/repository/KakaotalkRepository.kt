package com.won983212.mongle.repository

import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.model.MessageResult

interface KakaotalkRepository {

    /**
     * 카카오톡 텍스트를 보내면 백엔드 단에서 s3 스토리지에 올린다.
     */
    suspend fun upload(
        callback: RequestLifecycleCallback,
        content: ByteArray
    ): MessageResult?
}