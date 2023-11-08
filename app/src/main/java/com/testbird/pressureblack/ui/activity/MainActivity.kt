package com.testbird.pressureblack.ui.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.databinding.ActivityMainBinding
import com.testbird.pressureblack.ui.fragment.RecordFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun initBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {

    }

    fun setCurrentItem(currentItem:Int){
        binding.mainVp.currentItem = currentItem
        if (viewModel.listFragment[currentItem] is RecordFragment){
            (viewModel.listFragment[currentItem] as RecordFragment).getRecordData()
        }
    }

    override fun initData() {
        binding.title.leftText.text = viewModel.titleList[0]
        binding.bottomNav.rgMain.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.bottomNav.rbHome.id -> binding.mainVp.currentItem = 0
                binding.bottomNav.rbRecord.id -> binding.mainVp.currentItem = 1
                binding.bottomNav.rbInfo.id -> binding.mainVp.currentItem = 2
                binding.bottomNav.rbMine.id -> binding.mainVp.currentItem = 3
            }
        }
        binding.mainVp.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNav.rbHome.isChecked = true
                    1 -> binding.bottomNav.rbRecord.isChecked = true
                    2 -> binding.bottomNav.rbInfo.isChecked = true
                    3 -> binding.bottomNav.rbMine.isChecked = true
                }
                binding.title.leftText.text = viewModel.titleList[position]
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        binding.mainVp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int =
                viewModel.listFragment.size


            override fun getItem(position: Int): Fragment = viewModel.listFragment[position]

        }

    }


}