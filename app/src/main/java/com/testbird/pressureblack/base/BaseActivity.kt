package com.testbird.pressureblack.base

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.testbird.pressureblack.adapter.InformationEntity
import com.testbird.pressureblack.adapter.RecordEntity
import com.testbird.pressureblack.contacts.IntentKt
import com.testbird.pressureblack.ui.activity.InformationActivity
import com.testbird.pressureblack.ui.activity.MainActivity
import com.testbird.pressureblack.ui.activity.MoreActivity
import com.testbird.pressureblack.ui.activity.NewActivity
import com.testbird.pressureblack.ui.activity.WebActivity
import com.testbird.pressureblack.viewmodel.ActivityViewModel

abstract class BaseActivity<VB:ViewBinding>: AppCompatActivity() {
    lateinit var binding:VB
    lateinit var viewModel:ActivityViewModel
    var isResume = false
    private var onResult: () -> Unit = {}
    private var startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            onResult.invoke()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = initBinding()
        setContentView(binding.root)
        viewModel = initViewModel()
        initView()
        initData()
        isResume = false

    }

    override fun onStart() {
        super.onStart()
        isResume = false
    }

    override fun onResume() {
        super.onResume()
        isResume = true
    }

    override fun onPause() {
        super.onPause()
        isResume = false
    }

    override fun onStop() {
        super.onStop()
        isResume = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isResume = false
    }

    abstract fun initBinding(): VB

    abstract fun initView()

    abstract fun initData()

    private fun initViewModel(): ActivityViewModel = ViewModelProvider(this)[ActivityViewModel::class.java]

    fun startMainActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    fun starMoreRecordActivity() = startActivity(Intent(this, MoreActivity::class.java))
    fun startWebViewActivity(url: String, title: String) =
        startActivity(Intent(this, WebActivity::class.java).apply {
            putExtra(IntentKt.url,url)
            putExtra(IntentKt.title,title)
        })

    fun startNewRecordActivity(type:String,record: RecordEntity? = null,onResult: () -> Unit = {}) {
        this.onResult = onResult
        startActivityForResult.launch(Intent(this, NewActivity::class.java).apply {
            putExtra(IntentKt.type,type)
            putExtra(IntentKt.record,record)
        })
    }


    fun startContentActivity(it: InformationEntity)  =
        startActivity(Intent(this, InformationActivity::class.java).apply {
            putExtra(IntentKt.info,it)
        })



}