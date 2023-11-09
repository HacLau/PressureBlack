package com.testbird.pressureblack.ui.fragment

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.testbird.pressureblack.R
import com.testbird.pressureblack.adapter.FilterAdapter
import com.testbird.pressureblack.adapter.FilterRecordType
import com.testbird.pressureblack.adapter.ItemBottomDecoration
import com.testbird.pressureblack.adapter.RecordAdapter
import com.testbird.pressureblack.adapter.filterRecordList
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.base.BaseFragment
import com.testbird.pressureblack.contacts.IntentKt
import com.testbird.pressureblack.contacts.filterRecordList
import com.testbird.pressureblack.contacts.getRecordList
import com.testbird.pressureblack.contacts.log
import com.testbird.pressureblack.database.RecordDatabaseManager
import com.testbird.pressureblack.databinding.FragmentRecordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RecordFragment : BaseFragment<FragmentRecordBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRecordBinding =
        FragmentRecordBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.rvRecord.apply {
            addItemDecoration(ItemBottomDecoration(12))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = RecordAdapter(requireContext(), mutableListOf()) {
                (requireActivity() as BaseActivity<*>).startNewRecordActivity(IntentKt.edit, it) {
                    getRecordData()
                }
            }
        }
        binding.recordTop.apply {
            recordTopVp.adapter = FilterAdapter(requireContext())
            recordTopVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    viewModel.currentFilter = filterRecordList[position]
                    getRecordData()
                    setFilterBtnClickable()
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })

            filterPre.setOnClickListener {
                setCurrentFilter(-1)

            }
            filterNext.setOnClickListener {
                setCurrentFilter(1)
            }
        }

        setFilterBtnClickable()
        getRecordData()
    }

    fun getRecordData() {
        viewModel.getRecordData(requireContext()) {
            CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                (binding.rvRecord.adapter as RecordAdapter).setList(it.getRecordList { sys, dia ->
                    binding.recordTop.sysNumber.text = "$sys"
                    binding.recordTop.diasNumber.text = "$dia"
                })
            }
        }
    }

    private fun setCurrentFilter(i: Int) {
        binding.recordTop.recordTopVp.currentItem -= (-1) * i
        setFilterBtnClickable()
    }

    private fun setFilterBtnClickable() {
        binding.recordTop.apply {
            filterNext.isClickable = recordTopVp.currentItem != filterRecordList.size - 1
            filterPre.isClickable = recordTopVp.currentItem != 0

            filterNext.imageTintList = if (filterNext.isClickable) {
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.record_item_sd))
            }
            filterPre.imageTintList = if (filterPre.isClickable) {
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.record_item_sd))
            }

        }

    }


    override fun initView() {
        binding.recordTop.apply {
            recordTopNew.setOnClickListener {
                (requireActivity() as BaseActivity<*>).startNewRecordActivity(IntentKt.new) {
                    getRecordData()
                }
            }
            recordTopMore.setOnClickListener {
                (requireActivity() as BaseActivity<*>).starMoreRecordActivity(){
                    getRecordData()
                }
            }
        }
    }

}