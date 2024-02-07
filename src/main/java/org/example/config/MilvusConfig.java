package org.example.config;

import io.milvus.client.MilvusServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.example.aisearch.Milvus.AISearch.initMilvus;

@Configuration
public class MilvusConfig {

    @Bean
    public MilvusServiceClient milvusClient() throws Exception {
        return initMilvus();
    }

}
