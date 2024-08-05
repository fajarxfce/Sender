package com.anjay.mabar;

import com.anjay.mabar.utils.AESUtil;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";

    public static String replacePlaceholders(String input) {
        Pattern pattern = Pattern.compile("##rand(\\w+?)(\\d*)##");
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String type = matcher.group(1);
            String lengthStr = matcher.group(2);
            int length = lengthStr.isEmpty() ? 10 : Integer.parseInt(lengthStr);
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
                String plaintText = generateRandomNumber(100);
                String enc = AESUtil.encrypt(plaintText);
                sb.append(enc);
                break;
            case "lower":
                for (int i = 0; i < length; i++) {
                    sb.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
                }
                break;
            case "upper":
                for (int i = 0; i < length; i++) {
                    sb.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
                }
                break;
            case "num":
                for (int i = 0; i < length; i++) {
                    sb.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown random type: " + type);
        }

        return sb.toString();
    }

//    public static void main(String[] args) {
////        String input = "This is a test string with and ##randaes##.";
////        String output = replacePlaceholders(input);
////        System.out.println(output);
//
////        String output = AESUtil.encrypt("http://cms.fortytv.id:2021/login/api/v1/");
//        String output = AESUtil.encrypt("eyJpdiI6IkhaTElHZmRJb0dsek9ST0RkNHBxOFE9PSIsInZhbHVlIjoiU2xSY0JnMDRmWStXNWNQOGJRbTFzU2lQOSsyNzZvNlZ4UkMzRHZ0aHl6WE5jNUhqZTZETHNtVnNBRlVkb1o3K2NhZHlnMmV2YnkvdVdZaitXUzd6Y1cvOWFWZlF4MnNkanpWNFdhRGliWUNxbzZHZjBFWmhqMDlNb1RDZ1BtdVo5dGVZN2NYV1FqTFYvOUNGUkczMmM0TENoNWVDWlNMUVNhNVUwTSszRG0vQk0vUlg0bDhsWlYyNzFrU1FtV0lVTDk1QXFBbkpQYm93RVJJM0h6V1NETWFFLzFiZlE5QVArMFdGQiszeS9henpzMlYva2pJZDg3cHNpaVZJQmM1Uk54MTlRYmZ0OUZ0Qm5KdElZUGlKVU1adWxhSjlaN09BWU5SNWJVaGowVTNYRzRWYkpYYmhuOUQzZldKTzBtdFpWcWt5UzJOS2VHVE9CTG5STUUrekhONmh0dEZIbGY2M0xlRTlXU1dtaEZEeEJpUFN0TXMyU2FqQ2g1L0VtT05rIiwibWFjIjoiMTBlOTE0YmQzOGUzYjlmZmFiOWZiOWU0NGZkNjhmZjM2OTkzNmY0N2Q3MTQwNWJhZGIyNjc3NjllNmZmMDRkNCIsInRhZyI6IiJ9");
//        System.out.printf(output);
//    }

    public static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Random digit between 0-9
        }
        return sb.toString();
    }
}