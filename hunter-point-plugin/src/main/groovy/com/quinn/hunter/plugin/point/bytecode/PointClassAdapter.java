package com.quinn.hunter.plugin.point.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.util.Arrays;

public final class PointClassAdapter extends ClassVisitor{

    private String className;

    private boolean isHeritedFromPointHandler = false;

    PointClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM7, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.isHeritedFromPointHandler = Arrays.toString(interfaces).contains("com/hunter/library");
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if(isHeritedFromPointHandler) {
            return mv;
        } else {
            return mv == null ? null : new PointMethodAdapter(className + File.separator + name, access, desc, mv);
        }
    }



}