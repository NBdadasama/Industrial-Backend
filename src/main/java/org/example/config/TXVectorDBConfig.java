package org.example.config;

import com.tencent.tcvectordb.client.VectorDBClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.example.aisearch.txDB.AISearch.initTXVectorDB;

@Configuration
public class TXVectorDBConfig {

    @Bean
    public VectorDBClient vectorDBClient() throws Exception {
        return initTXVectorDB();
    }

}
