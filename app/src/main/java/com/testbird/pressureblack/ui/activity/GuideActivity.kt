package com.testbird.pressureblack.ui.activity

import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.databinding.ActivityGuideBinding

class GuideActivity : BaseActivity<ActivityGuideBinding>() {
    override fun initBinding(): ActivityGuideBinding = ActivityGuideBinding.inflate(layoutInflater)

    override fun initView() {
        initStartView()
    }

    private fun initStartView() {
        binding.startPrivacy.text = "Read and agree to the Privacy Policy and Terms of Service"
    }

    override fun initData() {

    }

}