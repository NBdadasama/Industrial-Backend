package org.example.function;

import java.util.Random;

public class NodeColor {


    public static String randomHexStr(int len) {
        try {
            StringBuffer result = new StringBuffer();
            for(int i=0;i<len;i++) {
                //随机生成0-15的数值并转换成16进制
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            return result.toString().toUpperCase();
        } catch (Exception e) {
            System.out.println("获取16进制字符串异常，返回默认...");
            return "00CCCC";
        }
    }






    public enum nodeColor {
        DEVICE_COLOR("#00FF00"),//设备
        FAULT_A_COLOR("#DC143C"),//故障
        FAULT_B_COLOR("#FFA500"),//故障
        FAULT_C_COLOR("#FFFF00"),//故障

        MAINTENANCE_COLOR("#7CFC00"),//保养
        SOP_COLOR("#1E90FF");//sop

        private String color;
        public String getColor() {
            return color;
        }
        nodeColor(String color) {
            this.color = color;
        }

    }











}
