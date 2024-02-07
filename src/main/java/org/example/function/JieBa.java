package org.example.function;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.util.List;

public class JieBa {


    static JiebaSegmenter seg = new JiebaSegmenter();//分词




    public static List<String> participle(String string){



        List<String> result=seg.sentenceProcess(string);




        return  result;
    }

}
