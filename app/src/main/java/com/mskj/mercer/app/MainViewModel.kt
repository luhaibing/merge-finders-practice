package com.mskj.mercer.app

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mskj.mercer.core.BaseViewModel
import com.mskj.mercer.core.OnRemindAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainViewModel : BaseViewModel(){

    private val TAG = "TAG"

    override fun attach(remind: OnRemindAction<*, *>) {
        super.attach(remind)
        Log.e(TAG,"----------------------- ${javaClass.simpleName}.attach -----------------------")
    }

    private val liveData = MutableLiveData<String>()

    fun msgLiveData() =liveData

    fun queryMsg() {
        viewModelScope.launch {
            delay(500)
            liveData.postValue("12345")
        }
    }

}