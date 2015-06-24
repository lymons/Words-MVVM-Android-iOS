package com.example.al333z.words.viewmodel;

import com.example.al333z.words.service.Word;
import com.example.al333z.words.viewmodelkit.ConstantProperty;

/**
 * Created by al333z on 14/06/15.
 */
public class WordViewModel {

    final public ConstantProperty<String> wordTitle;
    final public ConstantProperty<Integer> day;
    final public ConstantProperty<Integer> month;
    final public ConstantProperty<Integer> year;
    final public ConstantProperty<String> imageUrl;

    public WordViewModel(Word word) {
        this.wordTitle = ConstantProperty.create(word.word);
        this.day = ConstantProperty.create(word.day);
        this.month = ConstantProperty.create(word.month);
        this.year = ConstantProperty.create(word.year);
        this.imageUrl = ConstantProperty.create(word.imageUrl);
    }

}
