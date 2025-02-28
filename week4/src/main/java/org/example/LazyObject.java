package org.example;

import java.util.function.Supplier;


/*
1 这个LazyObject类，读入 / later提供一个还不存在的***类，若不用T generic types作placeholder显然不现实
2 supplier - 不接受input，返回一个东西。在这个类中，supplier是一个‘如何创建T’的逻辑的wrapper。
没call get之前，T实例都不用存在（lazy initialization：没有用到她的时候，她还不用存在）
 */

public class LazyObject<T> {

    private T value = null;
    private Supplier<T> supplier;

    public LazyObject(Supplier<T> supplier) {
        this.supplier = supplier;
    }


    public T getValue() {
        if (value == null) {
            value = supplier.get();
        }

        return value;
    }
}
