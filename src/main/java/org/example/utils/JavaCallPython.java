package org.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class JavaCallPython {

    public static void question_to_vector(String question) throws IOException, InterruptedException {
        // 指定Python程序的路径和参数
        String[] params = {"D:\\openai.wiki\\ChatGLM2-6B\\ENV\\python.exe", "D:\\openai.wiki\\M3E-base\\m3e-base-test-one-sentence-pure.py", question};
        // 执行Python程序
        Process process = Runtime.getRuntime().exec(params);
        // 读取Python程序的输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
        String line = reader.readLine();
        while (line != null) {
            // 打印输出
            System.out.println(line);
            // 将输出转换为向量
            String[] vector = line.substring(1, line.length() - 1).split(" ");

            System.out.println(vector);
            line = reader.readLine();
        }
        // 等待Python程序结束
        System.out.println(process.waitFor());
    }


    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        try {
            // 指定Python程序的路径和参数
            String[] params = {"D:\\openai.wiki\\ChatGLM2-6B\\ENV\\python.exe", "D:\\openai.wiki\\M3E-base\\m3e-base-test-one-sentence-pure.py", "E5是什么错误"};
            // 执行Python程序
            Process process = Runtime.getRuntime().exec(params);
            // 读取Python程序的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String line = reader.readLine();
            while (line != null) {
                // 打印输出
                System.out.println(line);
                // 将输出转换为向量
                String[] vector = line.substring(1, line.length() - 1).split(" ");

                System.out.println(vector);
                line = reader.readLine();
            }
            // 等待Python程序结束
            System.out.println(process.waitFor());
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}
