package org.example.milvus;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.param.ConnectParam;
import io.milvus.param.IndexType;
import io.milvus.param.R;
import io.milvus.param.collection.*;

public class HelloMilvus {
    static final MilvusServiceClient milvusClient = new MilvusServiceClient(
            ConnectParam.newBuilder()
                    .withHost("192.168.23.131")
                    .withPort(19530)
                    .build()
    );

    final static IndexType INDEX_TYPE = IndexType.IVF_FLAT;   // IndexType
    final static String INDEX_PARAM = "{\"nlist\":1024}";     // ExtraParam

    final static Integer SEARCH_K = 2;                       // TopK
    final static String SEARCH_PARAM = "{\"nprobe\":10, \"offset\":0}";    // Params


    public static void main(String[] args) {

        FieldType fieldType1 = FieldType.newBuilder()
                .withName("book_id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(false)
                .build();
        FieldType fieldType2 = FieldType.newBuilder()
                .withName("word_count")
                .withDataType(DataType.Int64)
                .build();
        FieldType fieldType3 = FieldType.newBuilder()
                .withName("book_intro")
                .withDataType(DataType.FloatVector)
                .withDimension(2)
                .build();
        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName("book")
                .withDescription("Test book search")
                .withShardsNum(2)
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .addFieldType(fieldType3)
                .withEnableDynamicField(true)
                .build();
        milvusClient.createCollection(createCollectionReq);
        R<Boolean> respHasCollection = milvusClient.hasCollection(
                HasCollectionParam.newBuilder()
                        .withCollectionName("book")
                        .build()
        );
        if (respHasCollection.getData() == Boolean.TRUE) {
            System.out.println("Collection exists.");
        }

    }



}
