package org.example.aisearch.txDB;

import com.tencent.tcvectordb.client.VectorDBClient;
import com.tencent.tcvectordb.exception.VectorDBException;
import com.tencent.tcvectordb.model.Collection;
import com.tencent.tcvectordb.model.Database;
import com.tencent.tcvectordb.model.DocField;
import com.tencent.tcvectordb.model.Document;
import com.tencent.tcvectordb.model.param.collection.*;
import com.tencent.tcvectordb.model.param.dml.Filter;
import com.tencent.tcvectordb.model.param.dml.HNSWSearchParams;
import com.tencent.tcvectordb.model.param.dml.InsertParam;
import com.tencent.tcvectordb.model.param.dml.SearchByEmbeddingItemsParam;
import com.tencent.tcvectordb.model.param.entity.SearchRes;
import org.apache.commons.lang3.ObjectUtils;
import org.example.factory.VDBClientFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tencent.tcvectordb.model.param.enums.EmbeddingModelEnum.BGE_BASE_ZH;

public class AISearchExample {
    private static final String DB_NAME = "ai_test_db";
    private static final String COLLECTION_NAME = "ai_test_collection";

    private static final Double THRESHOLD = 0.5;

    private static CreateCollectionParam initCreateEmbeddingCollectionParam(String collName) {




        return CreateCollectionParam.newBuilder()
                .withName(collName)
                .withShardNum(3)
                .withReplicaNum(0)
                .withDescription("test embedding collection0")
                .addField(new FilterIndex("id", FieldType.String, IndexType.PRIMARY_KEY))
                .addField(new VectorIndex("vector", BGE_BASE_ZH.getDimension(), IndexType.HNSW,
                        MetricType.COSINE, new HNSWParams(16, 200)))
                .addField(new FilterIndex("bookName", FieldType.String, IndexType.FILTER))
                .addField(new FilterIndex("author", FieldType.String, IndexType.FILTER))
                .withEmbedding(
                        Embedding
                                .newBuilder()
                                .withModel(BGE_BASE_ZH)
                                .withField("text")
                                .withVectorField("vector")
                                .build())
                .build();
    }


    private static void initDatabase(VectorDBClient client) {
        System.out.println("initdatabase..");
        try {
            client.dropDatabase(DB_NAME);
        } catch (VectorDBException e) {
            //ignore
        }
        client.createDatabase(DB_NAME);
    }

    private static void initCollection(VectorDBClient client) {
        System.out.println("initcollection..");
        Database database = client.database(DB_NAME);
        CreateCollectionParam param = initCreateEmbeddingCollectionParam(COLLECTION_NAME);
        database.createCollection(param);
    }

    private static void writeKnowledgeByFile(VectorDBClient client) throws Exception {
        Collection collection = client.database(DB_NAME).describeCollection(COLLECTION_NAME);

        List<Document> documentList = new ArrayList<>(Arrays.asList(
                Document.newBuilder()
                        .withId("0001")
                        .addDocField(new DocField("bookName", "西游记"))
                        .addDocField(new DocField("author", "吴承恩"))
                        .addDocField(new DocField("page", 21))
                        .addDocField(new DocField("segment", "富贵功名，前缘分定，为人切莫欺心。"))
                        .addDocField(new DocField("text", "富贵功名，前缘分定，为人切莫欺心。"))
                        .build(),
                Document.newBuilder()
                        .withId("0002")
                        .addDocField(new DocField("bookName", "西游记"))
                        .addDocField(new DocField("author", "吴承恩"))
                        .addDocField(new DocField("page", 22))
                        .addDocField(new DocField("segment",
                                "正大光明，忠良善果弥深。些些狂妄天加谴，眼前不遇待时临。"))
                        .addDocField(new DocField("text",
                                "正大光明，忠良善果弥深。些些狂妄天加谴，眼前不遇待时临。"))
                        .build(),
                Document.newBuilder()
                        .withId("0003")
                        .addDocField(new DocField("bookName", "三国演义"))
                        .addDocField(new DocField("author", "罗贯中"))
                        .addDocField(new DocField("page", 23))
                        .addDocField(new DocField("segment", "细作探知这个消息，飞报吕布。"))
                        .addDocField(new DocField("text", "细作探知这个消息，飞报吕布。"))
                        .build(),
                Document.newBuilder()
                        .withId("0004")
                        .addDocField(new DocField("bookName", "三国演义"))
                        .addDocField(new DocField("author", "罗贯中"))
                        .addDocField(new DocField("page", 24))
                        .addDocField(new DocField("segment", "富贵功名，前缘分定，为人切莫欺心。"))
                        .addDocField(new DocField("text", "富贵功名，前缘分定，为人切莫欺心。"))
                        .build(),
                Document.newBuilder()
                        .withId("0005")
                        .addDocField(new DocField("bookName", "三国演义"))
                        .addDocField(new DocField("author", "罗贯中"))
                        .addDocField(new DocField("page", 25))
                        .addDocField(new DocField("segment",
                                "布大惊，与陈宫商议。宫曰：“闻刘玄德新领徐州，可往投之。"))
                        .addDocField(new DocField("text",
                                "布大惊，与陈宫商议。宫曰：“闻刘玄德新领徐州，可往投之。"))
                        .build()));
        System.out.println("---------------------- upsert ----------------------");
        InsertParam insertParam = InsertParam.newBuilder()
                .addAllDocument(documentList)
                .withBuildIndex(true)
                .build();
        collection.upsert(insertParam);


/*        for (String f:
             Objects.requireNonNull(new File("doc").list())) {
            String filePath = "doc/" + f;
            System.out.println("uploadfile" + filePath);
            collection.upsert()
            collection.upload(filePath, Collections.emptyMap());
        }

        System.out.println("allfileuploaded.");
        System.out.println("文件上传后，向量数据库会进行解析和Embedding，请耐心等待10-20秒后可以开始进行知识检索。");*/
    }
    
     private static String searchKnowledge(String question, VectorDBClient client) {
        // 访问指定的表
        Collection collection = client.database(DB_NAME).describeCollection(COLLECTION_NAME);

         // searchByEmbeddingItems 返回类型为 SearchRes，接口查询过程中 embedding 可能会出现截断
         // 如发生截断将会返回响应 warn 信息，如需确认是否截断可以使用 SearchRes#getWarning" 获取警告信息，
         // 查询结果可以通过 SearchRes#getDocuments
         System.out.println("---------------------- searchByEmbeddingItems ----------------------");
         SearchByEmbeddingItemsParam searchByEmbeddingItemsParam = SearchByEmbeddingItemsParam.newBuilder()
                 .withEmbeddingItems(Arrays.asList(question))
                 // 若使用 HNSW 索引，则需要指定参数 ef，ef 越大，召回率越高，但也会影响检索速度
                 .withParams(new HNSWSearchParams(200))
                 // 设置标量字段的 Filter 表达式，过滤所需查询的文档
                 .withRetrieveVector(false)
                 // 指定 Top K 的 K 值
                 .withLimit(5)
                 // 使用 filter 过滤数据
                 .withFilter(new Filter("bookName=\"三国演义\""))
                 // 指定返回的 fields
                 .withOutputFields(Arrays.asList("id", "bookName","segment"))
                 .build();

         SearchRes searchRes = collection.searchByEmbeddingItems(searchByEmbeddingItemsParam);
         StringBuilder allKnowledge = new StringBuilder();

         int i = 0;
         for (List<Document> docs : searchRes.getDocuments()) {
             System.out.println("\tres: " + i++);
             for (Document doc : docs) {
                 Double score = doc.getScore();
                 if (ObjectUtils.isEmpty(score) || score < THRESHOLD) {
                     continue;
                 }

                 System.out.println("\t" + doc.toString());
                 allKnowledge.append("\t").append(doc.toString());
             }
         }



        // 获取搜索结果
        return allKnowledge.toString();
    }

    private static void initKnowledge(VectorDBClient client) throws Exception {
        initDatabase(client);
        initCollection(client);
        writeKnowledgeByFile(client);
        searchKnowledge("陈宫商议",client);
    }

    public static void main(String[] args) throws Exception {
        initKnowledge(VDBClientFactory.createClient());
    }

}