package com.testbird.pressureblack.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.testbird.pressureblack.base.BaseFragment
import com.testbird.pressureblack.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding  = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initData() {

    }

    override fun initView() {
        binding.homeTop.topBtn.setOnClickListener {
            startNewRecordActivity()
        }
    }

}