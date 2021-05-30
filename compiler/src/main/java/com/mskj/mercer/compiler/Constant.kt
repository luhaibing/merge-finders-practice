@file:Suppress("HasPlatformType", "unused")

package com.mskj.mercer.compiler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeVariableName
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

const val ALL = "*"

const val NAME_PACKAGE = "com.mskj.mercer"
const val NAME_CORE_PACKAGE = "$NAME_PACKAGE.core"
const val NAME_APT_PACKAGE = "$NAME_PACKAGE.apt"

const val NAME_GET_FUN = "get"
const val NAME_ATTACH = "attach"

const val NAME_ZLASS = "zlass"
const val NAME_LAYOUT_INFLATER = "layoutInflater"
const val NAME_PARENT = "parent"
const val NAME_ATTACH_TO_PARENT = "attachToParent"
const val NAME_VIEW = "view"

const val NAME_VIEW_BINDING = "androidx.viewbinding.ViewBinding"
const val NAME_VIEW_MODEL = "androidx.lifecycle.ViewModel"

const val NAME_VB = "VB"
const val NAME_VM = "VM"

val CLASS_NAME_NOT_NULL = ClassName.get(NotNull::class.java)

val CLASS_NAME_NULLABLE = ClassName.get(Nullable::class.java)

//  ParameterizedTypeName.get(
//                    ClassName.get("com.mskj.mercer.core", "OnRemindAction"),
//                    TypeVariableName.get("?"),
//                    TypeVariableName.get("?")
//                )

val CLASS_NAME_BASE_VIEW_MODEL = ClassName.get(NAME_CORE_PACKAGE, "BaseViewModel")
val CLASS_NAME_ON_REMIND_ACTION = ClassName.get(NAME_CORE_PACKAGE, "OnRemindAction")

val TYPE_VARIABLE_NAME_ANY = TypeVariableName.get("?")

val PARAMETERIZED_TYPE_NAME_ON_REMIND_ACTION = ParameterizedTypeName.get(
    CLASS_NAME_ON_REMIND_ACTION, TYPE_VARIABLE_NAME_ANY, TYPE_VARIABLE_NAME_ANY
)

val CLASS_NAME_ZLASS = ClassName.get(Class::class.java)

val CLASS_NAME_VIEW_BINDING = ClassName.bestGuess(NAME_VIEW_BINDING)
val CLASS_NAME_VIEW_MODEL = ClassName.bestGuess(NAME_VIEW_MODEL)

val TYPE_VARIABLE_NAME_VB = TypeVariableName.get(NAME_VB)
val TYPE_VARIABLE_NAME_VM = TypeVariableName.get(NAME_VM)

val TYPE_VARIABLE_NAME_VB_VIEW_BINDING = TypeVariableName.get(NAME_VB, CLASS_NAME_VIEW_BINDING)
val TYPE_VARIABLE_NAME_VM_VIEW_MODEL = TypeVariableName.get(NAME_VM, CLASS_NAME_VIEW_MODEL)

val CLASS_NAME_ZLASS_VB = ParameterizedTypeName.get(CLASS_NAME_ZLASS, TYPE_VARIABLE_NAME_VB)
val CLASS_NAME_ZLASS_VM = ParameterizedTypeName.get(CLASS_NAME_ZLASS, TYPE_VARIABLE_NAME_VM)

val CLASS_NAME_VIEW_GROUP = ClassName.get(ViewGroup::class.java)
val CLASS_NAME_LAYOUT_INFLATER = ClassName.get(LayoutInflater::class.java)

val CLASS_NAME_SUPPRESS_WARNINGS = ClassName.get(SuppressWarnings::class.java)

const val Separator = "_"

const val PREFIX_VIEW_BINDING = "ViewBindingFinder$Separator"

const val PREFIX_VIEW_MODEL = "ViewModelFinder$Separator"

