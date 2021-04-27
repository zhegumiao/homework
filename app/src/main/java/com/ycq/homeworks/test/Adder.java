package com.ycq.homeworks.test;

public class Adder {
    private final int value;
    public Adder(int step) {
        this.value = step;
    }

    private int result = 0;
    public void add() {
        result += value;
    }

    public int getResult() {
        return result;
    }
}
