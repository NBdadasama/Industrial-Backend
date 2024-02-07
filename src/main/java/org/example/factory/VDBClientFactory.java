package org.example.factory;

import com.tencent.tcvectordb.client.VectorDBClient;
import com.tencent.tcvectordb.model.param.database.ConnectParam;
import com.tencent.tcvectordb.model.param.enums.ReadConsistencyEnum;

public class VDBClientFactory {

    private static final String USERNAME = "root";
    private static final String KEY = "heAjaegnsZS8wMoclXRB3NaGPQ4yAhyM9oOpzsov";

    private static final String URL = "http://lb-8wn5zatf-cakd3m69w0elqnxg.clb.ap-beijing.tencentclb.com:20000";

    public static VectorDBClient createClient() {
        ConnectParam param = getConnectParam();
        return new VectorDBClient(param, ReadConsistencyEnum.EVENTUAL_CONSISTENCY);
    }

    private static ConnectParam getConnectParam() {
        return ConnectParam.newBuilder()
                .withUrl(URL)
                .withUsername(USERNAME)
                .withKey(KEY)
                .withTimeout(30)
                .build();
    }


}