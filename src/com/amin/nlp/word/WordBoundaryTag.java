package com.amin.nlp.word;

/**
 * Created by Amin on 6/27/2016.
 */
public enum WordBoundaryTag {
    BEGINNING("B"), INSIDE("I"), OUTSIDE("O");

    public String tag;
    WordBoundaryTag(String tag) {
        this.tag = tag;
    }
}
