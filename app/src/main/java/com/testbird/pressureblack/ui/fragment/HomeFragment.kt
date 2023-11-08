package com.testbird.pressureblack.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.testbird.pressureblack.adapter.InformationAdapter
import com.testbird.pressureblack.adapter.InformationEntity
import com.testbird.pressureblack.adapter.ItemBottomDecoration
import com.testbird.pressureblack.adapter.infoList
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.base.BaseFragment
import com.testbird.pressureblack.contacts.IntentKt
import com.testbird.pressureblack.databinding.FragmentHomeBinding
import com.testbird.pressureblack.ui.activity.MainActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.homeRl.apply {
            addItemDecoration(ItemBottomDecoration(12))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = InformationAdapter(
                requireContext(), if (infoList.isEmpty()) (mutableListOf<InformationEntity>()) else {
                    infoList.subList(
                        0, if (infoList.size > 5) {
                            5
                        } else {
                            infoList.size
                        }
                    )
                }
            ) {
                (requireActivity() as BaseActivity<*>).startContentActivity(it)
            }
        }

    }

    override fun initView() {
        binding.homeTop.topBtn.setOnClickListener {
            (requireActivity() as BaseActivity<*>).startNewRecordActivity(IntentKt.new){
                (requireActivity() as MainActivity).setCurrentItem(1)
            }
        }
    }

}