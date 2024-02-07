package org.example.aisearch.txDB;


import com.tencent.tcvectordb.client.VectorDBClient;
import com.tencent.tcvectordb.exception.VectorDBException;
import com.tencent.tcvectordb.model.Collection;
import com.tencent.tcvectordb.model.Database;
import com.tencent.tcvectordb.model.DocField;
import com.tencent.tcvectordb.model.Document;
import com.tencent.tcvectordb.model.param.collection.*;
import com.tencent.tcvectordb.model.param.dml.HNSWSearchParams;
import com.tencent.tcvectordb.model.param.dml.InsertParam;
import com.tencent.tcvectordb.model.param.dml.SearchByEmbeddingItemsParam;
import com.tencent.tcvectordb.model.param.entity.SearchRes;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvRow;
import org.apache.commons.lang3.ObjectUtils;
import org.example.baichuan.BaiChuanLLM;
import org.example.factory.VDBClientFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import static com.tencent.tcvectordb.model.param.enums.EmbeddingModelEnum.BGE_BASE_ZH;
import static org.example.utils.CsvReader.csvReadOperation;
import static org.example.utils.CsvWriter.csvWriteOperation;
import static org.example.utils.QuestionReader.readFileToList;
import static org.example.utils.QuestionReader.writeListToTxtFile;

public class AISearch {
    private static final String DB_NAME = "ai_aux_db";
    private static final String COLLECTION_NAME = "ai_aux_collection";

    private static final Double THRESHOLD = 0.8;

    private static Integer question_count = 0;

    private static CreateCollectionParam initCreateEmbeddingCollectionParam(String collName) {

        return CreateCollectionParam.newBuilder()
                .withName(collName)
                .withShardNum(3)
                .withReplicaNum(0)
                .withDescription("测试AUX空调错误代码集")
                .addField(new FilterIndex("id", FieldType.String, IndexType.PRIMARY_KEY))
                .addField(new VectorIndex("vector", BGE_BASE_ZH.getDimension(), IndexType.HNSW,
                        MetricType.COSINE, new HNSWParams(16, 200)))
                .addField(new FilterIndex("fault_code", FieldType.String, IndexType.FILTER))
                .addField(new FilterIndex("fault_name", FieldType.String, IndexType.FILTER))
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

        CsvContainer csv = csvReadOperation("E:\\IDEA_workplace\\Alexg-aidoc-demo\\fault\\aux_all_fault.csv");

        List<Document> documentList = new ArrayList<>();
        for (CsvRow row : csv.getRows()) {
            Document document = Document.newBuilder()
                    .withId(row.getField("序号"))
                    .addDocField(new DocField("type", row.getField("型号")))
                    .addDocField(new DocField("fault_display_mode", row.getField("故障显示方式")))
                    .addDocField(new DocField("fault_code", row.getField("故障代码")))
                    .addDocField(new DocField("fault_name", row.getField("故障名称")))
                    .addDocField(new DocField("possible_error_component", row.getField("可能的故障部位")))
                    .addDocField(new DocField("text", row.getField("简述")))
                    .addDocField(new DocField("device_id", row.getField("设备号")))
                    .build();
            documentList.add(document);
        }


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
                .withLimit(8)
                // 使用 filter 过滤数据
                //.withFilter(new Filter("故障代码=\"E1\""))
                // 指定返回的 fields
                .withOutputFields(Arrays.asList("type", "fault_code", "text","device_id"))
                .build();

        SearchRes searchRes = collection.searchByEmbeddingItems(searchByEmbeddingItemsParam);
        StringBuilder allKnowledge = new StringBuilder();

        int i = 0;
        List<String[]> headers = new ArrayList<>();
        headers.add(new String[]{"id","score","type", "fault_code", "text","device_id"});
        for (List<Document> docs : searchRes.getDocuments()) {
            System.out.println("\tres: " + i++);

            for (Document doc : docs) {
                Double score = doc.getScore();
                if (ObjectUtils.isEmpty(score) || score < THRESHOLD) {
                    continue;
                }
                buildCSV(doc,headers);
                System.out.println("\t" + doc.toString());
//                System.out.println("\t" + doc.getDocFields().get(0).getName()+":"+doc.getDocFields().get(0).getStringValue());
//                System.out.println("\t" + doc.getDocFields().get(1).getName()+":"+doc.getDocFields().get(1).getStringValue());
//                System.out.println("\t" + doc.getDocFields().get(2).getName()+":"+doc.getDocFields().get(2).getStringValue());


                allKnowledge.append("\t").append(doc.toString());
            }


        }

//        csvWriteOperation("knowledgeCSV/knowledge_"+question_count+".csv",headers);
        // 获取搜索结果
        return allKnowledge.toString();
    }
    
    public static void buildCSV(Document document,List<String[]> csvRaw){

        Map<String, String> collect = document.getDocFields().stream().collect(Collectors.toMap(docField -> docField.getName(), docField -> docField.getStringValue()));
        csvRaw.add(new String[]{document.getId().toString(),document.getScore().toString(),collect.get("type"),
                collect.get("fault_code"), collect.get("text"), collect.get("device_id")});
    }

    private static void initKnowledge(VectorDBClient client) throws Exception {
        initDatabase(client);
        initCollection(client);
        writeKnowledgeByFile(client);
/*        String knowledge = searchKnowledge("空调温度升高发出嗡嗡声", client);
        System.out.println("knowledge:" + knowledge);
        String llmResult = BaiChuanLLM.ask("空调温度升高发出嗡嗡声", knowledge);
        System.out.println("---->LLM回答结果：");
        System.out.println(llmResult);*/
    }

    private static String getKnowledge(String question, VectorDBClient client) {
        String knowledge = searchKnowledge(question, client);
        System.out.println("---->knowledge:" + knowledge);
        return knowledge;
    }

    private static String getLLMResult(String question, String knowledge) {
        String llmResult = BaiChuanLLM.ask(question, knowledge);
        System.out.println("---->LLM回答结果：");
        System.out.println(llmResult);
        return llmResult;
    }

    public static String askOnceBaiChuanAndTXVectorDB(String question, VectorDBClient client) {
        String knowledge = getKnowledge(question, client);
        String llmResult = getLLMResult(question, knowledge);
        return llmResult;
    }

    public static void askList(List<String> question, VectorDBClient client) {
        List<String> result = new ArrayList<>();
        question_count = 0;
        for (String q : question) {
            question_count++;
            String knowledge = getKnowledge(q, client);
            String llmResult = getLLMResult(q, knowledge);
            result.add("---->问题：");
            result.add(q);
            result.add("---->向量库搜出来的knowledge：");
            result.add(knowledge);
            result.add("---->LLM回答结果：");
            result.add(llmResult);
            result.add("----------------------------------------------------");
        }
        writeListToTxtFile(result, "result/beichuan2/result.txt");
    }

    public static VectorDBClient initTXVectorDB() throws Exception {
        VectorDBClient client = VDBClientFactory.createClient();
        initKnowledge(client);
        return client;
    }


    public static void main(String[] args) throws Exception {
        VectorDBClient client = initTXVectorDB();
        String filePath = "question/question_class2.txt";
        List<String> question = readFileToList(filePath);
        initKnowledge(client);
        askList(question,client);
    }

}