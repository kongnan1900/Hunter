package com.quinn.hunter.plugin.point.bytecode;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public final class PointMethodAdapter extends LocalVariablesSorter implements Opcodes {

    private String methodName;

    public PointMethodAdapter(String name, int access, String desc, MethodVisitor mv) {
        super(Opcodes.ASM7, access, desc, mv);
        this.methodName = name.replace("/", ".");
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/point/PointManager", "pointMethod", "(Ljava/lang/String;)V", false);
        }
        super.visitInsn(opcode);
    }

}
