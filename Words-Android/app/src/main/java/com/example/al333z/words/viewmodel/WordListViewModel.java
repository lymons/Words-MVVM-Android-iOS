package com.example.al333z.words.viewmodel;

import android.util.Log;

import com.example.al333z.words.service.WordService;
import com.example.al333z.words.viewmodelkit.MutableProperty;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by al333z on 14/06/15.
 */
public class WordListViewModel {

    private static final String TAG = WordListViewModel.class.getSimpleName();

    public final MutableProperty<Boolean> isLoading = MutableProperty.create(false);
    public final MutableProperty<List<WordViewModel>> words = MutableProperty.create(new LinkedList<>());

    public WordListViewModel(WordService wordService) {

        wordService.getWords(1, 2025)
                .doOnSubscribe(() -> this.isLoading.put(true))
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(wordResponse -> Observable.from(wordResponse.words))
                .map(word -> new WordViewModel(word))
                .toList()
                .subscribe(wordViewModelList -> {
                            this.isLoading.put(false);
                            this.words.put(wordViewModelList);
                        },
                        throwable -> {
                            this.isLoading.put(false);
                            Log.e(TAG, throwable.getMessage());
                        });
    }

}
