package com.zacharye.book.jvm_learning;

public class OverwriteTest {
}

interface Customer {
  boolean isVIP();
}

class Merchant {
  public Number actionPrice(double price, Customer customer) {
    return 0;
  }
}

class NativeMerchant extends Merchant {
  @Override
  public Double actionPrice(double price, Customer customer) {
    return 0d;
  }
}