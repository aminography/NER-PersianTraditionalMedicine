package com.amin.nlp.ner;

import com.amin.nlp.dictionary.DictionaryMap;
import com.amin.nlp.dictionary.NamedEntityTag;
import com.amin.nlp.pos.PosTag;
import com.amin.nlp.word.WordBoundaryTag;
import com.amin.nlp.utils.TextUtils;
import com.amin.nlp.word.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amin on 6/29/2016.
 */
public class NamedEntityRecognizer {

    public static void run(List<Word> wordsList, int windowSize) {
        long startTime = System.currentTimeMillis();
        System.out.print("Recognizing named entities ... ");

        dictionaryMatching(wordsList, windowSize);
        ruleApplying(wordsList, windowSize);

        long endTime = System.currentTimeMillis();
        System.out.println("done [" + (endTime - startTime) + " ms].");
    }

    private static void dictionaryMatching(List<Word> wordsList, int windowSize) {
        for (int i = 0; i < wordsList.size(); i++) {
            List<Word> forwardNgram = createForwardNgram(wordsList, i, windowSize);
            List<List<Word>> ngramLists = createAllNgramLists(forwardNgram);
            Word word = wordsList.get(i);
            if (word.getPosTag() == PosTag.PUNC) {
                continue;
            }

            for (List<Word> ngram : ngramLists) {
                List<List<String>> stemmedOrPrimaryLists = generateAllPossibleStemmedOrPrimary(ngram);

                for (List<String> sequence : stemmedOrPrimaryLists) {
                    String seq = TextUtils.concatenate(sequence, TextUtils.SPACE);

                    if (DictionaryMap.contains(seq)) {
                        NamedEntityTag namedEntityTag = DictionaryMap.getTag(seq);

                        for (int j = 0; j < ngram.size(); j++) {
                            WordBoundaryTag boundary = (j == 0) ? WordBoundaryTag.BEGINNING : WordBoundaryTag.INSIDE;
                            Word w = ngram.get(j);

                            if (w.getNamedEntityTag() == null || (w.getExtra().length() < seq.length())) {
                                w.setNamedEntityTag(namedEntityTag);
                                w.setBoundaryTag(boundary);
                                w.setExtra(seq);
                            }
                            if (j > 0) {
                                ngram.get(j - 1).setCoherentNextWord(ngram.get(j));
                            }
                            if (j == ngram.size() - 1) {
                                ngram.get(j).setCoherentNextWord(null);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void ruleApplying(List<Word> wordsList, int windowSize) {
        for (int i = 0; i < wordsList.size(); i++) {
            Word word = wordsList.get(i);
            if (word.getPosTag() == PosTag.V || word.getPosTag() == PosTag.PUNC || word.getPosTag() == PosTag.NUM) {
                continue;
            }
            ContextInfo wordContextInfo = ContextExtractor.getContext(wordsList, i, windowSize);

            if (word.getLemma().equals("\u0628\u0647") || word.getStem().equals("\u0628\u0647")) { // beh (fruit)
                RulesContainer.RULE_FOR_DISAMBIGUATING_BEH.apply(wordContextInfo);
            }
            if (word.getNamedEntityTag() == NamedEntityTag.NATURE) {
                RulesContainer.RULE_FOR_NATURE.apply(wordContextInfo);
            }
            if (word.getNamedEntityTag() == NamedEntityTag.ORGAN) {
                RulesContainer.RULE_FOR_DISEASE.apply(wordContextInfo);
            }
        }
    }

    private static List<List<Word>> createAllNgramLists(List<Word> tokensList) {
        List<List<Word>> result = new ArrayList<List<Word>>();
        for (int end = 0; end < tokensList.size(); end++) {
            List<Word> subContext = tokensList.subList(0, end + 1);
            result.add(subContext);
        }
        return result;
    }

    private static List<Word> createForwardNgram(List<Word> tokensList, int position, int windowSize) {
        int end = (position + windowSize) < tokensList.size() ? (position + windowSize) : (tokensList.size());
        return tokensList.subList(position, end);
    }

    private static List<List<String>> generateAllPossibleStemmedOrPrimary(List<Word> inputWordList) {
        List<List<String>> result = new ArrayList<List<String>>();
        int n = inputWordList.size();
        for (int i = 0; i < Math.pow(2, n); i++) {
            List<String> list = new ArrayList<String>();
            String binaryString = TextUtils.toBinaryString(i, n);
            for (int j = 0; j < n; j++) {
                if (binaryString.charAt(j) == '1') {
                    list.add(inputWordList.get(j).getStem());
                } else {
                    list.add(inputWordList.get(j).getLemma());
                }
            }
            result.add(list);
        }
        return result;
    }
}
