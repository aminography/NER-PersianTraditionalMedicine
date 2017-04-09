package com.amin.nlp.pos;

/**
 * Created by Amin on 6/27/2016.
 */
public enum PosTag {
    N("N", "Noun"), V("V", "Verb"), ADJ("ADJ", "Adjective"), P("P", "Preposition"), POSTP("POSTP", "Postposition"),
    CONJ("CONJ", "Conjunction"), ADV("ADV", "Adverb"), PUNC("PUNC", "Punctuation"), DET("DET", "Determiner"),
    PRO("PRO", "Pronoun"), NUM("NUM", "Number"), RES("RES", "Res");

    public String tag;
    public String description;

    PosTag(String tag, String description) {
        this.tag = tag;
        this.description = description;
    }
}
