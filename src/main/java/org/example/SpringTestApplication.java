package org.example;
import com.huaban.analysis.jieba.WordDictionary;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.example.aisearch.Milvus.AISearch.initMilvus;

@SpringBootApplication
@MapperScan("org.example.dao.mapper")
@EnableAsync//启动异步调用
public class SpringTestApplication {


    //@Value("${JieBa.url}")//配置文件注入url类
    static String s="src/main/resources/fkck.txt";
    public static void main(String[] args) throws Exception {
        Path path = Paths.get(s);
        WordDictionary.getInstance().loadUserDict(path);



        SpringApplication.run(SpringTestApplication.class, args);

    }
}