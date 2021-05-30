package com.mskj.mercer.buildsrc

import org.objectweb.asm.*
import java.lang.Exception

object ViewBindingFinderDump : Opcodes {

    @Throws(Exception::class)
    fun dump(className: String, children: List<String>): ByteArray {
        val cw = ClassWriter(0)
        lateinit var av: AnnotationVisitor

        cw.visit(
            Opcodes.V1_8, Opcodes.ACC_PUBLIC or Opcodes.ACC_SUPER,  className,
            null, "java/lang/Object", null
        )

        // 构造函数
        var mv: MethodVisitor =
            cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null)
        mv.visitCode()

        mv.visitVarInsn(Opcodes.ALOAD, 0)
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
        mv.visitInsn(Opcodes.RETURN)
        mv.visitMaxs(1, 1)
        mv.visitEnd()

        // get函数
        mv = cw.visitMethod(
            Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC,
            "get",
            "(Ljava/lang/Class;Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Z)Landroidx/viewbinding/ViewBinding;",
            "<VB::Landroidx/viewbinding/ViewBinding;>(Ljava/lang/Class;Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Z)TVB;",
            null
        )

        av = mv.visitAnnotation("Lorg/jetbrains/annotations/Nullable;", false)
        av.visitEnd()

        av = mv.visitParameterAnnotation(0, "Lorg/jetbrains/annotations/NotNull;", false)
        av.visitEnd()

        av = mv.visitParameterAnnotation(1, "Lorg/jetbrains/annotations/NotNull;", false)
        av.visitEnd()

        av = mv.visitParameterAnnotation(2, "Lorg/jetbrains/annotations/Nullable;", false)
        av.visitEnd()

        mv.visitCode()

        for (element in children) {
            println("element :$element")
            mv.visitVarInsn(Opcodes.ALOAD, 0)
            mv.visitVarInsn(Opcodes.ALOAD, 1)
            mv.visitVarInsn(Opcodes.ALOAD, 2)
            mv.visitVarInsn(Opcodes.ILOAD, 3)
            mv.visitMethodInsn(
                Opcodes.INVOKESTATIC, element, "get",
                "(Ljava/lang/Class;Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Z)Landroidx/viewbinding/ViewBinding;",
                false
            )
            mv.visitVarInsn(Opcodes.ASTORE, 4)
            mv.visitVarInsn(Opcodes.ALOAD, 4)
            val label0 = Label()
            mv.visitJumpInsn(Opcodes.IFNULL, label0)
            mv.visitVarInsn(Opcodes.ALOAD, 4)
            mv.visitInsn(Opcodes.ARETURN)
            mv.visitLabel(label0)
        }

        mv.visitTypeInsn(Opcodes.NEW, "java/lang/NullPointerException")
        mv.visitInsn(Opcodes.DUP)
        mv.visitLdcInsn("can not found ViewBinding instance.")
        mv.visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            "java/lang/NullPointerException",
            "<init>",
            "(Ljava/lang/String;)V",
            false
        )
        mv.visitInsn(Opcodes.ATHROW)

        mv.visitEnd()
        cw.visitEnd()
        return cw.toByteArray()

    }

}