package com.mskj.mercer.app

import android.util.Log
import com.mskj.mercer.core.BaseViewModel
import com.mskj.mercer.core.OnRemindAction


class MainViewModel : BaseViewModel(){

    override fun attach(remind: OnRemindAction<*, *>) {
        super.attach(remind)
        Log.e("TAG222","----------------------- ${javaClass.simpleName}.attach -----------------------")
    }

}