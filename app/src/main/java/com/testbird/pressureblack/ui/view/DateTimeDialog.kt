package com.testbird.pressureblack.ui.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import com.testbird.pressureblack.contacts.getCurrentDateArrayByMill
import com.testbird.pressureblack.contacts.getCurrentDateMillByArray
import com.testbird.pressureblack.contacts.getCurrentYearMonthHaveDay
import com.testbird.pressureblack.contacts.toast
import com.testbird.pressureblack.contacts.two
import com.testbird.pressureblack.databinding.LayoutSetTimeBinding


class DateTimeDialog(
    private val mContext: Context,
    private val timeMills: Long,
    private var cancel: () -> Unit = {},
    private var confirm: (Long) -> Unit = {}
) : AlertDialog(mContext) {
    private lateinit var binding: LayoutSetTimeBinding
    private lateinit var dateArray: IntArray
    private lateinit var pkYear:DateTimePicker
    private lateinit var pkMonth:DateTimePicker
    private lateinit var pkDay:DateTimePicker
    private lateinit var pkHour:DateTimePicker
    private lateinit var pkMinute:DateTimePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutSetTimeBinding.inflate(LayoutInflater.from(mContext))
        initPK()
        setContentView(binding.root)
        this.window?.setGravity(Gravity.CENTER)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.window?.setDimAmount(0.5f)
        initView()
        initData()
    }

    private fun initPK() {
        pkYear = DateTimePicker(context, visibleNumberCount = 5, onSelect = {
            dateArray[0] = it.toInt()
            setDay(getDayPickerDataList(dateArray[0], dateArray[1]))
        })
        pkMonth = DateTimePicker(context, onSelect = {
            dateArray[1] = it.toInt()
            setDay(getDayPickerDataList(dateArray[0], dateArray[1]))
        })
        pkDay = DateTimePicker(context, onSelect = {
            dateArray[2] = it.toInt()
        })
        pkHour = DateTimePicker(context, onSelect = {
            dateArray[3] = it.toInt()
        })
        pkMinute = DateTimePicker(context, visibleNumberCount = 5,onSelect = {
            dateArray[4] = it.toInt()
        })
        binding.groupYear.addView(pkYear)
        binding.groupMonth.addView(pkMonth)
        binding.groupDay.addView(pkDay)
        binding.groupHour.addView(pkHour)
        binding.groupMinute.addView(pkMinute)
    }

    private fun initData() {
        dateArray = timeMills.getCurrentDateArrayByMill()
        pkYear.setData(yearList, yearList.indexOf(dateArray[0].two()))
        pkMonth.setData(monthList, monthList.indexOf(dateArray[1].two()))
        setDay(getDayPickerDataList(dateArray[0], dateArray[1]))
        pkHour.setData(hourList, hourList.indexOf(dateArray[3].two()))
        pkMinute.setData(minuteList, minuteList.indexOf(dateArray[4].two()))
    }

    private fun initView() {
        binding.dialogCancel.setOnClickListener {
            dismiss()
            cancel.invoke()
        }
        binding.dialogConfirm.setOnClickListener {
            dateArray.getCurrentDateMillByArray().let {
                if (it > System.currentTimeMillis()) {
                    "You choice time is exceed current time,please choice again".toast(context)
                    return@setOnClickListener
                } else {
                    dismiss()
                    confirm.invoke(it)
                }
            }
        }
    }

    private fun setDay(list: MutableList<String>) {
        pkDay.setData(
            list, if (list.size < dateArray[2]) {
                dateArray[2] = list[list.size - 1].toInt()
                list.size - 1
            } else list.indexOf(dateArray[2].two())
        )
    }
}
private fun getDayPickerDataList(year: Int, month: Int): MutableList<String> {
    return mutableListOf<String>().apply {
        (1..getCurrentYearMonthHaveDay(year, month)).forEach {
            add(it.two())
        }
    }
}

val yearList: MutableList<String> = mutableListOf<String>().apply {
    (1970..2099).forEach {
        add(it.two())
    }
}
val monthList: MutableList<String> = mutableListOf<String>().apply {
    (1..12).forEach {
        add(it.two())
    }
}
val hourList: MutableList<String> = mutableListOf<String>().apply {
    (0..23).forEach {
        add(it.two())
    }
}
val minuteList: MutableList<String> = mutableListOf<String>().apply {
    (0..60).forEach {
        add(it.two())
    }
}