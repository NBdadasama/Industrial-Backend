package org.example.utils;

import de.siegmar.fastcsv.reader.CsvContainer;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class CsvReader {
    public static CsvContainer csvReadOperation(String path) {

        try {
            // File file = new File("E:\\IDEA_workplace\\Alexg-aidoc-demo\\fault\\aux_fault1_inside.csv");
            File file = new File(path);
            de.siegmar.fastcsv.reader.CsvReader csvReader = new de.siegmar.fastcsv.reader.CsvReader();
            csvReader.setContainsHeader(true);

            CsvContainer csv = csvReader.read(file, StandardCharsets.UTF_8);

            // 将解决中文乱码问题
            // CsvContainer csv = csvReader.read(file, Charset.forName("GBK"));
            // csv.getRowCount() 记录行数量
            // csv.getHeader().size() 记录 记录列数量
            // csv.getHeader().get(0) 编号
            // csv.getHeader().get(1) 时间
            return csv;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
