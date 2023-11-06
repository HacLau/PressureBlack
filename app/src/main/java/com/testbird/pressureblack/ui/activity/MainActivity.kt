package com.testbird.pressureblack.ui.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.databinding.ActivityMainBinding
import com.testbird.pressureblack.ui.fragment.HomeFragment
import com.testbird.pressureblack.ui.fragment.InfoFragment
import com.testbird.pressureblack.ui.fragment.MineFragment
import com.testbird.pressureblack.ui.fragment.RecordFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val listFragment = mutableListOf<Fragment>().apply {
        add(HomeFragment())
        add(RecordFragment())
        add(InfoFragment())
        add(MineFragment())
    }

    override fun initBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {

    }

    override fun initData() {
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
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        binding.mainVp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int =
                listFragment.size


            override fun getItem(position: Int): Fragment = listFragment[position]

        }

    }


}