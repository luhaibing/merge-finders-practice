package androidx.lifecycle

import org.jetbrains.annotations.NotNull

class ViewModelProvider(@NotNull store: ViewModelStore, @NotNull factory: Factory) {

    interface Factory {
        fun <T : ViewModel> create(modelClass: Class<T>): T
    }

    fun <T : ViewModel?> get(modelClass: Class<T>): T {
        throw IllegalArgumentException("Local and anonymous classes can not be ViewModels")
    }

}