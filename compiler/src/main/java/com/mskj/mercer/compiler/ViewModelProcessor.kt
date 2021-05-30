package com.mskj.mercer.compiler

import com.squareup.javapoet.*
import java.io.IOException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic
import javax.tools.StandardLocation


class ViewModelProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        // 匹配所有
        return hashSetOf(ALL)
    }

    private val filer: Filer by lazy { processingEnv.filer }

    private val types: Types by lazy { processingEnv.typeUtils }

    private val elements: Elements by lazy { processingEnv.elementUtils }

    private val messager: Messager by lazy { processingEnv.messager }


    @Suppress("PrivatePropertyName")
    private val TYPE_MIRROR_VIEW_MODEL by lazy {
        elements.getTypeElement(NAME_VIEW_MODEL).asType()
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }


    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)

    }

    override fun process(
        mutableSet: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment?
    ): Boolean {

        println("-------------- ${javaClass.canonicalName} --------------")

        if (roundEnvironment?.rootElements?.isNullOrEmpty() != false) {
            return false
        }

        val viewModels = roundEnvironment
            .rootElements
            .filterIsInstance(TypeElement::class.java)
            .filter {
                types.isSubtype(it.asType(), TYPE_MIRROR_VIEW_MODEL)
            }

        if (viewModels.isEmpty()) {
            return false
        }

        val stringBuilder = StringBuilder()
        val getFunSpec = MethodSpec
            .methodBuilder(NAME_GET_FUN)

        try {

            val subtypeOf =
//                ParameterizedTypeName.get(
//                    ClassName.get("com.mskj.mercer.core", "OnRemindAction"),
//                    TypeVariableName.get("?"),
//                    TypeVariableName.get("?")
//                )

                getFunSpec
                    .addParameter(
                        ParameterSpec
                            .builder(CLASS_NAME_ZLASS_VM, NAME_ZLASS)
                            .addAnnotation(CLASS_NAME_NOT_NULL)
                            .build()
                    )
                    .addParameter(
                        ParameterSpec
                            .builder(PARAMETERIZED_TYPE_NAME_ON_REMIND_ACTION, NAME_VIEW)
                            .addAnnotation(CLASS_NAME_NOT_NULL)
                            .build()
                    )


            /*val vb = TypeVariableName.get(
                "VM", CLASS_NAME_VIEW_MODEL
            )*/
            getFunSpec.addTypeVariable(TYPE_VARIABLE_NAME_VM_VIEW_MODEL)
            getFunSpec.returns(TYPE_VARIABLE_NAME_VM_VIEW_MODEL)
            getFunSpec.addAnnotation(
                AnnotationSpec
                    .builder(SuppressWarnings::class.java)
                    .addMember("value", "\$S", "unchecked")
                    .build()
            )

            getFunSpec.addStatement("\$T \$N = null", TYPE_VARIABLE_NAME_VM, NAME_VM.lowercase())

            viewModels.forEachIndexed { index, typeElement ->
                stringBuilder.append(",")
                stringBuilder.append(typeElement.qualifiedName)
                val asType = ClassName.get(typeElement.asType())
                getFunSpec.addCode(
                    if (index != 0) {
                        "else "
                    } else {
                        ""
                    } + "if(zlass.isAssignableFrom(\$T.class)){\r\n", asType
                )
                getFunSpec.addStatement(
                    "\$N = (\$T) new \$T()",
                    NAME_VM.lowercase(), TYPE_VARIABLE_NAME_VM, asType
                )
                getFunSpec.addCode("}")
            }

            // TODO: 2021/5/30
            getFunSpec.addCode(
                "\r\nif (\$N instanceof \$T) {\r\n",
                NAME_VM.lowercase(),
                CLASS_NAME_BASE_VIEW_MODEL
            )
            getFunSpec.addStatement(
                "((\$T) \$N).\$N(\$N)",
                CLASS_NAME_BASE_VIEW_MODEL,
                NAME_VM.lowercase(),
                NAME_ATTACH,
                NAME_VIEW
            )
            getFunSpec.addCode("}\r\n")

            getFunSpec.addStatement("return \$N", NAME_VM.lowercase())

            val viewBindingFinder =
                PREFIX_VIEW_MODEL + Encript.md5(
                    stringBuilder.substring(
                        1.coerceAtMost(stringBuilder.length)
                    )
                ).uppercase()

            val typeSpec = TypeSpec.classBuilder(viewBindingFinder)
                .addMethod(
                    getFunSpec
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
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