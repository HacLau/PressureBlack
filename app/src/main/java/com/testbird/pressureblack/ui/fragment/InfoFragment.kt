package com.testbird.pressureblack.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.testbird.pressureblack.adapter.InformationAdapter
import com.testbird.pressureblack.adapter.ItemBottomDecoration
import com.testbird.pressureblack.adapter.infoList
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.base.BaseFragment
import com.testbird.pressureblack.databinding.FragmentInfoBinding

class InfoFragment : BaseFragment<FragmentInfoBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentInfoBinding = FragmentInfoBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.rvInfo.apply {
            addItemDecoration(ItemBottomDecoration(12))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = InformationAdapter(requireContext(), infoList) {
                (requireActivity() as BaseActivity<*>).startContentActivity(it)
            }
        }
    }

    override fun initView() {

    }

}