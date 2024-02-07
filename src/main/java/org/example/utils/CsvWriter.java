package org.example.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class CsvWriter {
    public static void csvWriteOperation(String path, Collection<String[]> data) {
        // 根据路径创建csv文件
        File file = new File(path);
        de.siegmar.fastcsv.writer.CsvWriter csvWriter = new de.siegmar.fastcsv.writer.CsvWriter();
        try {
            csvWriter.write(file, StandardCharsets.UTF_8,data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
