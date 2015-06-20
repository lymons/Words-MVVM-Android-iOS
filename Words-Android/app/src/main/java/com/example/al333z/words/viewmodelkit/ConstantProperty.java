/**
 * Original idea from here: https://github.com/thuytrinh/viewmodel-kit
 */

package com.example.al333z.words.viewmodelkit;

import rx.Observable;

public class ConstantProperty<T> implements Val<T> {
    private final Var<T> var;

    protected ConstantProperty(Var<T> var) {
        this.var = var;
    }

    public static <T> ConstantProperty<T> of(Var<T> var) {
        return new ConstantProperty<>(var);
    }

    public static <T> ConstantProperty<T> create(T val) {
        return ConstantProperty.of(new MutableProperty(val));
    }

    @Override
    public T value() {
        return var.value();
    }

    @Override
    public boolean hasValue() {
        return var.hasValue();
    }

    @Override
    public Observable<T> observe() {
        return var.observe();
    }
}