package com.testbird.pressureblack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentViewModel : ViewModel() {

    val text: LiveData<String> = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
}