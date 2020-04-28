package com.zacharye.book.jvm_learning;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {
  public static void target(int i) {
    //new Exception("#" + i).printStackTrace();
  }

  public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Class<?> klass = Class.forName("com.zacharye.book.jvm_learning.ReflectTest");
    Method method = klass.getMethod("target", int.class);

    method.setAccessible(true); // 关闭权限检查
    polluteProfile();

    long current = System.currentTimeMillis();
    for(int i = 0; i <= 2_000_000_000; i++) {
      if(i % 100_000_000 == 0) {
        long temp = System.currentTimeMillis();
        System.out.println(temp - current);
        current = temp;
      }
      method.invoke(null, 128);
    }
  }

  public static void polluteProfile() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method1 = ReflectTest.class.getMethod("target", int.class);
    Method method2 = ReflectTest.class.getMethod("target", int.class);
    for(int i = 0;i < 2000; i++) {
      method1.invoke(null, 0);
      method2.invoke(null, 0);
    }

  }

  public static void target1(int i){}
  public static void target2(int i){}

}
