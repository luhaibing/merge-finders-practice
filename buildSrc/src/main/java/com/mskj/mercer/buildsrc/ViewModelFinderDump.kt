package com.mskj.mercer.buildsrc

import org.objectweb.asm.*

object ViewModelFinderDump : Opcodes {
    // packageName: String, className: String, children: List<String>
    @Throws(Exception::class)
    fun dump(className: String, children: List<String>): ByteArray {
        println("children : $children")
        val cw = ClassWriter(0)
        var av: AnnotationVisitor

        cw.visit(
            Opcodes.V1_8, Opcodes.ACC_PUBLIC or Opcodes.ACC_SUPER, className,
            null, "java/lang/Object", null
        )

        constructorFun(cw)

        val mv = cw.visitMethod(
            Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC, "get",
            "(Ljava/lang/Class;Lcom/mskj/mercer/core/OnRemindAction;)Landroidx/lifecycle/ViewModel;",
            "<VM:Landroidx/lifecycle/ViewModel;>(Ljava/lang/Class<TVM;>;Lcom/mskj/mercer/core/OnRemindAction<**>;)TVM;",
            null
        )

        // 方法参数添加注解
        mv.visitAnnotableParameterCount(2, false)
        av = mv.visitParameterAnnotation(0, "Lorg/jetbrains/annotations/NotNull;", false)
        av.visitEnd()
        av = mv.visitParameterAnnotation(1, "Lorg/jetbrains/annotations/NotNull;", false)
        av.visitEnd()

        // 进入方法
        mv.visitCode()

        children.onEach { element->
            mv.visitVarInsn(Opcodes.ALOAD, 0)
            mv.visitVarInsn(Opcodes.ALOAD, 1)
            mv.visitMethodInsn(
                Opcodes.INVOKESTATIC, element, "get",
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
        }

        // 抛出异常
        mv.visitTypeInsn(Opcodes.NEW, "java/lang/NullPointerException")
        mv.visitInsn(Opcodes.DUP)
        mv.visitLdcInsn("can not found ViewModel instance.111")
        mv.visitMethodInsn(
            Opcodes.INVOKESPECIAL, "java/lang/NullPointerException", "<init>",
            "(Ljava/lang/String;)V", false
        )
        mv.visitInsn(Opcodes.ATHROW)

        mv.visitMaxs(3, 3)
        // 退出方法
        mv.visitEnd()

        cw.visitEnd()
        return cw.toByteArray()
    }

    // 构造函数
    private fun constructorFun(cw: ClassWriter) {
        cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null).run {
            visitCode()
            visitVarInsn(Opcodes.ALOAD, 0)
            visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
            visitInsn(Opcodes.RETURN)
            visitMaxs(1, 1)
            visitEnd()
        }
    }

}