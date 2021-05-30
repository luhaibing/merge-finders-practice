package com.mskj.mercer.compiler

import android.view.ViewGroup
import com.squareup.javapoet.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

class ViewBindingProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        // 匹配所有
        return hashSetOf(ALL)
    }

    private val filer: Filer by lazy { processingEnv.filer }

    private val types: Types by lazy { processingEnv.typeUtils }

    private val elements: Elements by lazy { processingEnv.elementUtils }

    private val messager: Messager by lazy { processingEnv.messager }

    companion object {
        // const val CLASS_NAME_VIEW_BINDING = "androidx.viewbinding.ViewBinding"
    }

    @Suppress("PrivatePropertyName")
    private val TYPE_MIRROR_VIEW_BINDING by lazy {
        elements.getTypeElement(NAME_VIEW_BINDING).asType()
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun process(
        mutableSet: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {

        println("-------------- ${javaClass.canonicalName} --------------")

        if (roundEnvironment?.rootElements?.isNullOrEmpty() != false) {
            return false
        }

        val viewBindings = roundEnvironment
            .rootElements
            .filterIsInstance(TypeElement::class.java)
            .filter {
                types.isSubtype(it.asType(), TYPE_MIRROR_VIEW_BINDING)
            }

        if (viewBindings.isEmpty()) {
            return false
        }

        val stringBuilder = StringBuilder()
        val getFunSpec = MethodSpec
            .methodBuilder(NAME_GET_FUN)

        //val notNull = ClassName.get(NotNull::class.java)
        //val nullable = ClassName.get(Nullable::class.java)

        try {
            getFunSpec
                .addParameter(
                    ParameterSpec
                        .builder(CLASS_NAME_ZLASS_VB, NAME_ZLASS)
                        .addAnnotation(CLASS_NAME_NOT_NULL)
                        .build()
                )
                .addParameter(
                    ParameterSpec.builder(CLASS_NAME_LAYOUT_INFLATER, NAME_LAYOUT_INFLATER)
                        .addAnnotation(CLASS_NAME_NOT_NULL)
                        .build()
                )
                .addParameter(
                    ParameterSpec.builder(ClassName.get(ViewGroup::class.java), NAME_PARENT)
                        .addAnnotation(CLASS_NAME_NULLABLE)
                        .build()
                )
                .addParameter(
                    ParameterSpec.builder(TypeName.BOOLEAN, NAME_ATTACH_TO_PARENT)
                        .build()
                )


            getFunSpec.addTypeVariable(TYPE_VARIABLE_NAME_VB_VIEW_BINDING)
            getFunSpec.returns(TYPE_VARIABLE_NAME_VB_VIEW_BINDING)
            getFunSpec.addAnnotation(
                AnnotationSpec
                    .builder(CLASS_NAME_SUPPRESS_WARNINGS)
                    .addMember("value", "\$S", "unchecked")
                    .build()
            )
            for (viewBinding in viewBindings) {
                stringBuilder.append(",")
                stringBuilder.append(viewBinding.qualifiedName)
                val asType = ClassName.get(viewBinding.asType())
                getFunSpec.addCode(
                    // "if(\$T.class.getCanonicalName().equals(zlass)){\r\n", asType
                    "if(\$N.isAssignableFrom(\$T.class)){\r\n", NAME_ZLASS, asType
                )
                getFunSpec.addStatement(
                    "return (\$T) \$T.inflate(\$N, \$N, \$N)",
                    TYPE_VARIABLE_NAME_VB, asType,
                    NAME_LAYOUT_INFLATER, NAME_PARENT, NAME_ATTACH_TO_PARENT
                )
                getFunSpec.addCode("}")
            }

            getFunSpec.addStatement("return null")

            val viewBindingFinder =
                PREFIX_VIEW_BINDING + Encript.md5(
                    stringBuilder.substring(
                        1.coerceAtMost(stringBuilder.length)
                    )
                ).uppercase()

            val typeSpec = TypeSpec.classBuilder(viewBindingFinder)
                .addMethod(
                    getFunSpec
                        .addModifiers(/*Modifier.FINAL,*/ Modifier.PUBLIC, Modifier.STATIC)
                        .build()
                )
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .build()

            JavaFile.builder(NAME_APT_PACKAGE, typeSpec)
                .build()
                .writeTo(filer)
        } catch (e: Exception) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.message)
        }
        return false
    }

}