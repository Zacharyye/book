package com.zacharye.book.jvm_learning;

public class FanXTest {
  static {
    System.out.println(1);
  }

  public static void main(String[] args) {
    System.out.println(2);
  }
}

interface VIP extends Customer {

}

class Merchant2<T extends Customer> {
  public double actionPrice(double price, T customer) {
    return 0;
  }
}

class VIPOnlyMerchant extends Merchant2<VIP> {
  @Override
  public double actionPrice(double price, VIP customer) {
    return 0;
  }
}