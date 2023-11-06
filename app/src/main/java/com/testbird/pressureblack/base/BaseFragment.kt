package com.testbird.pressureblack.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.testbird.pressureblack.ui.activity.RecordMoreActivity
import com.testbird.pressureblack.ui.activity.RecordNewActivity
import com.testbird.pressureblack.viewmodel.FragmentViewModel

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    lateinit var binding: VB
    lateinit var viewModel: FragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = initBinding(inflater, container)
        viewModel = ViewModelProvider(requireActivity())[FragmentViewModel::class.java]
        initView()
        initData()
        return binding.root

    }

    abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun initData()

    abstract fun initView()

    fun startNewRecordActivity() =
        startActivity(Intent(requireContext(), RecordNewActivity::class.java))


    fun starMoreRecordActivity() = startActivity(Intent(requireContext(), RecordMoreActivity::class.java))

}