package com.mskj.mercer.buildsrc;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class ViewModelFinderDump3 implements Opcodes {

    // packageName: String, className: String, children: List<String>
    public static byte[] dump( String className, List<String> children) throws Exception {

        System.out.println("className : " + className);
        System.out.println("children : " + children);

        ClassWriter classWriter = new ClassWriter(0);
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "com/mskj/mercer/core/ViewModelFinder", null, "java/lang/Object", null);

        methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();

        methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "get", "(Ljava/lang/Class;Lcom/mskj/mercer/core/OnRemindAction;)Landroidx/lifecycle/ViewModel;", "<VM:Landroidx/lifecycle/ViewModel;>(Ljava/lang/Class<TVM;>;Lcom/mskj/mercer/core/OnRemindAction<**>;)TVM;", null);

        methodVisitor.visitAnnotableParameterCount(2, false);

        annotationVisitor0 = methodVisitor.visitParameterAnnotation(0, "Lorg/jetbrains/annotations/NotNull;", false);
        annotationVisitor0.visitEnd();

        annotationVisitor0 = methodVisitor.visitParameterAnnotation(1, "Lorg/jetbrains/annotations/NotNull;", false);
        annotationVisitor0.visitEnd();

        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "com/mskj/mercer/apt/ViewModelFinder_D00809DC6B21A861163A1B478232F3CA", "get", "(Ljava/lang/Class;Lcom/mskj/mercer/core/OnRemindAction;)Landroidx/lifecycle/ViewModel;", false);
        methodVisitor.visitVarInsn(ASTORE, 2);
        methodVisitor.visitVarInsn(ALOAD, 2);
        Label label0 = new Label();
        methodVisitor.visitJumpInsn(IFNULL, label0);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitLabel(label0);

        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "com/mskj/mercer/apt/ViewModelFinder_F314FA8A6EC473E234584B5FB95C0246", "get", "(Ljava/lang/Class;Lcom/mskj/mercer/core/OnRemindAction;)Landroidx/lifecycle/ViewModel;", false);
        methodVisitor.visitVarInsn(ASTORE, 2);
        methodVisitor.visitVarInsn(ALOAD, 2);
        Label label1 = new Label();
        methodVisitor.visitJumpInsn(IFNULL, label1);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitLabel(label1);


        methodVisitor.visitTypeInsn(NEW, "java/lang/NullPointerException");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitLdcInsn("can not found ViewModel instance.111");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/NullPointerException", "<init>", "(Ljava/lang/String;)V", false);
        methodVisitor.visitInsn(ATHROW);
        methodVisitor.visitMaxs(3, 3);

        methodVisitor.visitEnd();


        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
