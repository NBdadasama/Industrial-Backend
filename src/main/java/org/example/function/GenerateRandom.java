package org.example.function;

import java.util.Random;

public class GenerateRandom {




    public static String getRandom(int number) {//获得验证码


        String sources = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 加上一些字母，就可以生成pc站的验证码了
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < number; j++)
        {
            flag.append(sources.charAt(rand.nextInt(sources.length())) + "");
        }
        System.out.println(flag.toString());

        return String.valueOf(flag);
    }


}
