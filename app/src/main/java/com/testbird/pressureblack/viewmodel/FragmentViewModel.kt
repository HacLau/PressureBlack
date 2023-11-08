package com.testbird.pressureblack.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testbird.pressureblack.adapter.FilterRecordType
import com.testbird.pressureblack.adapter.RecordEntity
import com.testbird.pressureblack.appContext
import com.testbird.pressureblack.contacts.filterRecordList
import com.testbird.pressureblack.database.RecordDatabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FragmentViewModel : ViewModel() {
    var currentFilter = FilterRecordType.recent
    fun getRecordData(context: Context = appContext, filter: String = currentFilter, onLoadList: (List<RecordEntity>) -> Unit = {}) {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            onLoadList.invoke(RecordDatabaseManager.getManager(context).getHelper().query().filterRecordList(filter))
        }
    }

    val text: LiveData<String> = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
}