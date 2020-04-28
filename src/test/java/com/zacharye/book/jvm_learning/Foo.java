package com.zacharye.book.jvm_learning;

import com.mysql.cj.mysqla.authentication.MysqlClearPasswordPlugin;
import net.bytebuddy.implementation.bytecode.Throw;

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Foo {
  public static void target(int i) {}

  public static class MyCallSite extends ConstantCallSite {
//    public final MethodHandle mh;

    public MyCallSite() {
//      mh = findTarget();
      super(findTarget());
    }

    private static MethodHandle findTarget() {
      try {
        MethodHandles.Lookup l = MethodHandles.lookup();
        MethodType t = MethodType.methodType(void.class, int.class);
        return l.findStatic(Foo.class, "target", t);
      } catch (Throwable a) {
        throw new RuntimeException(a);

      }
    }
  }

  private static final MyCallSite myCallSite = new MyCallSite();

  public static void main(String[] args) throws Throwable {
    long current = System.currentTimeMillis();
    for(int i = 1;i <= 2_000_000_000; i++) {
      if(i % 100_000_000 == 0) {
        long temp = System.currentTimeMillis();
        System.out.println(temp - current);
        current = temp;
      }
      myCallSite.getTarget().invokeExact(128);
    }
  }
}
