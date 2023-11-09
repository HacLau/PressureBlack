package com.testbird.pressureblack.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.testbird.pressureblack.contacts.getFormatTimeRecordItem
import com.testbird.pressureblack.contacts.getRecordDataByLevel
import com.testbird.pressureblack.contacts.log
import com.testbird.pressureblack.databinding.LayoutRecordChartBinding
import com.testbird.pressureblack.databinding.LayoutRecordItemBinding
import kotlinx.parcelize.Parcelize

class RecordAdapter(
    private val context: Context,
    private var list: List<Record>,
    private val onItemClick: (RecordEntity) -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> RecordChartViewHolder(LayoutRecordChartBinding.inflate(LayoutInflater.from(context),parent,false))
            else -> RecordItemViewHolder(LayoutRecordItemBinding.inflate(LayoutInflater.from(context),parent,false))
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            0 -> {
                if (holder is RecordChartViewHolder) {
                    list[position].chartList.let { chartList ->
                        holder.binding.apply {
                            getMaxAndMin(chartList) { max,num4,num3,num2, min ->
                                holder.binding.let {
                                    it.num1.text = "$max"
                                    it.num2.text = "$num4"
                                    it.num3.text = "$num3"
                                    it.num4.text = "$num2"
                                    it.num5.text = "$min"
                                }
                                holder.binding.recordChartRv.addItemDecoration(ItemDecoration(0))
                                holder.binding.recordChartRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                                holder.binding.recordChartRv.adapter = ChartAdapter(context, chartList, max, min)
                            }
                        }
                    }
                }
            }

            else -> {
                if (holder is RecordItemViewHolder) {
                    list[position].entity.toString().log()
                    list[position].entity?.let {
                        holder.binding.apply {
                            itemEdit.setOnClickListener { _ ->
                                onItemClick.invoke(it)
                            }
                            getRecordDataByLevel(it) { title, _,_,color ->
                                ColorStateList.valueOf(ContextCompat.getColor(context, color)).let {
                                    itemTitle.setTextColor(it)
                                    itemImage.imageTintList = it
                                }
                                itemTitle.text = context.getString(title)
                            }
                            itemTime.text = it.time.getFormatTimeRecordItem()
                            sysNumber.text = it.systolic.toString()
                            diasNumber.text = it.diastolic.toString()

                        }
                    }
                }
            }
        }
    }


    private fun getMaxAndMin(chartList: List<RecordEntity>, onLoaded: (Int, Int, Int, Int, Int) -> Unit) {
        var max: Int = if (chartList.isEmpty()) 320 else chartList[0].systolic
        var min: Int = if (chartList.isEmpty()) 20 else chartList[0].diastolic
        chartList.forEach {
            if (max - it.systolic < 0) {
                max = it.systolic
            }
            if (it.diastolic - min < 0) {
                min = it.diastolic
            }
        }
        max += 10
        min -= 7
        val dis = (max - min) / 4
        onLoaded.invoke(max, min + dis * 3, min + dis * 2, min + dis * 1, min)
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Record>) {
        this.list = list
        notifyDataSetChanged()
    }

}

class RecordChartViewHolder(var binding: LayoutRecordChartBinding) : RecyclerView.ViewHolder(binding.root)
class RecordItemViewHolder(var binding: LayoutRecordItemBinding) : RecyclerView.ViewHolder(binding.root)

@Parcelize
@Entity
data class RecordEntity(
    var systolic: Int = 90,
    var diastolic: Int = 60,
    var time: Long = System.currentTimeMillis(),
    var level: Int = 0,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable

data class Record(
    var type: Int,
    var chartList: List<RecordEntity> = mutableListOf(),
    var entity: RecordEntity? = null
)
