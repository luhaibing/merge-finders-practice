package com.mskj.mercer.app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mskj.mercer.app.databinding.ActivityMainBinding
import com.mskj.mercer.core.OnRemindAction
import com.mskj.mercer.core.ViewModelFactory

class MainActivity : AppCompatActivity(), OnRemindAction<String, Unit> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bt.setOnClickListener {
            val vm = ViewModelProvider(this, ViewModelFactory(this)).get(MainViewModel::class.java)
            // val vm = ViewModelProvider(this).get(MainViewModel::class.java)
            Toast.makeText(this, "vm21312333 : $vm", Toast.LENGTH_SHORT).show()
        }

    }

    /*
    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        // return super.getDefaultViewModelProviderFactory()
        return ViewModelFactory(this)
    }
    */

    override fun onPrepare(action: () -> Dialog, onCancelListener: ((Unit) -> Unit)?) {
    }

    override fun onShow(params: String?) {
    }

    override fun onCancel(result: Unit?) {
    }

    override fun onPrompt(msg: Any?) {
    }

    override fun onCleared() {
    }

}