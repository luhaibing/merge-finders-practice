package com.mskj.mercer.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mskj.mercer.app.databinding.FragmentThirdBinding

/**
 * A simple [Fragment] subclass.
 * com.mskj.mercer.app.ThirdFragment
 */
class ThirdFragment : BaseFragment<FragmentThirdBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = "--- 页面碎片 ---"
    }

}