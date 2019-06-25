package com.s251437.KarlsonAdventures.control;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s251437.KarlsonAdventures.journey.Kid;

public class Utils {
    public static Kid getElementFromJSON(Gson gson, String elementInString) throws ReadElementFromJsonException, JsonSyntaxException {
        Kid element = gson.fromJson(elementInString, Kid.class);
        if (element == null || element.getName() == null || element.getAge() == 0) {
            throw new ReadElementFromJsonException();
        }
        return element;
    }
}
