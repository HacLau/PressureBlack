package com.testbird.pressureblack.viewmodel

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testbird.pressureblack.BuildConfig
import com.testbird.pressureblack.R
import com.testbird.pressureblack.adapter.FilterRecordType
import com.testbird.pressureblack.adapter.RecordEntity
import com.testbird.pressureblack.appContext
import com.testbird.pressureblack.contacts.filterRecordList
import com.testbird.pressureblack.contacts.getFormatTimeMain
import com.testbird.pressureblack.contacts.getRecordDataByLevel
import com.testbird.pressureblack.contacts.log
import com.testbird.pressureblack.database.RecordDatabaseManager
import com.testbird.pressureblack.ui.fragment.HomeFragment
import com.testbird.pressureblack.ui.fragment.InfoFragment
import com.testbird.pressureblack.ui.fragment.MineFragment
import com.testbird.pressureblack.ui.fragment.RecordFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ActivityViewModel : ViewModel() {
    var isResume: LiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }
    val listFragment by lazy {
        mutableListOf<Fragment>().apply {
            add(HomeFragment())
            add(RecordFragment())
            add(InfoFragment())
            add(MineFragment())
        }
    }
    val titleList by lazy {
        mutableListOf<String>().apply {
            add(System.currentTimeMillis().getFormatTimeMain())
            add(appContext.getString(R.string.title_record))
            add(appContext.getString(R.string.title_info))
            add(appContext.getString(R.string.title_mine))
        }
    }

    fun getRecordData(context: Context = appContext, filter: String = FilterRecordType.all, onLoadList: (List<RecordEntity>) -> Unit = {}) {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            onLoadList.invoke(RecordDatabaseManager.getManager(context).getHelper().query().filterRecordList(filter))
        }
    }

    fun spanText(onPrivacy:(String,String)->Unit,onAgreement:(String,String)->Unit): CharSequence? {
        val text = "Read and agree to the Privacy Policy and Terms of Service"
        val key1 = "Privacy Policy"
        val key2 = "Terms of Service"
        return SpannableStringBuilder(text).apply {
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    onPrivacy.invoke(BuildConfig.privacy, key1)
                }

            }, text.indexOf(key1), text.indexOf(key1) + key1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    onAgreement.invoke(BuildConfig.policy, key2)
                }
            }, text.indexOf(key2), text.indexOf(key2) + key2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    fun getRecordLevel(recordEntity: RecordEntity): Int {
        "systolic = ${recordEntity.systolic} diastolic = ${recordEntity.diastolic}".log()
        return if (recordEntity.systolic > 180 || recordEntity.diastolic > 120)
            5
        else if (recordEntity.systolic >= 140 || recordEntity.diastolic >= 90)
            4
        else if (recordEntity.systolic >= 130 || recordEntity.diastolic >= 80)
            3
        else if (recordEntity.systolic >= 120 && recordEntity.diastolic >= 60)
            2
        else if (recordEntity.systolic >= 90 && recordEntity.diastolic >= 60)
            1
        else if (recordEntity.systolic < 90 || recordEntity.diastolic < 60)
            0
        else 0
    }

    fun getPageData(recordEntity: RecordEntity,onLoaded:(Int,Int,Int,Int)->Unit) {
        getRecordDataByLevel(recordEntity,onLoaded)
    }

}