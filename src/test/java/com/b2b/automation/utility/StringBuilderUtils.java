package com.b2b.automation.utility;

public class StringBuilderUtils {

    public static String build(String... strings) {
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string);
        }
        return sb.toString();
    }
}
