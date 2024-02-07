package org.example.factory;


import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;

public class MilvusClientFactory {

    private static final String USERNAME = "root";
    private static final String KEY = "heAjaegnsZS8wMoclXRB3NaGPQ4yAhyM9oOpzsov";

    // 虚拟机IP
    private static final String HOST = "8.134.192.208";

    // Milvus端口
    private static final Integer Port = 19530;

    // Milvus数据库名
    private static final String DB_NAME = "ai_aux_db";

    private static final String URL = "http://lb-8wn5zatf-cakd3m69w0elqnxg.clb.ap-beijing.tencentclb.com:20000";

    public static MilvusServiceClient createMilvusClient() {
        ConnectParam param = getConnectParam();
        return new MilvusServiceClient(param);
    }

    private static ConnectParam getConnectParam() {
        return ConnectParam.newBuilder()
                .withAuthorization("root", "Milvus")
                .withDatabaseName(DB_NAME)
                .withHost(HOST)
                .withPort(Port)
                .build();
    }


}