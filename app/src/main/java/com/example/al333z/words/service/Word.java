package com.example.al333z.words.service;

public class Word {

    public final long id;
    public final String word;
    public final int day;
    public final int month;
    public final int year;
    public final String imageUrl;

    public Word(int id, String word, int day, int month, int year, String imageUrl) {
        this.id = id;
        this.word = word;
        this.day = day;
        this.month = month;
        this.year = year;
        this.imageUrl = imageUrl;
    }
}
