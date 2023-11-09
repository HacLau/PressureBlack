package com.testbird.pressureblack.contacts

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.testbird.pressureblack.R
import com.testbird.pressureblack.adapter.RecordEntity
import java.util.Calendar

fun Int.dp2px(context: Context):Int{
    return (context.resources.displayMetrics.density * this).toInt()
}

fun Float.dp2px(context: Context):Float{
    return (context.resources.displayMetrics.density * this)
}

fun Long.getFormatTimeMain(): String {
    return Calendar.getInstance().let {
        it.timeInMillis = this
        "${(it.get(Calendar.MONTH) + 1).two()}.${it.get(Calendar.DATE).two()}"
    }
}

fun Long.getFormatTimeRecordItem(): String {
    return Calendar.getInstance().let {
        it.timeInMillis = this
        "${it.get(Calendar.YEAR)}-${(it.get(Calendar.MONTH) + 1).two()}-${it.get(Calendar.DATE).two()} ${
            it.get(Calendar.HOUR_OF_DAY).two()
        }:${it.get(Calendar.MINUTE).two()}"
    }
}

fun Long.getFormatTimeRecordChart(): String {

    return Calendar.getInstance().let {
        it.timeInMillis = this
        "${(it.get(Calendar.MONTH) + 1).two()}-${it.get(Calendar.DATE).two()}"
    }
}

fun getCurrentYearMonthHaveDay(year: Int, month: Int): Int {
    return Calendar.getInstance().apply {
        set(year, month, 1)
        add(Calendar.DATE, -1)
    }.get(Calendar.DATE)
}

fun Long.getCurrentDateArrayByMill(): IntArray {
    return Calendar.getInstance().let {
        it.timeInMillis = this
        intArrayOf(
            it.get(Calendar.YEAR),
            it.get(Calendar.MONTH) + 1,
            it.get(Calendar.DATE),
            it.get(Calendar.HOUR_OF_DAY),
            it.get(Calendar.MINUTE)
        )
    }
}

fun IntArray.getCurrentDateMillByArray(): Long {
    return Calendar.getInstance().let {
        it.set(Calendar.YEAR, this[0])
        it.set(Calendar.MONTH, this[1] - 1)
        it.set(Calendar.DATE, this[2])
        it.set(Calendar.HOUR_OF_DAY, this[3])
        it.set(Calendar.MINUTE, this[4])
        it.timeInMillis
    }

}

fun Int.getDaysMills():Long{
    return Calendar.getInstance().let {
        it.set(Calendar.DATE, it.get(Calendar.DATE) + this)
        it.set(Calendar.HOUR_OF_DAY, 0)
        it.set(Calendar.MINUTE, 0)
        it.set(Calendar.SECOND, 0)
        it.timeInMillis
    }
}

fun Int.getDayOfWeek(weekOffset:Int,firstDayOfWeek: Int = Calendar.SUNDAY):Long{

    if (this > Calendar.SATURDAY || this < Calendar.SUNDAY){
        return 0L
    }
    if (firstDayOfWeek > Calendar.SATURDAY || firstDayOfWeek < Calendar.SUNDAY){
        return 0L
    }
    return Calendar.getInstance().let {
        it.firstDayOfWeek = firstDayOfWeek
        it.add(Calendar.WEEK_OF_MONTH,weekOffset)
        it.set(Calendar.DAY_OF_WEEK,this)
        it.set(Calendar.HOUR,0)
        it.set(Calendar.MINUTE,0)
        it.set(Calendar.SECOND,0)
        it.set(Calendar.MILLISECOND,0)
        it.timeInMillis
    }

}

fun Int.getMonthStart():Long{
    return Calendar.getInstance().let {
        it.set(Calendar.MONTH,it.get(Calendar.MONTH) + this)
        it.set(Calendar.DATE, 1)
        it.set(Calendar.HOUR_OF_DAY, 0)
        it.set(Calendar.MINUTE, 0)
        it.set(Calendar.SECOND, 0)
        it.timeInMillis
    }
}

fun Long.getLastYearToDay():Long{
    return Calendar.getInstance().let {
        it.timeInMillis = this
        it.add(Calendar.YEAR,-1)
        it.timeInMillis
    }
}

fun Int.two():String{
    return if (this < 10)
        "0${this}"
    else
        "$this"
}

fun String.log(){
    Log.e("Number",this)
}

fun String.toast(context: Context){
    Toast.makeText(context,this, Toast.LENGTH_SHORT).show()
}

fun getRecordDataByLevel(recordEntity: RecordEntity, onLoaded:(Int, Int, Int, Int)->Unit) {
    var title = R.string.title_h0
    var content = R.string.content_h0
    var des = R.string.des_h0
    var color = R.color.record_level_0
    when (recordEntity.level) {
        0 -> {
            title = R.string.title_h0
            content = R.string.content_h0
            des = R.string.des_h0
            color = R.color.record_level_0
        }

        1 -> {
            title = R.string.title_h1
            content = R.string.content_h1
            des = R.string.des_h1
            color = R.color.record_level_1
        }

        2 -> {
            title = R.string.title_h2
            content = R.string.content_h2
            des = R.string.des_h2
            color = R.color.record_level_2
        }

        3 -> {
            title = R.string.title_h3
            content = R.string.content_h3
            des = R.string.des_h3
            color = R.color.record_level_3

        }

        4 -> {
            title = R.string.title_h4
            content = R.string.content_h4
            des = R.string.des_h4
            color = R.color.record_level_4

        }

        5 -> {
            title = R.string.title_h5
            content = R.string.content_h5
            des = R.string.des_h5
            color = R.color.record_level_5
        }
    }
    onLoaded.invoke(title,content,des,color)
}