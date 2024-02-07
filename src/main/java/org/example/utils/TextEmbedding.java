package org.example.utils;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvRow;

import java.util.ArrayList;
import java.util.List;

import static org.example.utils.CsvReader.csvReadOperation;
import static org.example.utils.QuestionReader.writeListToTxtFile;


public class TextEmbedding {
    public static void main(String[] args) {
        CsvContainer csv = csvReadOperation("E:\\IDEA_workplace\\Alexg-aidoc-demo\\fault\\aux_all_fault.csv");
        List<String> fields = new ArrayList<>();
        for (CsvRow row : csv.getRows()) {

            String field = row.getField("简述");
            fields.add(field);
        }
        writeListToTxtFile(fields,"E:\\IDEA_workplace\\Alexg-aidoc-demo\\ori_text\\ori_text.txt");
    }
}
