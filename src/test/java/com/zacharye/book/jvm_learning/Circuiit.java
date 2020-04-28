package com.zacharye.book.jvm_learning;

import java.lang.invoke.*;

public class Circuiit {
  public static void startRace(Object obj) {

  }

  public static void main(String[] args) {
    startRace(new Horse());
  }

  public static CallSite bootstrap(MethodHandles.Lookup l, String name, MethodType callSiteType) throws NoSuchMethodException, IllegalAccessException {
    MethodHandle mh = l.findVirtual(Horse.class, name, MethodType.methodType(void.class));
    return new ConstantCallSite(mh.asType(callSiteType));
  }
}

class Horse {
  public void race() {
    System.out.println("Horse.race()");
  }
}

class Deer {
  public void race() {
    System.out.println("Deer.race()");
  }
}