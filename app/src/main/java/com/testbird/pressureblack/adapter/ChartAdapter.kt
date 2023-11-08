package com.testbird.pressureblack.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testbird.pressureblack.R
import com.testbird.pressureblack.contacts.dp2px
import com.testbird.pressureblack.contacts.getFormatTimeRecordChart
import com.testbird.pressureblack.contacts.getRecordDataByLevel
import com.testbird.pressureblack.databinding.LayoutChartItemBinding

class ChartAdapter (
    private val context: Context,
    private val list: List<RecordEntity>,
    private val max: Int,
    private val min: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var viewHeight = 178
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemVH(LayoutChartItemBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemVH) {
            list[position].let {
                holder.binding.time.text = it.time.getFormatTimeRecordChart()
                holder.binding.diaSys.text = "${it.diastolic}-${it.systolic}"

                val h = (it.systolic - it.diastolic) / (max - min).toFloat()
                val translationY = (((max + min) / 2.0f - (it.systolic + it.diastolic) / 2.0f) / (max - min).toFloat())
                holder.binding.level.layoutParams.height = if (it.systolic <= it.diastolic) {
                    1
                } else (viewHeight * h).toInt().dp2px(context)
                holder.binding.level.translationY = (translationY * viewHeight).dp2px(context)
                holder.binding.diaSys.translationY = (translationY * viewHeight).dp2px(context)

                holder.binding.time.translationY = (0.6f * viewHeight).dp2px(context)

                getRecordDataByLevel(it){_,_,_,color->
                    ColorStateList.valueOf(context.getColor(color)).let {
                        holder.binding.level.imageTintList = it
                        holder.binding.diaSys.setTextColor(it)
                    }
                }
            }
        }
    }

    inner class ItemVH(val binding: LayoutChartItemBinding) : RecyclerView.ViewHolder(binding.root)
}