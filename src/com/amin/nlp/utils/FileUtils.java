package com.amin.nlp.utils;

import com.amin.nlp.dictionary.DictionaryMap;
import com.amin.nlp.dictionary.NamedEntityTag;
import com.amin.nlp.word.WordBoundaryTag;
import com.amin.nlp.word.Word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Amin on 6/24/2016.
 */
public class FileUtils {

    public static String readInputFile(String inputPath) {
        String content = "";
        try {
            for (String currentLine : Files.readAllLines(Paths.get(inputPath), Charset.forName("UTF8"))) {
                content += currentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = content.replaceAll("\\p{Cc}", ""); // remove control characters
        return content;
    }

    public static void readDictionary(String dictionaryPath, NamedEntityTag namedEntityTag) {
        try {
            for (String currentLine : Files.readAllLines(Paths.get(dictionaryPath), Charset.forName("UTF8"))) {
                currentLine = TextUtils.relaxChars(currentLine);
                currentLine = TextUtils.relaxSpaces(currentLine);
                currentLine = TextUtils.removePunctuations(currentLine);
                currentLine = currentLine.toLowerCase();
                DictionaryMap.putNamedEntity(currentLine, namedEntityTag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String content, String filePath) {
        try {
            File file = new File(filePath);
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int makeOutput(List<Word> wordsList, String outputPath, String outputDetailsPath) {
        int namedEntitiesCount = 0;
        String details = "";
        String output = "";
        for (int i = 0; i < wordsList.size(); i++) {
            Word currentWord = wordsList.get(i);
            Word nextWord = (i < wordsList.size() - 1) ? wordsList.get(i + 1) : null;
            switch (currentWord.getBoundary()) {
                case BEGINNING:
                    namedEntitiesCount++;
                    output += ("<" + currentWord.getNamedEntityTag().tag + ">");
                    output += currentWord.getLemma();
                    if (nextWord == null) {
                        output += ("</" + currentWord.getNamedEntityTag().tag + ">");
                    } else if (nextWord.getBoundary() == WordBoundaryTag.BEGINNING || nextWord.getBoundary() == WordBoundaryTag.OUTSIDE) {
                        output += ("</" + currentWord.getNamedEntityTag().tag + ">");
                    }
                    output += TextUtils.SPACE;
                    break;
                case INSIDE:
                    output += currentWord.getLemma();
                    if (nextWord == null) {
                        output += ("</" + currentWord.getNamedEntityTag().tag + ">");
                    } else if (nextWord.getBoundary() == WordBoundaryTag.BEGINNING || nextWord.getBoundary() == WordBoundaryTag.OUTSIDE) {
                        output += ("</" + currentWord.getNamedEntityTag().tag + ">");
                    }
                    output += TextUtils.SPACE;
                    break;
                case OUTSIDE:
                    output += currentWord.getLemma();
                    output += TextUtils.SPACE;
                    break;
            }
            details += (currentWord + "\n");
        }
        writeFile(output, outputPath);
        writeFile(details, outputDetailsPath);
        return namedEntitiesCount;
    }
}
