package com.won983212.mongle.data.source.entity

import com.google.common.truth.Truth.assertThat
import com.won983212.mongle.data.source.local.entity.CalendarDayEntity
import org.junit.Test
import java.time.LocalDate

internal class CalendarDayEntityTest{
    @Test
    fun is_id_hashcode_of_date_string(){
        val date = LocalDate.now()
        
        val entity = CalendarDayEntity(date, null, listOf(), "", "")

        assertThat(entity.id).isEqualTo(date.toString().hashCode().toLong())
    }
}