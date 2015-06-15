package com.example.al333z.words.service;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by al333z on 14/06/15.
 */
public class WordService {

    public WordService() {
    }

    public Observable<WordResponse> getWords(int month, int year) {
        final Word[] words = {
                new Word(1, "Cat", 1, 1, 2015, "http://truestorieswithgill.com/wp-content/uploads/2013/09/20130915-190532.jpg"),
                new Word(2, "Dog", 2, 1, 2015, "https://media3.giphy.com/media/10we3R8dLZQ7hS/200_s.gif"),
                new Word(3, "Bacon", 3, 1, 2015, "http://rs1img.memecdn.com/Bacon_o_137966.jpg"),
                new Word(4, "Train", 4, 1, 2015, "http://rs2img.memecdn.com/nyan-train_o_289564.jpg"),
                new Word(5, "Onion", 5, 1, 2015, "http://rs1img.memecdn.com/sinister-onion_o_2425497.jpg")};

        return Observable.just(new WordResponse(words)).delay(1, TimeUnit.SECONDS);
    }

}
