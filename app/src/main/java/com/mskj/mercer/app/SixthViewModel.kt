package com.mskj.mercer.app

import android.util.Log
import com.mskj.mercer.core.BaseViewModel
import com.mskj.mercer.core.OnRemindAction

class SixthViewModel : BaseViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

    override fun attach(remind: OnRemindAction<*, *>) {
        super.attach(remind)
        Log.e("TAG","----------- SixthViewModel -----------")
    }

}