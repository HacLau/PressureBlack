package com.testbird.pressureblack.contacts

import com.testbird.pressureblack.adapter.Record
import com.testbird.pressureblack.adapter.RecordEntity
import com.testbird.pressureblack.adapter.filterRecordList
import java.util.Calendar

fun List<RecordEntity>.filterRecordList(currentFilter: String): List<RecordEntity> {
    return this.filter {
        when (filterRecordList.indexOf(currentFilter)) {
            0 -> {
                it.time > 0.getDaysMills()
            }

            1 -> {
                it.time < Calendar.SATURDAY.getDayOfWeek(0) && it.time > Calendar.SUNDAY.getDayOfWeek(0)
            }

            2 -> {
                it.time > (-6).getDaysMills()
            }

            3 -> {
                it.time > 0.getMonthStart()
            }

            4 -> {
                it.time > (-1).getMonthStart() && it.time < 0.getMonthStart()
            }

            else -> {
                it.time < System.currentTimeMillis()
            }
        }
    }
}

fun List<RecordEntity>.getRecordList(data: (Int, Int) -> Unit): MutableList<Record> {
    return mutableListOf<Record>().let {
        it.add(Record(0, chartList = this))
        var sys = 0
        var dia = 0
        val list = this.subList(0, if (this.isEmpty()) 0 else if (this.size > 5) 5 else this.size)
        list.forEach { entity ->
            it.add(Record(1, entity = entity))
        }
        this.forEach {entity->
            sys += entity.systolic
            dia += entity.diastolic
        }
        data.invoke(if (this.isNotEmpty()) sys / this.size else sys, if (this.isNotEmpty()) dia / this.size else dia)
        it
    }
}