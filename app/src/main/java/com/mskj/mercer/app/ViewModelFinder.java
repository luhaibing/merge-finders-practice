//package com.mskj.mercer.app;
//
//import androidx.lifecycle.ViewModel;
//
//import com.mskj.mercer.apt.ViewModelFinder_62536A46FA5261BF2B66AD02DFFD5AA4;
//import com.mskj.mercer.apt.ViewModelFinder_D00809DC6B21A861163A1B478232F3CA;
//import com.mskj.mercer.core.OnRemindAction;
//
//import org.jetbrains.annotations.NotNull;
//
//public class ViewModelFinder {
//
//    @SuppressWarnings("unchecked")
//    public static <VM extends ViewModel> VM get(@NotNull Class<VM> zlass,
//                                                @NotNull OnRemindAction<?, ?> view) {
//        VM vm;
//        vm = ViewModelFinder_D00809DC6B21A861163A1B478232F3CA.get(zlass, view);
//        if (vm != null) {
//            return vm;
//        }
//        vm = ViewModelFinder_62536A46FA5261BF2B66AD02DFFD5AA4.get(zlass, view);
//        if (vm != null) {
//            return vm;
//        }
//        throw new NullPointerException("can not found ViewModel instance.111");
//    }
//
//}
