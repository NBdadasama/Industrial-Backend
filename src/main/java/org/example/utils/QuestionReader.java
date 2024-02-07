package org.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class QuestionReader {


    public static List<Float> readFloatsFromFile(String filePath) throws IOException {
        List<Float> floatVector = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                String[] floatStrings = line.split(",");
                floatVector = Arrays.stream(floatStrings).map(Float::parseFloat).collect(Collectors.toList());
            }
        }
        return floatVector;
    }


    public static List<String> readFileToList(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static void writeListToTxtFile(List<String> list, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String str : list) {
                bufferedWriter.write(str);
                bufferedWriter.newLine(); // 或者使用bufferedWriter.write("\n")
            }

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<List<Float>> readFileAndExtractFloats(String filePath) throws IOException {
        List<List<Float>> floatList = new ArrayList<>();
        List<Float> vectors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                vectors = Arrays.stream(values).map(Float::parseFloat).collect(Collectors.toList());
                System.out.println(vectors);
                System.out.println("vectors size:"+vectors.size());
                floatList.add(vectors);
            }
        }
        return floatList;
    }

    public static void main(String[] args) {
        String filePath = "E:\\IDEA_workplace\\Alexg-aidoc-demo\\embedding_text\\embedding_question_sentence.txt";
        try {
            List<Float> floats = readFloatsFromFile(filePath);
            System.out.println(floats);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
