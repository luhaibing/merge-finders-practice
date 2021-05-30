//package com.mskj.mercer.app
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.mskj.mercer.core.OnRemindAction
//
//class ViewModelFactory(private val view: OnRemindAction<*, *>) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return ViewModelFinder.get(modelClass, view)
//    }
//
//}