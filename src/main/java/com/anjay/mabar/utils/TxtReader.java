package com.anjay.mabar.utils;

import com.anjay.mabar.models.EmailList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtReader {
    public static List<EmailList> readTxt(String filePath) {
        List<EmailList> emailLists = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                EmailList emailList = new EmailList(line);
                emailLists.add(emailList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emailLists;
    }
}