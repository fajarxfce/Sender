package com.anjay.mabar.utils;

import com.anjay.mabar.models.Smtp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public static List<Smtp> readCsv(String filePath) {
        List<Smtp> smtps = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }
                String[] values = line.split(",");
                Smtp smtp = new Smtp();
                smtp.setEmailAddress(values[2]);
                smtp.setUsername(values[2]);
                smtp.setPassword(values[3]);
                smtps.add(smtp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return smtps;
    }
}