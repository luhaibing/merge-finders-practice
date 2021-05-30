package com.mskj.mercer.buildsrc

import org.objectweb.asm.*

object ViewModelFinderDump2 : Opcodes {

    @Throws(Exception::class)
    fun dump(packageName: String, className: String, children: List<String>): ByteArray {
        val cw = ClassWriter(0)
        var mv: MethodVisitor
        var av: AnnotationVisitor

        val packageFile = packageName.replace(".", "/")
        cw.visit(
            Opcodes.V1_8,
            Opcodes.ACC_PUBLIC or Opcodes.ACC_SUPER,
            packageFile + (if (packageFile.endsWith("/")) "" else "/") + className,
            null,
            "java/lang/Object",
            null
        )

        run {
            // 构造函数
            generateInitFun(cw)
        }

        run {
            mv = cw.visitMethod(
                Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC,
                "get",
                "(Ljava/lang/Class;Lcom/mskj/mercer/core/OnRemindAction;)Landroidx/lifecycle/ViewModel;",
                "<VM:Landroidx/lifecycle/ViewModel;>(Ljava/lang/Class<TVM;>;Lcom/mskj/mercer/core/OnRemindAction<**>;)TVM;",
                null
            )

            mv.visitAnnotableParameterCount(2, false)

            // 为参数添加注解
            av = mv.visitParameterAnnotation(0, "Lorg/jetbrains/annotations/NotNull;", false)
            av.visitEnd()
            av = mv.visitParameterAnnotation(1, "Lorg/jetbrains/annotations/NotNull;", false)
            av.visitEnd()

            // 方法开始
            mv.visitCode()

            mv.visitInsn(Opcodes.ACONST_NULL)
            mv.visitVarInsn(Opcodes.ASTORE, 2)

            // 循环 开始

            children.forEachIndexed { _, child ->
                // 一次 开始
                mv.visitVarInsn(Opcodes.ALOAD, 0)
                mv.visitVarInsn(Opcodes.ALOAD, 1)
                mv.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    // packageName + (if (packageName.endsWith("/")) "" else "/") + "ViewModelFinder_D00809DC6B21A861163A1B478232F3CA",
                    packageFile + (if (packageFile.endsWith("/")) "" else "/") + child,
                    // "com/mskj/mercer/ViewModelFinder_D00809DC6B21A861163A1B478232F3CA",
                    "get",
                    "(Ljava/lang/Class;Lcom/mskj/mercer/core/OnRemindAction;)Landroidx/lifecycle/ViewModel;",
                    false
                )
                mv.visitVarInsn(Opcodes.ASTORE, 2)
                mv.visitVarInsn(Opcodes.ALOAD, 2)
                val label0 = Label()
                mv.visitJumpInsn(Opcodes.IFNULL, label0)
                mv.visitVarInsn(Opcodes.ALOAD, 2)
                mv.visitInsn(Opcodes.ARETURN)
                mv.visitLabel(label0)
                // 一次 结束
            }


            // 抛出异常
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/NullPointerException")
            mv.visitInsn(Opcodes.DUP)
            mv.visitLdcInsn("can not found ViewModel instance.")
            mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                "java/lang/NullPointerException",
                "<init>",
                "(Ljava/lang/String;)V",
                false
            )
            mv.visitInsn(Opcodes.ATHROW)
            mv.visitMaxs(3, 3)

            // 方法完毕
            mv.visitEnd()
        }

        cw.visitEnd()

        return cw.toByteArray()
    }

    private fun generateInitFun(cw: ClassWriter) {
        val mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null)
        mv.visitCode()
        mv.visitVarInsn(Opcodes.ALOAD, 0)
        mv.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            "java/lang/Object",
            "<init>",
            "()V",
            false
        )
        mv.visitInsn(Opcodes.RETURN)
        mv.visitMaxs(1, 1)
        mv.visitEnd()
    }
}