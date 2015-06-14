package com.example.al333z.words.viewmodel;

import android.util.Log;

import com.example.al333z.words.service.WordService;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by al333z on 14/06/15.
 */
public class WordListViewModel {

    private static final String TAG = WordListViewModel.class.getSimpleName();

    final BehaviorSubject<Boolean> isLoading = BehaviorSubject.create(false);
    final BehaviorSubject<List<WordViewModel>> words = BehaviorSubject.create(new LinkedList<>());

    final WordService wordService;

    public WordListViewModel(WordService wordService) {
        this.wordService = wordService;

        this.wordService.getWords(1, 2025)
                .doOnSubscribe(() -> this.isLoading.onNext(true))
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(wordResponse -> Observable.from(wordResponse.words))
                .map(word -> new WordViewModel(word))
                .toList()
                .subscribe(wordViewModelList -> {
                            this.isLoading.onNext(false);
                            this.words.onNext(wordViewModelList);
                        },
                        throwable -> {
                            this.isLoading.onNext(false);
                            Log.e(TAG, throwable.getMessage());
                        });
    }

}
