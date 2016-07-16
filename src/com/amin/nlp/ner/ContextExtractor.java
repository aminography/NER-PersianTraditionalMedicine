package com.amin.nlp.ner;

import com.amin.nlp.word.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amin on 6/30/2016.
 */
public class ContextExtractor {

    public static ContextInfo getContext(List<Word> tokensList, int position, int windowSize) {
        Word targetWord = tokensList.get(position);
        int start = (position - windowSize) >= 0 ? (position - windowSize) : 0;
        int end = (position + windowSize) < tokensList.size() ? (position + windowSize) : (tokensList.size() - 1);
        int minRange = start - position;
        int maxRange = end - position;
        Map<Integer, Word> contextMap = new HashMap<Integer, Word>();
        for (int i = start; i <= end; i++) {
            if (i == position) {
                continue;
            }
            contextMap.put(i - position, tokensList.get(i));
        }
        return new ContextInfo(targetWord, position, windowSize, contextMap, minRange, maxRange);
    }
}
