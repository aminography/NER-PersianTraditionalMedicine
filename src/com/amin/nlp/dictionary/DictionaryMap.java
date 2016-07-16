package com.amin.nlp.dictionary;

import com.amin.nlp.utils.FileUtils;
import com.amin.nlp.utils.TextUtils;

import java.util.*;

/**
 * Created by Amin on 6/24/2016.
 */
public class DictionaryMap {

    public static final String DICTIONARY_BASE_PATH = "resources/dictionaries";
    public static final String DICTIONARY_DISEASE_PATH = DICTIONARY_BASE_PATH + "/disease.txt";
    public static final String DICTIONARY_HERB_PATH = DICTIONARY_BASE_PATH + "/herb.txt";
    public static final String DICTIONARY_LOCATION_CAPITAL_PATH = DICTIONARY_BASE_PATH + "/location-capital.txt";
    public static final String DICTIONARY_LOCATION_CONTINENT_PATH = DICTIONARY_BASE_PATH + "/location-continent.txt";
    public static final String DICTIONARY_LOCATION_COUNTRY_PATH = DICTIONARY_BASE_PATH + "/location-country.txt";
    public static final String DICTIONARY_LOCATION_IRAN_CITY_PATH = DICTIONARY_BASE_PATH + "/location-iran-city.txt";
    public static final String DICTIONARY_LOCATION_IRAN_PROVINCE_PATH = DICTIONARY_BASE_PATH + "/location-iran-province.txt";
    public static final String DICTIONARY_LOCATION_LAKE_PATH = DICTIONARY_BASE_PATH + "/location-lake.txt";
    public static final String DICTIONARY_LOCATION_OCEAN_PATH = DICTIONARY_BASE_PATH + "/location-ocean.txt";
    public static final String DICTIONARY_LOCATION_RIVER_PATH = DICTIONARY_BASE_PATH + "/location-river.txt";
    public static final String DICTIONARY_LOCATION_SEA_PATH = DICTIONARY_BASE_PATH + "/location-sea.txt";
    public static final String DICTIONARY_NATURE_PATH = DICTIONARY_BASE_PATH + "/nature.txt";
    public static final String DICTIONARY_INGREDIENT_PATH = DICTIONARY_BASE_PATH + "/ingredient.txt";
    public static final String DICTIONARY_ORGAN_PATH = DICTIONARY_BASE_PATH + "/organ.txt";

    private static Map<String, NamedEntityTag> mNamedEntitiesMap = new LinkedHashMap<String, NamedEntityTag>();

    public static void init() {
        long startTime = System.currentTimeMillis();
        System.out.print("Reading dictionaries from " + DICTIONARY_BASE_PATH + "/*.txt ... ");

        FileUtils.readDictionary(DICTIONARY_DISEASE_PATH, NamedEntityTag.DISEASE);
        FileUtils.readDictionary(DICTIONARY_HERB_PATH, NamedEntityTag.HERB);
        FileUtils.readDictionary(DICTIONARY_INGREDIENT_PATH, NamedEntityTag.INGREDIENT);
        FileUtils.readDictionary(DICTIONARY_LOCATION_CAPITAL_PATH, NamedEntityTag.LOCATION_CAPITAL);
        FileUtils.readDictionary(DICTIONARY_LOCATION_CONTINENT_PATH, NamedEntityTag.LOCATION_CONTINENT);
        FileUtils.readDictionary(DICTIONARY_LOCATION_COUNTRY_PATH, NamedEntityTag.LOCATION_COUNTRY);
        FileUtils.readDictionary(DICTIONARY_LOCATION_LAKE_PATH, NamedEntityTag.LOCATION_LAKE);
        FileUtils.readDictionary(DICTIONARY_LOCATION_IRAN_CITY_PATH, NamedEntityTag.LOCATION_IRAN_CITY);
        FileUtils.readDictionary(DICTIONARY_LOCATION_IRAN_PROVINCE_PATH, NamedEntityTag.LOCATION_IRAN_PROVINCE);
        FileUtils.readDictionary(DICTIONARY_LOCATION_OCEAN_PATH, NamedEntityTag.LOCATION_OCEAN);
        FileUtils.readDictionary(DICTIONARY_LOCATION_RIVER_PATH, NamedEntityTag.LOCATION_RIVER);
        FileUtils.readDictionary(DICTIONARY_LOCATION_SEA_PATH, NamedEntityTag.LOCATION_SEA);
        FileUtils.readDictionary(DICTIONARY_NATURE_PATH, NamedEntityTag.NATURE);
        FileUtils.readDictionary(DICTIONARY_ORGAN_PATH, NamedEntityTag.ORGAN);

        long endTime = System.currentTimeMillis();
        System.out.println("done [" + (endTime - startTime) + " ms].");
    }

    public static void putNamedEntity(String namedEntity, NamedEntityTag namedEntityTag) {
        List<String> list = generateSpaceStates(namedEntity);
        for (String ne : list) {
            mNamedEntitiesMap.put(ne, namedEntityTag);
        }
    }

    public static boolean contains(String namedEntity) {
        return mNamedEntitiesMap.containsKey(namedEntity.toLowerCase());
    }

    public static NamedEntityTag getTag(String namedEntity) {
        return mNamedEntitiesMap.get(namedEntity.toLowerCase());
    }

    private static List<String> generateSpaceStates(String phrase) {
        List<String> wordSequence = Arrays.asList(phrase.split(TextUtils.SPACE));
        List<String> result = new ArrayList<String>();
        int n = wordSequence.size();
        int m = n - 1; // number of spaces between n words
        for (int i = 0; i < Math.pow(2, m); i++) {
            String seq = "";
            seq += wordSequence.get(0);
            String binaryString = TextUtils.toBinaryString(i, m);
            for (int j = 0; j < m; j++) {
                if (binaryString.charAt(j) == '1') {
                    seq += TextUtils.SPACE;
                    seq += wordSequence.get(j + 1);
                } else {
                    seq += wordSequence.get(j + 1);
                }
            }
            result.add(seq);
        }
        return result;
    }
}
