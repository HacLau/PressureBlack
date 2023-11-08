package com.testbird.pressureblack.ui.activity

import android.view.View
import com.testbird.pressureblack.contacts.IntentKt
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.databinding.ActivityWebViewBinding

class WebActivity : BaseActivity<ActivityWebViewBinding>() {
    override fun initBinding(): ActivityWebViewBinding = ActivityWebViewBinding.inflate(layoutInflater)

    override fun initView() {
        binding.webTitle.leftImage.let {
            it.visibility = View.VISIBLE
            it.setOnClickListener {
                finish()
            }
        }
    }

    override fun initData() {
        intent.getStringExtra(IntentKt.url)?.let { binding.webView.loadUrl(it) }
        intent.getStringExtra(IntentKt.title)?.let { binding.webTitle.titleText.text = it }
    }

}