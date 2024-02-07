package org.example.service;

import com.tencent.tcvectordb.client.VectorDBClient;
import io.milvus.client.MilvusClient;
import io.milvus.client.MilvusServiceClient;
import org.springframework.stereotype.Service;


import static org.example.aisearch.Milvus.AISearch.askOnceChatglmAndMilvus;
import static org.example.aisearch.txDB.AISearch.askOnceBaiChuanAndTXVectorDB;

@Service
public class AisearchService {

    /**
     * 单次请求ChatGLM和Milvus向量库
     *
     * @param question
     * @param client
     * @return
     * @throws Exception
     */
    public String askChatglmAndMilvus(String question, MilvusServiceClient client) throws Exception {

        return askOnceChatglmAndMilvus(question,client);
    }

    /**
     * 单次请求Beichuan2和腾讯向量库
     *
     * @param question
     * @param client
     * @return
     * @throws Exception
     */
    public String askBaiChuanAndTXVectorDB(String question, VectorDBClient client) throws Exception {

        return askOnceBaiChuanAndTXVectorDB(question,client);
    }
}
