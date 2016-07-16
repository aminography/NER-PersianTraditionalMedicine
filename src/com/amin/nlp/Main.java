package com.amin.nlp;

import com.amin.nlp.dictionary.DictionaryMap;
import com.amin.nlp.ner.NamedEntityRecognizer;
import com.amin.nlp.pos.PosTagger;
import com.amin.nlp.utils.FileUtils;
import com.amin.nlp.utils.TextUtils;
import com.amin.nlp.word.Word;

import java.io.IOException;
import java.util.List;

public class Main {

    public static final String POS_MODEL_PATH = "resources/models/persian.tagger";
    public static final String INPUT_PATH = "input-data/input.txt";
    public static final String OUTPUT_PATH = "output-result/result.txt";
    public static final String OUTPUT_DETAIL_PATH = "output-result/result_detail.txt";
    public static final int CONTEXT_FORWARD_WINDOW_SIZE = 6;

    public static void main(String[] args) throws IOException {
        DictionaryMap.init();
        PosTagger.getInstance(POS_MODEL_PATH);
        List<Word> wordsList = TextUtils.documentTokenizer(INPUT_PATH);
        NamedEntityRecognizer.run(wordsList, CONTEXT_FORWARD_WINDOW_SIZE);
        int namedEntitiesCount = FileUtils.makeOutput(wordsList, OUTPUT_PATH, OUTPUT_DETAIL_PATH);
        System.out.println("\nTotal Tokens: " + wordsList.size() + "\nTotal NamedEntities: " + namedEntitiesCount);
    }
}
