package com.mskj.mercer.core

import android.app.Dialog


/**
 * @author mercer
 * 用于提示
 * 例如显示对话框弹窗 toast
 */
@Suppress("unused")
interface OnRemindAction<Params, Result> {

    /**
     * 准备
     * @param   action
     * @param   onCancelListener   消失时的对话框
     */
    fun onPrepare(
        action: () -> Dialog,
        onCancelListener: ((Result) -> Unit)? = null
    )

    /**
     * 显示
     * @param       params              参数
     */
    fun onShow(params: Params? = null)

    /**
     * 取消显示
     */
    fun onCancel(result: Result? = null)

    /**
     * 轻量级提示
     */
    fun onPrompt(msg: Any?)

    fun onCleared()

}

@Suppress("unused")
interface OnProgressRemindAction<Params, Progress, Result> : OnRemindAction<Params, Result> {

    /**
     * 更新
     */
    fun onUpdate(value: Progress)

}
