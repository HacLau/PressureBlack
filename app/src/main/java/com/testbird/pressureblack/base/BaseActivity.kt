package com.testbird.pressureblack.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.testbird.pressureblack.viewmodel.ActivityViewModel

abstract class BaseActivity<VB:ViewBinding>: AppCompatActivity() {
    lateinit var binding:VB
    lateinit var viewModel:ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = initBinding()
        setContentView(binding.root)
        viewModel = initViewModel()
        initView()
        initData()
    }

    abstract fun initBinding(): VB

    abstract fun initView()

    abstract fun initData()

    private fun initViewModel(): ActivityViewModel = ViewModelProvider(this)[ActivityViewModel::class.java]

}