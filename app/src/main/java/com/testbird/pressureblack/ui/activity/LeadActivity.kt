package com.testbird.pressureblack.ui.activity

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.testbird.pressureblack.BuildConfig
import com.testbird.pressureblack.adapter.GuideAdapter
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.databinding.ActivityLeadBinding
import com.testbird.pressureblack.util.SharedHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class LeadActivity : BaseActivity<ActivityLeadBinding>() {
    override fun initBinding(): ActivityLeadBinding = ActivityLeadBinding.inflate(layoutInflater)

    override fun initView() {

    }

    private fun initProgressView() {
        binding.clStart.visibility = View.GONE
        binding.clSplash.visibility = View.VISIBLE
        binding.clStep.visibility = View.GONE
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (binding.splashProgress.progress >= 100) {
                    if (isResume) {
                        cancel()
                        CoroutineScope(Dispatchers.Main).launch {
                            if (SharedHelper.launchedStep) {
                                startMainActivity()
                            } else {
                                initStepView()
                            }
                        }

                    }
                } else {
                    binding.splashProgress.progress++
                }

            }

        }, 33, 33)
    }

    private fun initStepView() {
        binding.clStep.visibility = View.VISIBLE
        binding.clStart.visibility = View.GONE
        binding.clSplash.visibility = View.GONE
        binding.guideVp.apply {
            adapter = GuideAdapter(this@LeadActivity)
            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> binding.guideRg.check(binding.rb1.id)
                        1 -> binding.guideRg.check(binding.rb2.id)
                        2 -> binding.guideRg.check(binding.rb3.id)
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }
        binding.guideNext.setOnClickListener {
            when (binding.guideVp.currentItem) {
                0 -> binding.guideVp.currentItem = 1
                1 -> binding.guideVp.currentItem = 2
                2 -> {
                    startMainActivity()
                    SharedHelper.launchedStep = true
                }
            }

        }

    }

    private fun initStartView() {
        binding.clStart.visibility = View.VISIBLE
        binding.clSplash.visibility = View.GONE
        binding.clStep.visibility = View.GONE
        binding.startPrivacy.movementMethod = LinkMovementMethod.getInstance()
        binding.startPrivacy.text = viewModel.spanText(onPrivacy = { key1, key2 ->
            startWebViewActivity(key1, key2)
        }, onAgreement = { key1, key2 ->
            startWebViewActivity(key1, key2)
        })
        binding.startBtn.setOnClickListener {
            initProgressView()
            SharedHelper.launchedStart = true
        }
    }

    override fun initData() {
        if (SharedHelper.launchedStart.not())
            initStartView()
        else
            initProgressView()
    }

}