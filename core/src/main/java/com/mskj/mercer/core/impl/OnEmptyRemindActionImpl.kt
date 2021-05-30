package com.mskj.mercer.core.impl

import android.app.Dialog
import com.mskj.mercer.core.OnRemindAction

/**
 * @author mercer
 */
class OnEmptyRemindActionImpl: OnRemindAction<Unit, Unit> {

    override fun onPrepare(action: () -> Dialog, onCancelListener: ((Unit) -> Unit)?) {
    }

    override fun onShow(params: Unit?) {
    }

    override fun onCancel(result: Unit?) {
    }

    override fun onPrompt(msg: Any?) {
    }

    override fun onCleared() {
    }

}