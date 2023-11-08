package com.testbird.pressureblack.ui.activity

import android.app.Activity
import android.view.View
import android.widget.NumberPicker
import com.testbird.pressureblack.R
import com.testbird.pressureblack.adapter.RecordEntity
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.contacts.IntentKt
import com.testbird.pressureblack.contacts.dp2px
import com.testbird.pressureblack.contacts.getFormatTimeRecordItem
import com.testbird.pressureblack.contacts.toast
import com.testbird.pressureblack.database.RecordDatabaseManager
import com.testbird.pressureblack.databinding.ActivityRecordNewBinding
import com.testbird.pressureblack.ui.view.DateTimeDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewActivity : BaseActivity<ActivityRecordNewBinding>() {
    private lateinit var currentType: String
    private lateinit var recordEntity: RecordEntity
    override fun initBinding(): ActivityRecordNewBinding = ActivityRecordNewBinding.inflate(layoutInflater)

    override fun initView() {
        binding.newTitle.leftImage.let {
            it.visibility = View.VISIBLE
            it.setOnClickListener {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
        binding.recordNewConfirm.setOnClickListener {
            if (recordEntity.systolic > recordEntity.diastolic) {
                CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                    when (currentType) {
                        IntentKt.new -> RecordDatabaseManager.getManager(this@NewActivity).getHelper().insert(recordEntity)
                        IntentKt.edit -> RecordDatabaseManager.getManager(this@NewActivity).getHelper().update(recordEntity)
                    }
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            } else {
                getString(R.string.record_toast).toast(this)
            }
        }
        binding.sysNumberPicker.let {
            it.maxValue = 320
            it.minValue = 20
            it.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            it.wrapSelectorWheel = false
            it.setOnValueChangedListener { _, _, value ->
                recordEntity.systolic = value
                setRecordLevel()
            }
        }
        binding.diasNumberPicker.let {
            it.maxValue = 320
            it.minValue = 20
            it.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            it.wrapSelectorWheel = false
            it.setOnValueChangedListener { _, _, value ->
                recordEntity.diastolic = value
                setRecordLevel()
            }
        }
        binding.clTime.setOnClickListener {
            DateTimeDialog(this, recordEntity.time, confirm = {
                recordEntity.time = it
                binding.recordNewTime.text = it.getFormatTimeRecordItem()
            }).show()
        }
    }


    override fun initData() {
        currentType = intent.getStringExtra(IntentKt.type) ?: IntentKt.new
        when (currentType) {
            IntentKt.new -> binding.newTitle.titleText.text = getString(R.string.record_add)
            IntentKt.edit -> binding.newTitle.titleText.text = getString(R.string.record_edit)
        }
        recordEntity = intent.getParcelableExtra<RecordEntity>(IntentKt.record) ?: RecordEntity()
        setPageData()

    }

    private fun setRecordLevel() {
        recordEntity.level = viewModel.getRecordLevel(recordEntity)
        setPageData()
    }

    private fun setPageData() {
        recordEntity.let {
            binding.recordNewTime.text = it.time.getFormatTimeRecordItem()
            binding.sysNumberPicker.value = it.systolic
            binding.diasNumberPicker.value = it.diastolic
            viewModel.getPageData(recordEntity) { title, content, des, _ ->
                binding.levelTitle.text = getString(title)
                binding.levelContent.text = getString(content)
                binding.levelDes.text = getString(des)
            }
            binding.levelProgress.translationX = (60f * recordEntity.level).dp2px(this)
        }
    }
}