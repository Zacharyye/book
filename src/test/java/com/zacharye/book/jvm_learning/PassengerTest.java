package com.zacharye.book.jvm_learning;

public abstract class PassengerTest {
  abstract void passThroughImmigration();

  public static void main(String[] args) {
    PassengerTest a = new ChinesePassenger();
    PassengerTest b = new ForeignerPassenger();
    long current = System.currentTimeMillis();
    for (int i = 1; i <= 2_000_000_000; i++) {
      if(i % 100_000_000 == 0) {
        long temp = System.currentTimeMillis();
        System.out.println(temp -current);

      }
      PassengerTest c = (i < 1_000_000_000) ? a : b;
      c.passThroughImmigration();;
    }
  }
}

class ChinesePassenger extends PassengerTest {

  @Override
  void passThroughImmigration() {

  }
}

class ForeignerPassenger extends PassengerTest {

  @Override
  void passThroughImmigration() {

  }
}