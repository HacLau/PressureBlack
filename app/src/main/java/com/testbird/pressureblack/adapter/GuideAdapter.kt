package com.testbird.pressureblack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.viewpager.widget.PagerAdapter
import com.testbird.pressureblack.R
import com.testbird.pressureblack.databinding.LayoutStepItemBinding

class GuideAdapter(private val context: Context) : PagerAdapter() {

    override fun getCount(): Int {
        return listGuide.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = LayoutStepItemBinding.inflate(LayoutInflater.from(context))
        binding.guideImage.setBackgroundResource(listGuide[position].image)
        binding.guideTitle.text = context.getString(listGuide[position].title)
        binding.guideContent.text = context.getString(listGuide[position].content)
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}

data class GuideEntity(
    @StringRes
    var title: Int,
    @StringRes
    var content: Int,
    @DrawableRes
    var image: Int
)

val listGuide: List<GuideEntity> by lazy {
    mutableListOf<GuideEntity>().apply {
        add(GuideEntity(R.string.text_guide_title_1, R.string.text_guide_content_1, R.mipmap.bg_guide_1))
        add(GuideEntity(R.string.text_guide_title_2, R.string.text_guide_content_1, R.mipmap.bg_guide_2))
        add(GuideEntity(R.string.text_guide_title_3, R.string.text_guide_content_1, R.mipmap.bg_guide_3))
    }
}