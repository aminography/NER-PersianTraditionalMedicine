package com.amin.nlp.dictionary;

/**
 * Created by Amin on 6/27/2016.
 */
public enum NamedEntityTag {
    DISEASE("disease"), HERB("herb"), INGREDIENT("ingredient"), LOCATION_CAPITAL("location_capital"),
    LOCATION_IRAN_CITY("location_iran_city"), LOCATION_CONTINENT("location_continent"),
    LOCATION_COUNTRY("location_country"), LOCATION_LAKE("location_lake"), LOCATION_OCEAN("location_ocean"),
    LOCATION_IRAN_PROVINCE("location_iran_province"), LOCATION_RIVER("location_river"), LOCATION_SEA("location_sea"),
    NATURE("nature"), ORGAN("organ");

    public String tag;

    NamedEntityTag(String tag) {
        this.tag = tag;
    }
}
