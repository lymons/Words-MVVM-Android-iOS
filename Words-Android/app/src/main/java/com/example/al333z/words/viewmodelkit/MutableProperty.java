/**
 * Original idea from here: https://github.com/thuytrinh/viewmodel-kit
 */

package com.example.al333z.words.viewmodelkit;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class MutableProperty<T> implements Var<T> {
    private final BehaviorSubject<T> subject;
    private Val<T> val;

    protected MutableProperty() {
        subject = BehaviorSubject.create();
    }

    protected MutableProperty(T defaultValue) {
        subject = BehaviorSubject.create(defaultValue);
    }

    public static <T> MutableProperty<T> create() {
        return new MutableProperty<>();
    }

    public static <T> MutableProperty<T> create(T defaultValue) {
        return new MutableProperty<>(defaultValue);
    }

    @Override
    public void put(T value) {
        subject.onNext(value);
    }

    @Override
    public synchronized Val<T> asVal() {
        if (val == null) {
            val = ConstantProperty.of(this);
        }

        return val;
    }

    @Override
    public T value() {
        return subject.getValue();
    }

    @Override
    public boolean hasValue() {
        return subject.hasValue();
    }

    @Override
    public Observable<T> observe() {
        return subject.asObservable();
    }
}