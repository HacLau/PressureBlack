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
                it.time > (-7).getDaysMills()
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
            sys += entity.systolic
            dia += entity.diastolic
            it.add(Record(1, entity = entity))
            it.toString().log()
        }
        data.invoke(if (list.isNotEmpty()) sys / list.size else sys, if (list.isNotEmpty()) dia / list.size else dia)
        it
    }
}