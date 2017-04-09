package com.amin.nlp.utils;

import com.amin.nlp.Main;
import com.amin.nlp.word.PersianStemmer;
import com.amin.nlp.word.Word;
import com.amin.nlp.word.WordBoundaryTag;
import com.amin.nlp.pos.PosTaggedWord;
import com.amin.nlp.pos.PosTagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Amin on 6/23/2016.
 */
public class TextUtils {

    public static final String SPACE = "\u0020";

    public static final HashSet<String> TO_MERGE_SUFFIX_SET = new HashSet<String>();

    static {
        TO_MERGE_SUFFIX_SET.add("\u062A\u0631\u064A\u0646"); // tarin
        TO_MERGE_SUFFIX_SET.add("\u0627\u0628\u0627\u062F"); // Abad
        TO_MERGE_SUFFIX_SET.add("\u0622\u0628\u0627\u062F"); // Abad
        TO_MERGE_SUFFIX_SET.add("\u06AF\u064A\u0631\u064A"); // giri
        TO_MERGE_SUFFIX_SET.add("\u0647\u0627\u064A\u064A"); // haE
        TO_MERGE_SUFFIX_SET.add("\u0633\u0627\u0632\u064A"); // sazi
        TO_MERGE_SUFFIX_SET.add("\u0631\u064A\u0632\u064A"); // rizi
        TO_MERGE_SUFFIX_SET.add("\u0628\u0646\u062F\u064A"); // bandi
        TO_MERGE_SUFFIX_SET.add("\u0628\u0627\u0631\u0647"); // bare
        TO_MERGE_SUFFIX_SET.add("\u0647\u0627\u064A"); // haye
        TO_MERGE_SUFFIX_SET.add("\u0647\u0627"); // hA
    }

    public static List<Word> documentTokenizer(String inputPath) throws IOException {
        long startTime = System.currentTimeMillis();
        System.out.print("Reading and pre-processing input document from " + inputPath + " ... ");

        String content = FileUtils.readInputFile(inputPath);
        content = TextUtils.relaxSpaces(content);
        content = TextUtils.relaxChars(content);

        List<Word> wordsList = new ArrayList<Word>();
        List<String> sentenceTokens = TextUtils.sentenceTokenizer(content);
        for (String sentence : sentenceTokens) {
            List<String> wordTokens = TextUtils.wordTokenizer(sentence);
            List<String> wordTokensMerged = new ArrayList<String>();
            for (int i = 0; i < wordTokens.size(); i++) {
                if (i < wordTokens.size() - 1 && TO_MERGE_SUFFIX_SET.contains(wordTokens.get(i + 1))) {
                    wordTokensMerged.add(wordTokens.get(i) + "" + wordTokens.get(i + 1));
                    i++;
                } else {
                    wordTokensMerged.add(wordTokens.get(i));
                }
            }
            List<PosTaggedWord> taggedWordsList = PosTagger.getInstance(Main.POS_MODEL_PATH).doTagging(wordTokensMerged);
            for (int i = 0; i < taggedWordsList.size(); i++) {
                PosTaggedWord taggedWord = taggedWordsList.get(i);
                Word word = new Word(taggedWord.getLemma(), PersianStemmer.stem(taggedWord.getLemma()), taggedWord.getPosTag(), null, WordBoundaryTag.OUTSIDE, null, null);
                wordsList.add(word);
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("done [" + (endTime - startTime) + " ms].");

        return wordsList;
    }

    public static List<String> sentenceTokenizer(String text) {
        text = text.replaceAll("([!\\.\\??�]+)[ \\n]+", "$1\n\n");
        List<String> tokens = new ArrayList<String>();
        for (String sentence : text.split("\n\n")) {
            sentence = sentence.replace("\n", " ").trim();
            tokens.add(sentence);
        }
        return tokens;
    }

    public static List<String> wordTokenizer(String text) {
        text = text.replaceAll("([�!\\?]+|[:\\.\u060C\u061B�\\]\\)\\}\"�\\[\\(\\{])", " $1 ").trim();
        List<String> tokens = Arrays.asList(text.split(" +"));
        return tokens;
    }

    public static String removePunctuations(String token) {
        token = token.replaceAll("\\p{P}", ""); // remove punctuations
        return token;
    }

    public static String relaxSpaces(String token) {
        token = token.replace("\u00A0", ""); // remove non-break space
        token = token.replace('\u200C', '\u0020'); // semi-space to space
        return token;
    }

    public static String relaxChars(String token) {
        token = relaxSoundLetters(token);
        token = relaxYaLetters(token);
        token = relaxKafLetters(token);
        token = relaxAlefLetters(token);
        token = relaxVavLetters(token);
        token = relaxHehLetters(token);
        token = token.replace("\u0647\u200C\064A", "\u0647");
        token = token.replace("\u0647\u0020\064A", "\u0647");
        token = token.replace("\u0654", ""); // remove �
        token = token.replace("\u0640", ""); // remove �
        return token;
    }

    private static String relaxSoundLetters(String token) {
        token = token.replace("\u064B", "");
        token = token.replace("\u064C", "");
        token = token.replace("\u064D", "");
        token = token.replace("\u064F", "");
        token = token.replace("\u0650", "");
        token = token.replace("\u0651", "");
        token = token.replace("\u0652", "");
        token = token.replace("\u0653", "");
        return token;
    }

    private static String relaxHehLetters(String token) {
        token = token.replace('\u0629', '\u0647');
        token = token.replace('\u06BE', '\u0647');
        token = token.replace('\u06C0', '\u0647');
        token = token.replace('\u06C1', '\u0647');
        token = token.replace('\u06C2', '\u0647');
        token = token.replace('\u06C3', '\u0647');
        token = token.replace('\u06D5', '\u0647');
        token = token.replace('\u06FF', '\u0647');
        return token;
    }

    private static String relaxVavLetters(String token) {
        token = token.replace('\u0624', '\u0648');
        token = token.replace('\u0676', '\u0648');
        token = token.replace('\u0677', '\u0648');
        token = token.replace('\u06C4', '\u0648');
        token = token.replace('\u06C5', '\u0648');
        token = token.replace('\u06C6', '\u0648');
        token = token.replace('\u06C7', '\u0648');
        token = token.replace('\u06C8', '\u0648');
        token = token.replace('\u06C9', '\u0648');
        token = token.replace('\u06CA', '\u0648');
        token = token.replace('\u06CB', '\u0648');
        token = token.replace('\u06CF', '\u0648');
        token = token.replace('\u0778', '\u0648');
        token = token.replace('\u0779', '\u0648');
        return token;
    }

    private static String relaxAlefLetters(String token) {
//        token = token.replace('\u0622', '\u0627'); // Aye ba kolah
        token = token.replace('\u0623', '\u0627');
        token = token.replace('\u0625', '\u0627');
        token = token.replace('\u0671', '\u0627');
        token = token.replace('\u0672', '\u0627');
        token = token.replace('\u0673', '\u0627');
        token = token.replace('\u0675', '\u0627');
        token = token.replace('\u0773', '\u0627');
        token = token.replace('\u0774', '\u0627');
        return token;
    }

    private static String relaxYaLetters(String token) {
        token = token.replace('\u0620', '\u064A');
        token = token.replace('\u0626', '\u064A');
        token = token.replace('\u063D', '\u064A');
        token = token.replace('\u063E', '\u064A');
        token = token.replace('\u063F', '\u064A');
        token = token.replace('\u0649', '\u064A');
        token = token.replace('\u06CC', '\u064A');
        token = token.replace('\u06CD', '\u064A');
        token = token.replace('\u06CE', '\u064A');
        token = token.replace('\u06D0', '\u064A');
        token = token.replace('\u06D1', '\u064A');
        token = token.replace('\u0775', '\u064A');
        token = token.replace('\u0776', '\u064A');
        token = token.replace('\u0777', '\u064A');
        return token;
    }

    private static String relaxKafLetters(String token) {
        token = token.replace('\u063B', '\u06A9');
        token = token.replace('\u063C', '\u06A9');
        token = token.replace('\u0643', '\u06A9');
        token = token.replace('\u06AA', '\u06A9');
        token = token.replace('\u06AB', '\u06A9');
        token = token.replace('\u06AC', '\u06A9');
        token = token.replace('\u06AD', '\u06A9');
        token = token.replace('\u06AE', '\u06A9');
        token = token.replace('\u0762', '\u06A9');
        token = token.replace('\u0763', '\u06A9');
        token = token.replace('\u0764', '\u06A9');
        token = token.replace('\u077F', '\u06A9');
        return token;
    }

    public static String toBinaryString(int number, int length) {
        String binaryString = Integer.toBinaryString(number);
        if (binaryString.length() < length) {
            String tempZero = "";
            for (int j = 0; j < (length - binaryString.length()); j++) {
                tempZero += "0";
            }
            binaryString = tempZero.concat(binaryString);
        }
        return binaryString;
    }

    public static String concatenate(List<String> inputSequence, String delimiter) {
        String result = "";
        for (int i = 0; i < inputSequence.size(); i++) {
            String word = inputSequence.get(i);
            result += word;
            if (i != inputSequence.size() - 1) {
                result += delimiter;
            }
        }
        return result;
    }

    public static String toUnicode(String token) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < token.length(); i++) {
            int codePoint = Character.codePointAt(token, i);
            int charCount = Character.charCount(codePoint);
            if (charCount > 1) {
                i += charCount - 1;
                if (i >= token.length()) {
                    throw new IllegalArgumentException("truncated unexpectedly");
                }
            }
            if (codePoint < 128) {
                stringBuilder.appendCodePoint(codePoint);
            } else {
                stringBuilder.append(String.format("\\u%x", codePoint));
            }
        }
        return stringBuilder.toString();
    }
}
