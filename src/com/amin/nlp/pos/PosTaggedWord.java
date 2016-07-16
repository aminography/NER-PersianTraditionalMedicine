package com.amin.nlp.pos;

/**
 * Created by Amin on 6/28/2016.
 */
public class PosTaggedWord {

    private String mLemma;
    private PosTag mPosTag;

    public PosTaggedWord(String lemma, PosTag posTag){
        mLemma = lemma;
        mPosTag = posTag;
    }

    public String getLemma() {
        return mLemma;
    }

    public PosTag getPosTag() {
        return mPosTag;
    }
}
