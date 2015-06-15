package com.example.al333z.words.viewmodel;

import com.example.al333z.words.service.Word;

import rx.subjects.BehaviorSubject;

/**
 * Created by al333z on 14/06/15.
 */
public class WordViewModel {

    final public BehaviorSubject<String> wordTitle;
    final public BehaviorSubject<Integer> day;
    final public BehaviorSubject<Integer> month;
    final public BehaviorSubject<Integer> year;
    final public BehaviorSubject<String> imageUrl;

    private final Word word;

    public WordViewModel(Word word) {
        this.word = word;
        this.wordTitle = BehaviorSubject.create(word.word);
        this.day = BehaviorSubject.create(word.day);
        this.month = BehaviorSubject.create(word.month);
        this.year = BehaviorSubject.create(word.year);
        this.imageUrl = BehaviorSubject.create(word.imageUrl);
    }

}
