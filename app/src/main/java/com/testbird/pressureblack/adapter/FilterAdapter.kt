package com.testbird.pressureblack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.testbird.pressureblack.R
import com.testbird.pressureblack.appContext
import com.testbird.pressureblack.databinding.LayoutFilterItemBinding

class FilterAdapter(private val context: Context) : PagerAdapter() {
    override fun getCount(): Int = filterRecordList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = LayoutFilterItemBinding.inflate(LayoutInflater.from(context))
        binding.title.text = filterRecordList[position]
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}

val filterRecordList: MutableList<String> = mutableListOf(
    FilterRecordType.recent,
    FilterRecordType.week,
    FilterRecordType.seven,
    FilterRecordType.month,
    FilterRecordType.lastMonth
)

object FilterRecordType {
    val recent = appContext.getString(R.string.record_recent)
    val week = appContext.getString(R.string.record_week)
    val seven = appContext.getString(R.string.record_7day)
    val month = appContext.getString(R.string.record_month)
    val lastMonth = appContext.getString(R.string.record_last_month)
    val all = appContext.getString(R.string.record_all)
}