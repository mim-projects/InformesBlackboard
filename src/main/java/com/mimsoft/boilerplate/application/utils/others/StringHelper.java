package com.mimsoft.boilerplate.application.utils.others;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringHelper {
    public static String StripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        return s;
    }

    public static String CleanStrLowerCase(Object str) {
        String result = String.valueOf(str).toLowerCase();
        result = StripAccents(result);
        return result;
    }

    public static String CleanRemoveSpaces(String str) {
        String result = CleanStrLowerCase(str);
        return result.replaceAll(" ", "");
    }

    public static int CompareStrings(String str1, String str2) {
        if (CleanStrLowerCase(str1).equals(CleanStrLowerCase(str2))) return 0;
        List<String> list = new ArrayList<>();
        list.add(CleanStrLowerCase(str1));
        list.add(CleanStrLowerCase(str2));
        Collections.sort(list);
        return list.get(0).equals(CleanStrLowerCase(str1)) ?1 :-1;
    }
}
