package com.testbird.pressureblack.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.testbird.pressureblack.base.BaseFragment
import com.testbird.pressureblack.databinding.FragmentRecordBinding

class RecordFragment : BaseFragment<FragmentRecordBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRecordBinding = FragmentRecordBinding.inflate(inflater, container, false)

    override fun initData() {


    }

    override fun initView() {
        binding.recordTop.recordTopNew.setOnClickListener {
            startNewRecordActivity()
        }
        binding.recordTop.recordTopMore.setOnClickListener {
            starMoreRecordActivity()
        }
    }

}