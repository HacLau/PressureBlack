package com.testbird.pressureblack.ui.activity

import android.view.View
import com.testbird.pressureblack.adapter.InformationEntity
import com.testbird.pressureblack.adapter.infoImageList
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.contacts.IntentKt
import com.testbird.pressureblack.databinding.ActivityInformationBinding

class InformationActivity : BaseActivity<ActivityInformationBinding>() {
    override fun initBinding(): ActivityInformationBinding = ActivityInformationBinding.inflate(layoutInflater)

    override fun initView() {
        binding.title.leftImage.visibility = View.VISIBLE
    }

    override fun initData() {
        binding.title.leftImage.setOnClickListener {
            finish()
        }

        intent.getParcelableExtra<InformationEntity>(IntentKt.info)?.let {
            binding.contentTitle.text = it.title
            binding.contentText.text = it.content
            binding.contentFrom.text = it.from
            binding.contentImage.setBackgroundResource(infoImageList[it.image])
        }
    }
}