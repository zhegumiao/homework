package com.ycq.homeworks.test;

public class TestClosure {
    // 这里{不}需要是 final 的
    private String name = "start name ";
    private ClickListener listener = new ClickListener() {
        @Override
        public void onClick() {
            name = "the other name";
        }
    };

    public ClickListener fun() {
        // 这里需要是 final 的
        final String name = "";
        return new ClickListener() {
            @Override
            public void onClick() {
                System.out.println(name);
                // name = "";
            }
        };
    }


    public void fun1() {
        Adder add1 = new Adder(1);
        add1.add();
        add1.getResult();
        Adder add2 = new Adder(2);
        add2.add();
        add2.getResult();
    }


    public static void main(String[] args) {

    }
}
