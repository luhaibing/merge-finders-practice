package com.mskj.mercer.app

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import com.mskj.mercer.app.base.BaseActivity
import com.mskj.mercer.app.databinding.ActivityMainBinding
import com.mskj.mercer.core.OnRemindAction

class MainActivity :
    // AppCompatActivity()
    BaseActivity<ActivityMainBinding>(), OnRemindAction<String, Unit> {

    private val vm: MainViewModel by viewModelFinders()

    // private val binding: ActivityMainBinding by viewBindingFinders()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        // val vb = ActivityMainBinding.inflate(layoutInflater)
        // val vb = ViewBindingFactory.get<ActivityMainBinding>(this)
        setContentView(binding.root)
        binding.bt.setOnClickListener {
            // 单次使用自定义的 ViewModelProvider.Factory 获取 viewModel 实例
            // val vm = ViewModelProvider(this, ViewModelFactory(this)).get(MainViewModel::class.java)
            // 走默认流程获取
            // val vm = ViewModelProvider(this).get(MainViewModel::class.java)
            vm.queryMsg()
        }
        vm.msgLiveData().observe(this) {
            Toast.makeText(this, "msg : $it", Toast.LENGTH_SHORT).show()
        }

    }

    /*
    // 替换默认的 ViewModelProvider.Factory 实现
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
