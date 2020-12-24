package com.ycq.homeworks.lint;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

class TestLint1 {
    static final int V1 = 1;
    static final int V2 = 2;
    static final int V3 = 3;


    @IntDef({V1, V2, V3})
    @Retention(RetentionPolicy.SOURCE)
    private @interface TestClassName {
    }

    void testFun(@TestClassName int value) {

    }

    void init() {
//        testFun(1);
        testFun(TestLint1.V1);
    }
}
