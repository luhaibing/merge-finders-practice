package androidx.fragment.app

import android.content.Context

// 需要 android 项目才能依赖 androidx.xx 包
// 所有只在编译的时候模拟一个同名的类,打包的时候,
// 在 build.gradle 中,进行配置 剔除该部分即可
class Fragment {
    fun requireContext(): Context? {
        TODO("Not yet implemented")
    }
}