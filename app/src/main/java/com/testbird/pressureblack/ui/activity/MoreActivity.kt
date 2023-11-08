package com.testbird.pressureblack.ui.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.testbird.pressureblack.R
import com.testbird.pressureblack.adapter.ItemBottomDecoration
import com.testbird.pressureblack.adapter.Record
import com.testbird.pressureblack.adapter.RecordAdapter
import com.testbird.pressureblack.base.BaseActivity
import com.testbird.pressureblack.contacts.IntentKt
import com.testbird.pressureblack.database.RecordDatabaseManager
import com.testbird.pressureblack.databinding.ActivityRecordMoreBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MoreActivity : BaseActivity<ActivityRecordMoreBinding>() {
    override fun initBinding(): ActivityRecordMoreBinding = ActivityRecordMoreBinding.inflate(layoutInflater)

    override fun initView() {
        binding.moreTitle.leftImage.let {
            it.visibility = View.VISIBLE
            it.setOnClickListener {
                finish()
            }
        }
        binding.moreTitle.titleText.text = getString(R.string.record_edit)
        binding.noBtn.setOnClickListener {
            startNewRecordActivity(IntentKt.new, onResult = {
                getRecordData()
            })
        }
    }

    private fun showNo() {
        binding.moreNo.visibility = View.VISIBLE
        binding.rvMore.visibility = View.GONE

    }
    private fun showList(){
        binding.moreNo.visibility = View.GONE
        binding.rvMore.visibility = View.VISIBLE
    }

    override fun initData() {
        binding.rvMore.apply {
            addItemDecoration(ItemBottomDecoration(12))
            layoutManager = LinearLayoutManager(this@MoreActivity, LinearLayoutManager.VERTICAL, false)
            adapter = RecordAdapter(this@MoreActivity, mutableListOf()) {
                startNewRecordActivity(IntentKt.edit, it) {
                    getRecordData()
                }
            }
        }
        getRecordData()
    }

    private fun getRecordData() {
        viewModel.getRecordData{
            CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                mutableListOf<Record>().apply {
                    it.forEach {
                        add(Record(1, entity = it))
                    }
                }.let {
                    if (it.isEmpty()){
                        showNo()
                    }else{
                        showList()
                    }
                    (binding.rvMore.adapter as RecordAdapter).setList(it)
                }
            }
        }

    }

}