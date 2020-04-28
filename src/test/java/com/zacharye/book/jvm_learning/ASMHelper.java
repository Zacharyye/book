package com.zacharye.book.jvm_learning;


import org.objectweb.asm.*;

import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ASMHelper implements Opcodes {
  private static class MyMethodVisitor extends MethodVisitor {
    private static final String BOOTSTRAP_CLASS_NAME = Circuiit.class.getName().replace('.','/');
    private static final String BOOTSTRAP_METHOD_NAME = "bootstrap";
    private static final String BOOTSTRAP_METHOD_DESC = MethodType
        .methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class)
        .toMethodDescriptorString();

    private static final String TARGET_METHOD_NAME = "race";
    private static final String TARGET_METHOD_DESC = "(Ljava/lang/Object;)V";

    public final MethodVisitor mv;

    public MyMethodVisitor(int i, MethodVisitor methodVisitor) {
      super(i, methodVisitor);
      this.mv = methodVisitor;
    }

    @Override
    public void visitCode() {
      mv.visitCode();
      mv.visitVarInsn(ALOAD, 0);
      Handle h = new Handle(H_INVOKESTATIC, BOOTSTRAP_CLASS_NAME,BOOTSTRAP_METHOD_NAME,BOOTSTRAP_METHOD_DESC);
      mv.visitInvokeDynamicInsn(TARGET_METHOD_NAME,TARGET_METHOD_DESC, h);
      mv.visitInsn(RETURN);
      mv.visitMaxs(1,1);
      mv.visitEnd();
    }
  }

  public static void main(String[] args) throws IOException {
    ClassReader cr = new ClassReader("com.zacharye.book.jvm_learning.Circuiit");
    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
    ClassVisitor cv = new ClassVisitor(6 << 16 | 0 << 8 | 0, cw) {
      @Override
      public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        MethodVisitor visitor = super.visitMethod(i, s, s1, s2, strings);
        if("startRace".equals(s)) {
          return new MyMethodVisitor(6 << 16 | 0 << 8 | 0, visitor);
        }
        return visitor;
      }
    };
    cr.accept(cv, ClassReader.SKIP_FRAMES);
    Files.write(Paths.get("Circuiit.class"), cw.toByteArray());
  }

}
