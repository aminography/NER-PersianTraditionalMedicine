package com.amin.nlp.word;

import com.amin.nlp.dictionary.NamedEntityTag;
import com.amin.nlp.pos.PosTag;

/**
 * Created by Amin on 6/27/2016.
 */
public class Word {

    private String mLemma;
    private String mStem;
    private PosTag mPosTag;
    private NamedEntityTag mNamedEntityTag;
    private WordBoundaryTag mBoundary;
    private String mExtra;
    private Word mCoherentNextWord;

    public Word(String lemma, String stem, PosTag posTag, NamedEntityTag namedEntityTag, WordBoundaryTag boundary, String extra, Word coherentNextWord) {
        mLemma = lemma;
        mStem = stem;
        mPosTag = posTag;
        mNamedEntityTag = namedEntityTag;
        mBoundary = boundary;
        mExtra = extra;
        mCoherentNextWord = coherentNextWord;
    }

    public String getLemma() {
        return mLemma;
    }

    public void setLemma(String lemma) {
        mLemma = lemma;
    }

    public NamedEntityTag getNamedEntityTag() {
        return mNamedEntityTag;
    }

    public void setNamedEntityTag(NamedEntityTag namedEntityTag) {
        mNamedEntityTag = namedEntityTag;
    }

    public PosTag getPosTag() {
        return mPosTag;
    }

    public void setPosTag(PosTag posTag) {
        mPosTag = posTag;
    }

    public WordBoundaryTag getBoundary() {
        return mBoundary;
    }

    public void setBoundaryTag(WordBoundaryTag boundary) {
        mBoundary = boundary;
    }

    public String getExtra() {
        return mExtra;
    }

    public void setExtra(String extra) {
        mExtra = extra;
    }

    public String getStem() {
        return mStem;
    }

    public void setStem(String stem) {
        mStem = stem;
    }

    public Word getCoherentNextWord() {
        return mCoherentNextWord;
    }

    public void setCoherentNextWord(Word coherentNextWord) {
        mCoherentNextWord = coherentNextWord;
    }

    public boolean equals(Word another) {
        return another.getLemma().equals(mLemma);
    }

    @Override
    public String toString() {
        if (mBoundary == WordBoundaryTag.OUTSIDE) {
            return "Lemma(" + mLemma + "), Stem(" + mStem + "), PosTag(" + mPosTag.tag + "), NamedEntityTag(" + mBoundary.tag + ")";
        } else {
            return "Lemma(" + mLemma + "), Stem(" + mStem + "), PosTag(" + mPosTag.tag + "), NamedEntityTag(" + mBoundary.tag + "-" + mNamedEntityTag.tag + "), Extra(" + mExtra + ")";
        }
    }
}
