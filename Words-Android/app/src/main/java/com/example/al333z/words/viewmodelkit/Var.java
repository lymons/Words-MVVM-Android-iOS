/**
 * Original idea from here: https://github.com/thuytrinh/viewmodel-kit
 */

package com.example.al333z.words.viewmodelkit;

public interface Var<T> extends Val<T> {
    void put(T value);

    Val<T> asVal();
}