/**
 * Original idea from here: https://github.com/thuytrinh/viewmodel-kit
 */

package com.example.al333z.words.viewmodelkit;

import rx.Observable;

public interface Val<T> {
    T value();

    boolean hasValue();

    Observable<T> observe();
}