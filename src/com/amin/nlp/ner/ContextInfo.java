package com.amin.nlp.ner;

import com.amin.nlp.word.Word;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amin on 6/30/2016.
 */
public class ContextInfo {

    private Word mTargetWord;
    private int mTargetPositionInText;
    private int mWindowSize;
    private int mMaxRange;
    private int mMinRange;
    private Map<Integer, Word> mContextMap = new HashMap<Integer, Word>();

    public ContextInfo(Word targetWord, int targetPositionInText, int windowSize, Map<Integer, Word> contextMap, int minRange, int maxRange) {
        mTargetWord = targetWord;
        mTargetPositionInText = targetPositionInText;
        mWindowSize = windowSize;
        mContextMap = contextMap;
        mMinRange = minRange;
        mMaxRange = maxRange;
    }

    public Word getContextWordAt(int index) {
        return mContextMap.get(index);
    }

    public Word getTargetWord() {
        return mTargetWord;
    }

    public int getTargetPositionInText() {
        return mTargetPositionInText;
    }

    public int getWindowSize() {
        return mWindowSize;
    }

    public int getMaxRange() {
        return mMaxRange;
    }

    public int getMinRange() {
        return mMinRange;
    }
}
