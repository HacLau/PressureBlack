package com.testbird.pressureblack.adapter

import android.content.Context
import android.os.Parcelable
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testbird.pressureblack.R
import com.testbird.pressureblack.appContext
import com.testbird.pressureblack.contacts.log
import com.testbird.pressureblack.databinding.LayoutInfoItemBinding
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import java.nio.charset.Charset

class InformationAdapter(
    private val context: Context,
    private val list: MutableList<InformationEntity>,
    private val onItemClick: (InformationEntity) -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return InformationViewHolder(LayoutInfoItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is InformationViewHolder) {
            list[position].let {
                holder.binding.apply {
                    itemIcon.setImageResource(infoIconList[it.icon])
                    itemBg.setBackgroundResource(infoItemImageList[it.image])
                    itemTitle.text = it.title
                    itemContent.text = it.content
                    root.setOnClickListener { _ ->
                        onItemClick.invoke(it)
                    }
                }

            }

        }
    }

}

class InformationViewHolder(var binding: LayoutInfoItemBinding) : RecyclerView.ViewHolder(binding.root)


@Parcelize
data class InformationEntity(
    var title: String,
    var content: String,
    var from: String,
    var image: Int,
    var icon: Int
) : Parcelable

val infoList by lazy {
    mutableListOf<InformationEntity>().apply {
        kotlin.runCatching {
            val infoBase64 = appContext.assets.open("aW5mb3JtYXRpb24uanNvbg").let {
                val buffer = ByteArray(it.available())
                it.read(buffer)
                it.close()
                String(buffer, Charset.defaultCharset())
            }
            val json = Base64.decode(infoBase64,Base64.DEFAULT).decodeToString()
            JSONArray(json).let { array ->
                for (i in 0 until array.length()) {
                    array.getJSONObject(i).let {
                        add(InformationEntity(it.getString("title"), it.getString("content"), it.getString("from"), (0..7).random(), (0..3).random()))
                    }
                }
            }
        }
    }
}

val infoImageList by lazy {
    mutableListOf<Int>().apply {
        add(R.mipmap.bg_information_image0)
        add(R.mipmap.bg_information_image1)
        add(R.mipmap.bg_information_image2)
        add(R.mipmap.bg_information_image3)
        add(R.mipmap.bg_information_image4)
        add(R.mipmap.bg_information_image5)
        add(R.mipmap.bg_information_image6)
        add(R.mipmap.bg_information_image7)
        add(R.mipmap.bg_information_image8)
    }
}

val infoItemImageList by lazy {
    mutableListOf<Int>().apply {
        add(R.mipmap.bg_information_item_image0)
        add(R.mipmap.bg_information_item_image1)
        add(R.mipmap.bg_information_item_image2)
        add(R.mipmap.bg_information_item_image3)
        add(R.mipmap.bg_information_item_image4)
        add(R.mipmap.bg_information_item_image5)
        add(R.mipmap.bg_information_item_image6)
        add(R.mipmap.bg_information_item_image7)
        add(R.mipmap.bg_information_item_image8)
    }
}

val infoIconList by lazy {
    mutableListOf<Int>().apply {
        add(R.mipmap.ic_question_1)
        add(R.mipmap.ic_question_2)
        add(R.mipmap.ic_question_3)
        add(R.mipmap.ic_question_4)
    }
}