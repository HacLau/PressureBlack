package com.testbird.pressureblack.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.testbird.pressureblack.BuildConfig
import com.testbird.pressureblack.R
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.base.BaseFragment
import com.testbird.pressureblack.databinding.FragmentMineBinding

class MineFragment : BaseFragment<FragmentMineBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMineBinding =
        FragmentMineBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.minePrivacy.mineParent.setOnClickListener {
            (requireActivity() as BaseActivity<*>).startWebViewActivity(BuildConfig.privacy, getString(R.string.mine_privacy))
        }
        binding.minePolicy.mineParent.setOnClickListener {
            (requireActivity() as BaseActivity<*>).startWebViewActivity(BuildConfig.policy, getString(R.string.mine_policy))
        }
        binding.mineShared.mineParent.setOnClickListener {
            kotlin.runCatching {
                startActivity(Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                })
            }
        }
    }

    override fun initView() {
        binding.mineAlarm.mineAlarmTitle.text = getString(R.string.mine_alarm)
        binding.mineFeedback.mineTitle.text = getString(R.string.mine_feed)
        binding.mineLanguage.mineTitle.text = getString(R.string.mine_language)
        binding.minePrivacy.mineTitle.text = getString(R.string.mine_privacy)
        binding.minePolicy.mineTitle.text = getString(R.string.mine_policy)
        binding.mineShared.mineTitle.text = getString(R.string.mine_shared)
        binding.upgrade.mineTitle.text = getString(R.string.mine_upgrade)
    }

}