package com.amin.nlp.pos;

import com.amin.nlp.utils.TextUtils;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amin on 6/27/2016.
 */
public class PosTagger {

    private static PosTagger mInstance;
    private MaxentTagger mMaxentTagger;
    private Map<String, PosTag> mTagSetMap;

    private PosTagger(String modelPath) throws IOException {
        mMaxentTagger = new MaxentTagger(modelPath);
        mTagSetMap = new HashMap<String, PosTag>();
        initTagSetMap();
    }

    public static PosTagger getInstance(String modelPath) throws IOException {
        if (mInstance == null) {
            mInstance = new PosTagger(modelPath);
        }
        return mInstance;
    }

    public List<PosTaggedWord> doTagging(List<String> sentenceWords) {
        String[] wordsArray = new String[sentenceWords.size()];
        for (int i = 0; i < sentenceWords.size(); i++) {
            wordsArray[i] = sentenceWords.get(i).replace(TextUtils.SPACE, "_");
        }
        List taggedSentence = mMaxentTagger.tagSentence(Sentence.toWordList(wordsArray));
        List<PosTaggedWord> result = new ArrayList<PosTaggedWord>();
        for (int i = 0; i < taggedSentence.size(); i++) {
            TaggedWord taggedWord = (TaggedWord) taggedSentence.get(i);
            PosTag posTag = mTagSetMap.get(taggedWord.tag());
            if(posTag == null){
                posTag = PosTag.RES; // maybe noun!?
            }
            result.add(new PosTaggedWord(sentenceWords.get(i), posTag));
        }
        return result;
    }

    private void initTagSetMap(){
        mTagSetMap.put("N", PosTag.N);
        mTagSetMap.put("Ne", PosTag.N);
        mTagSetMap.put("V", PosTag.V);
        mTagSetMap.put("Ve", PosTag.V);
        mTagSetMap.put("AJ", PosTag.ADJ);
        mTagSetMap.put("AJe", PosTag.ADJ);
        mTagSetMap.put("P", PosTag.P);
        mTagSetMap.put("Pe", PosTag.P);
        mTagSetMap.put("POSTP", PosTag.POSTP);
        mTagSetMap.put("POSTPe", PosTag.POSTP);
        mTagSetMap.put("CONJ", PosTag.CONJ);
        mTagSetMap.put("CONJe", PosTag.CONJ);
        mTagSetMap.put("ADV", PosTag.ADV);
        mTagSetMap.put("ADVe", PosTag.ADV);
        mTagSetMap.put("PUNC", PosTag.PUNC);
        mTagSetMap.put("PUNCe", PosTag.PUNC);
        mTagSetMap.put("DET", PosTag.DET);
        mTagSetMap.put("DETe", PosTag.DET);
        mTagSetMap.put("PRO", PosTag.PRO);
        mTagSetMap.put("PROe", PosTag.PRO);
        mTagSetMap.put("NUM", PosTag.NUM);
        mTagSetMap.put("NUMe", PosTag.NUM);
        mTagSetMap.put("RES", PosTag.RES);
        mTagSetMap.put("RESe", PosTag.RES);
    }
}