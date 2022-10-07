package com.won983212.mongle.data.repository

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.local.LocalCalendarDataSource
import com.won983212.mongle.data.source.remote.RemoteCalendarDataSource
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.CalendarDayPreview
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import com.won983212.mongle.data.util.CachePolicy
import com.won983212.mongle.domain.repository.CalendarRepository
import java.time.LocalDate
import java.time.YearMonth

internal class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource,
    private val localCalendarDataSource: LocalCalendarDataSource
) : CalendarRepository {

    override suspend fun updateDiary(date: LocalDate, text: String): Result<MessageResult> {
        localCalendarDataSource.updateDiary(date, text)
        return remoteCalendarDataSource.updateDiary(date, text)
    }

    override suspend fun updateEmotion(date: LocalDate, emotion: Emotion): Result<MessageResult> {
        localCalendarDataSource.updateEmotion(date, emotion)
        return remoteCalendarDataSource.updateEmotion(date, emotion)
    }

    // TODO Cache Policy 구현
    override suspend fun getCalendarDayMetadata(
        startMonth: YearMonth,
        endMonth: YearMonth,
        cachePolicy: CachePolicy
    ): Result<List<CalendarDayPreview>> {
        return cachePolicy.get(object : CachePolicy.CacheableResource<List<CalendarDayPreview>> {
            override suspend fun loadFromCache(): Result<List<CalendarDayPreview>> {
                return localCalendarDataSource.getCalendarDayMetadata(startMonth, endMonth)
            }

            override suspend fun saveCallResult(value: List<CalendarDayPreview>) {
                localCalendarDataSource.updateCalendarDayMetadata(value)
            }

            override suspend fun fetch(): Result<List<CalendarDayPreview>> {
                return remoteCalendarDataSource.getCalendarDayMetadata(
                    startMonth,
                    endMonth
                )
            }
        })
    }

    override suspend fun getCalendarDayDetail(
        date: LocalDate,
        cachePolicy: CachePolicy
    ): Result<CalendarDayDetail> {
        return cachePolicy.get(object : CachePolicy.CacheableResource<CalendarDayDetail> {
            override suspend fun loadFromCache(): Result<CalendarDayDetail> {
                return localCalendarDataSource.getCalendarDayDetail(date)
            }

            override suspend fun saveCallResult(value: CalendarDayDetail) {
                localCalendarDataSource.updateCalendarDayDetail(date, value)
            }

            override suspend fun fetch(): Result<CalendarDayDetail> {
                return remoteCalendarDataSource.getCalendarDayDetail(date)
            }
        })
    }

    override suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion,
        cachePolicy: CachePolicy
    ): Result<List<EmotionalSentence>> {
        return cachePolicy.get(object : CachePolicy.CacheableResource<List<EmotionalSentence>> {
            override suspend fun loadFromCache(): Result<List<EmotionalSentence>> {
                return localCalendarDataSource.getDayEmotionalSentences(date, emotion)
            }

            override suspend fun saveCallResult(value: List<EmotionalSentence>) {
                localCalendarDataSource.updateDayEmotionalSentences(date, value)
            }

            override suspend fun fetch(): Result<List<EmotionalSentence>> {
                return remoteCalendarDataSource.getDayEmotionalSentences(date, emotion)
            }
        })
    }
}