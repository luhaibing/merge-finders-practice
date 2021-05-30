package com.mskj.mercer.core;

import androidx.lifecycle.ViewModel;

/**
 * @author mercer
 */
public class ViewModelFinder {

    @SuppressWarnings("unchecked")
    public static <VM extends ViewModel> VM get(Class<VM> var0, OnRemindAction<?,?> var1) {
        throw new NullPointerException("can not found ViewModel instance.");
    }

}
