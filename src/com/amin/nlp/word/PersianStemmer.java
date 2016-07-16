package com.amin.nlp.word;

import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Amin on 6/23/2016.
 */
public class PersianStemmer {

    private static Map<String, String> mCache = new WeakHashMap<String, String>();

    public static String stem(String word) {
        String result = mCache.get(word);
        if (result != null) {
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder(word);
        removeSuffix(stringBuilder);
        result = stringBuilder.toString().trim();
        mCache.put(word, result);
        return result;
    }

    private static void removeSuffix(StringBuilder word) {
        int len = word.length();
        if (len > 6) {
            if (word.substring(len - 4, len).equals("\u062A\u0631\u064A\u0646") || // tarin
                    word.substring(len - 4, len).equals("\u0627\u0628\u0627\u062F") || // Abad
                    word.substring(len - 4, len).equals("\u0622\u0628\u0627\u062F") || // Abad
                    word.substring(len - 4, len).equals("\u06AF\u064A\u0631\u064A") || // giri
                    word.substring(len - 4, len).equals("\u0647\u0627\u064A\u064A") || // haee
                    word.substring(len - 4, len).equals("\u0633\u0627\u0632\u064A") || // sazi
                    word.substring(len - 4, len).equals("\u0631\u064A\u0632\u064A") || // rizi
                    word.substring(len - 4, len).equals("\u0628\u0646\u062F\u064A") || // bandi
                    word.substring(len - 4, len).equals("\u0628\u0627\u0631\u0647")) // bare
            {
                word.delete(len - 4, len);
                return;
            }
        }
        if (len >= 5) {
            if (word.substring(len - 3, len).equals("\u0647\u0627\u064A") || // haye
                    word.substring(len - 3, len).equals("\u0627\u0646\u062F") || // and
                    word.substring(len - 3, len).equals("\u0627\u064A\u0645") || // eem
                    word.substring(len - 3, len).equals("\u0634\u0627\u0646")) // shan
            {
                word.delete(len - 3, len);
                return;
            }
        }
        if (len >= 4) {
            if (word.substring(len - 2, len).equals("\u0627\u0646")) // Çה
            {
                word.delete(len - 2, len);
                normalizeFirst(word);
                return;
            } else if (word.substring(len - 2, len).equals("\u0647\u0627") || // hA
                    word.substring(len - 2, len).equals("\u064A\u0646") || // En
                    word.substring(len - 2, len).equals("\u0627\u062A") || // At
                    word.substring(len - 2, len).equals("\u0627\u0634") || // ash
                    word.substring(len - 2, len).equals("\u062A\u0631") || // tar
                    word.substring(len - 2, len).equals("\u0631\u0627") || // rA
                    word.substring(len - 2, len).equals("\u0648\u0646") || // un
                    word.substring(len - 2, len).equals("\u0627\u0645")) // am
            {
                word.delete(len - 2, len);
                return;
            }
        }
        if (len > 2) {
            if (word.charAt(len - 1) == '\u0647' || // he
                    word.charAt(len - 1) == '\u064A' || // ye
                    word.charAt(len - 1) == '\u0645' || // me
                    word.charAt(len - 1) == '\u062A' || // te
                    word.charAt(len - 1) == '\u0634') // she
            {
                word.deleteCharAt(len - 1);
            }
        }
    }

    private static void normalizeFirst(StringBuilder word) {
        int len = word.length() - 1;
        if (len > 2) {
            if (word.charAt(len) == '\u064A' || // ye
                    word.charAt(len) == '\u06AF' || // ge
                    word.charAt(len) == '\u0645' || // me
                    word.charAt(len) == '\u062A' || // te
                    word.charAt(len) == '\u0631' || // re
                    word.charAt(len) == '\u0634') // she
            {
                word.deleteCharAt(len);
                normalizeSecond(word);
            }
        }
    }

    private static void normalizeSecond(StringBuilder word) {
        int len = word.length() - 1;
        if (len > 2) {
            if (word.charAt(len) == '\u064A') // ye
            {
                word.deleteCharAt(len);
            }
        }
    }
}
