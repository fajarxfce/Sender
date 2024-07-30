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
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length >= 4) {
                    String firstName = data[0];
                    String lastName = data[1];
                    String emailAddress = data[2];
                    String password = data[3];
                    Smtp smtp = new Smtp(firstName, lastName, emailAddress, password);
                    smtps.add(smtp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return smtps;
    }
}