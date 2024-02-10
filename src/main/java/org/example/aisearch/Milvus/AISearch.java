package org.example.aisearch.Milvus;


import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvRow;
import io.milvus.client.MilvusServiceClient;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.grpc.DataType;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResults;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.*;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.response.SearchResultsWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.example.baichuan.BaiChuanLLM;
import org.example.chatglm.ChatGLM6B;
import org.example.factory.MilvusClientFactory;

import java.util.ArrayList;
import java.util.List;

import static org.example.utils.CsvReader.csvReadOperation;
import static org.example.utils.JavaCallPython.question_to_vector;
import static org.example.utils.QuestionReader.readFileAndExtractFloats;


/**
 * 基于 Milvus 向量库和百川2的AI搜索
 */
public class AISearch {
    private static final String DB_NAME = "ai_aux_db";
    private static final String COLLECTION_NAME = "ai_aux_collection";
    private static final IndexType INDEX_TYPE = IndexType.HNSW;
    private static final String INDEX_PARAM = "{\"M\":16,\"efConstruction\":200}";
    public static final String QUESTION_VECTOR = "E:\\IDEA_workplace\\Alexg-aidoc-demo\\embedding_text\\embedding_question_sentence.txt";
    private static final Double THRESHOLD = 0.8;

    private static Integer question_count = 0;
    private static final Integer MAXLENGTH = 10240;

    private static CreateCollectionParam initCreateEmbeddingCollectionParam(String collName) {
        FieldType fieldType1 = FieldType.newBuilder()
                .withName("id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(false)
                .build();
        FieldType fieldType2 = FieldType.newBuilder()
                .withName("type")
                .withDataType(DataType.VarChar)
                .withMaxLength(MAXLENGTH)
                .build();
        FieldType fieldType3 = FieldType.newBuilder()
                .withName("fault_display_mode")
                .withDataType(DataType.VarChar)
                .withMaxLength(MAXLENGTH)
                .build();
        FieldType fieldType4 = FieldType.newBuilder()
                .withName("fault_code")
                .withDataType(DataType.VarChar)
                .withMaxLength(MAXLENGTH)
                .build();
        FieldType fieldType5 = FieldType.newBuilder()
                .withName("fault_name")
                .withDataType(DataType.VarChar)
                .withMaxLength(MAXLENGTH)
                .build();
        FieldType fieldType6 = FieldType.newBuilder()
                .withName("possible_error_component")
                .withDataType(DataType.VarChar)
                .withMaxLength(MAXLENGTH)
                .build();
        FieldType fieldType7 = FieldType.newBuilder()
                .withName("text")
                .withDataType(DataType.FloatVector)
                .withDimension(768)
                .build();

        FieldType fieldType8 = FieldType.newBuilder()
                .withName("device_id")
                .withDataType(DataType.VarChar)
                .withMaxLength(MAXLENGTH)
                .build();
        FieldType fieldType9 = FieldType.newBuilder()
                .withName("oritext")
                .withDataType(DataType.VarChar)
                .withMaxLength(MAXLENGTH)
                .build();


        return CreateCollectionParam.newBuilder()
                .withDatabaseName(DB_NAME)
                .withCollectionName(COLLECTION_NAME)
                .withDescription("Test aux search")
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .addFieldType(fieldType3)
                .addFieldType(fieldType4)
                .addFieldType(fieldType5)
                .addFieldType(fieldType6)
                .addFieldType(fieldType7)
                .addFieldType(fieldType8)
                .addFieldType(fieldType9)
                .withEnableDynamicField(true)
                .build();

    }


    private static void initDatabase(MilvusServiceClient client) {
        System.out.println("initdatabase..");
        try {
            DropDatabaseParam dropDatabaseParam
                    = DropDatabaseParam.newBuilder().withDatabaseName(DB_NAME).build();
            client.dropDatabase(dropDatabaseParam);
        } catch (Exception e) {
            //ignore
        }
        CreateDatabaseParam createDatabaseParam = CreateDatabaseParam
                .newBuilder().withDatabaseName(DB_NAME).build();
        client.createDatabase(createDatabaseParam);


    }

    private static void initCollection(MilvusServiceClient client) {
        System.out.println("initcollection..");

        CreateCollectionParam param = initCreateEmbeddingCollectionParam(COLLECTION_NAME);
        R<RpcStatus> response = client.createCollection(param);
        if (response.getStatus() != R.Status.Success.getCode()) {
            System.out.println(response.getMessage());
        }
    }

    private static void writeKnowledgeByFile(MilvusServiceClient client) throws Exception {

        CsvContainer csv = csvReadOperation("E:\\IDEA_workplace\\Alexg-aidoc-demo\\fault\\aux_all_fault.csv");

        List<InsertParam.Field> fields = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<String> fault_display_modes = new ArrayList<>();
        List<String> fault_codes = new ArrayList<>();
        List<String> fault_names = new ArrayList<>();
        List<String> possible_error_components = new ArrayList<>();

        // 向量化故障简述
        List<List<Float>> texts = readFileAndExtractFloats("E:\\IDEA_workplace\\Alexg-aidoc-demo\\embedding_text\\embedding_text.txt");
        List<String> oritexts = new ArrayList<>();
        List<String> device_ids = new ArrayList<>();


        for (CsvRow row : csv.getRows()) {
            ids.add(Long.valueOf(row.getField("序号")));
            types.add(row.getField("型号"));
            fault_display_modes.add(row.getField("故障显示方式"));
            fault_codes.add(row.getField("故障代码"));
            fault_names.add(row.getField("故障名称"));
            possible_error_components.add(row.getField("可能的故障部位"));
            oritexts.add(row.getField("简述"));
            device_ids.add(row.getField("设备号"));
        }
        fields.add(new InsertParam.Field("id", ids));
        fields.add(new InsertParam.Field("type", types));
        fields.add(new InsertParam.Field("fault_display_mode", fault_display_modes));
        fields.add(new InsertParam.Field("fault_code", fault_codes));
        fields.add(new InsertParam.Field("fault_name", fault_names));
        fields.add(new InsertParam.Field("possible_error_component", possible_error_components));
        fields.add(new InsertParam.Field("text", texts));
        fields.add(new InsertParam.Field("device_id", device_ids));
        fields.add(new InsertParam.Field("oritext", oritexts));


        System.out.println("---------------------- insert ----------------------");
        InsertParam insertParam = InsertParam.newBuilder()
                .withDatabaseName(DB_NAME)
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName("_default")
                .withFields(fields)
                .build();
        R<MutationResult> result = client.insert(insertParam);
        if (result.getStatus() != R.Status.Success.getCode()) {
            System.out.println(result.getMessage());
        }
        System.out.println("---------------------- 插入结果：" + result.toString());
        System.out.println("---------------------- 插入完毕");


    }

    private static String searchKnowledge(String question, MilvusServiceClient client) throws Exception {

        StringBuilder allKnowledge = new StringBuilder();
        List<String[]> headers = new ArrayList<>();
        headers.add(new String[]{"id", "score", "type", "fault_code", "text", "device_id"});

        question_to_vector(question);

        List<List<Float>> targetVectors = readFileAndExtractFloats(QUESTION_VECTOR);

        // 查询
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withMetricType(MetricType.COSINE)
                .withTopK(10)
                .withVectors(targetVectors)
                .withVectorFieldName("text")
                .withConsistencyLevel(ConsistencyLevelEnum.EVENTUALLY)
                .addOutField("id")
                .addOutField("type")
                .addOutField("fault_code")
                .addOutField("possible_error_component")
                .addOutField("oritext")
                .build();
        R<SearchResults> results = client.search(searchParam);
        if (results.getStatus() != R.Status.Success.getCode()) {
            System.out.println(results.getMessage());
        }

        SearchResultsWrapper wrapper = new SearchResultsWrapper(results.getData().getResults());
        System.out.println("Search results:");
        for (int i = 0; i < targetVectors.size(); ++i) {
            List<SearchResultsWrapper.IDScore> scores = wrapper.getIDScore(i);
            for (SearchResultsWrapper.IDScore score : scores) {

                Float score1 = score.getScore();
                if (ObjectUtils.isEmpty(score) || score1 < THRESHOLD) {
                    continue;
                }
                System.out.println(score);
                allKnowledge.append("\t").append(score);
            }
        }


        // 获取搜索结果
        return allKnowledge.toString();
    }

    // 为向量编制索引
    private static void initIndex(MilvusServiceClient client) throws Exception {
        client.createIndex(
                CreateIndexParam.newBuilder()
                        .withCollectionName(COLLECTION_NAME)
                        .withFieldName("text")
                        .withIndexType(INDEX_TYPE)
                        .withMetricType(MetricType.COSINE)
                        .withExtraParam(INDEX_PARAM)
                        .withSyncMode(Boolean.TRUE)
                        .build()
        );
        FlushParam param = FlushParam.newBuilder()
                .addCollectionName(COLLECTION_NAME)
                .build();
        client.flush(param);
    }

    private static void initKnowledge(MilvusServiceClient client) throws Exception {
        initDatabase(client);
        initCollection(client);
        writeKnowledgeByFile(client);
        initIndex(client);

    }

    private static String getKnowledge(String question, MilvusServiceClient client) throws Exception {
        String knowledge = searchKnowledge(question, client);
        System.out.println("---->knowledge:" + knowledge);
        return knowledge;
    }

    private static String getLLMResult(String question, String knowledge) {
        String llmResult = ChatGLM6B.ask(question, knowledge);
        System.out.println("---->LLM回答结果：");
        System.out.println(llmResult);
        return llmResult;
    }

    public static String askOnceChatglmAndMilvus(String question, MilvusServiceClient client) throws Exception {
        String knowledge = getKnowledge(question, client);
        String llmResult = getLLMResult(question, knowledge);
        return llmResult;
    }

    public static MilvusServiceClient initMilvus() throws Exception {
        MilvusServiceClient client = MilvusClientFactory.createMilvusClient();

        initKnowledge(client);
        return client;
    }



    public static void main(String[] args) throws Exception {
        MilvusServiceClient client = initMilvus();
        askOnceChatglmAndMilvus("奥克斯主流变频空调显示故障代码E3是什么故障？", client);
    }

}