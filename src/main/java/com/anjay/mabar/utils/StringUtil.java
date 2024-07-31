package com.anjay.mabar.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String replacePlaceholders(String input) {
        Pattern pattern = Pattern.compile("##rand(\\w+?)(\\d*)##");
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String type = matcher.group(1);
            String lengthStr = matcher.group(2);
            int length = lengthStr.isEmpty() ? 10 : Integer.parseInt(lengthStr); // Default length is 10 if not specified
            String replacement = generateRandomValue(type, length);
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);

        return result.toString();
    }

    private static String generateRandomValue(String type, int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        switch (type) {
            case "str":
                for (int i = 0; i < length; i++) {
                    sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
                }
                break;
            case "int":
                for (int i = 0; i < length; i++) {
                    sb.append(random.nextInt(10)); // Random digit between 0-9
                }
                break;
            case "aes":
                // Add logic for session ID or other types if needed
                for (int i = 0; i < length; i++) {
                    sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown random type: " + type);
        }

        return sb.toString();
    }
}
