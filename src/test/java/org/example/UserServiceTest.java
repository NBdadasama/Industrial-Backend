package org.example;
import com.alibaba.fastjson.JSON;
import org.example.controller.dto.UserDto;
import org.example.function.HttpClientUtils;
import org.example.service.UserService;
import org.example.service.obj.BehavioralRecordBo;
import org.example.service.obj.difyBo.message.HistoryMessagesBo;
import org.example.service.obj.difyBo.next.question.NestQuestionBo;
import org.example.service.obj.difyBo.user.history.UserHistoryBo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    UserService userService;



    @Value("${Dify.api}")
    String URL ;

    @Test
    public void TestPost(){

        String url=URL+"/v1/chat-messages";
        String str = "{\n" +
                "    \"inputs\": {},\n" +
                "    \"query\": \"汇报下预检台的检查标准\",\n" +
                "    \"response_mode\": \"blocking\",\n" +
                "    \"conversation_id\": \"cff63149-d8e1-4d71-92df-28fda6235c44\",\n" +
                "    \"user\": \"abc-123\",\n" +
                "    \"files\": []\n" +
                "}";
        try {
            String result = HttpClientUtils.post(url, str);

            result=HttpClientUtils.unicodeToString(result);

            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Test
    public void getUserHistoryTest() {

        String url=URL+"/v1/conversations?user="+"abc-123"+"&last_id=&limit="+"20";

        String result="";

        try {
            result = HttpClientUtils.get(url);

            result= HttpClientUtils.unicodeToString(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        UserHistoryBo userHistoryBo = JSON.parseObject(result , UserHistoryBo.class);//这里就可以做一些插入的内容

        System.out.println(userHistoryBo);
    }


    @Test
    public void getNextMessage() {

        String url=URL+"/v1/messages/"+"be55b031-86e7-4f6a-8b35-c20239bf3153"+"/suggested";//获得这句话的下一句记录
        String result="";
        System.out.println(url);
        try {
            result = HttpClientUtils.get(url);
            result= HttpClientUtils.unicodeToString(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        NestQuestionBo nestQuestionBo = JSON.parseObject(result , NestQuestionBo.class);//这里就可以做一些插入的内容


    }


    @Test
    public void getTalkHistory() {

        String url=URL+"/v1/messages?user="+"abc-123"+"&conversation_id="+"a327fdb9-53ae-4dcc-b3d1-317275ead614&limit=5";//获得这句话的下一句记录
        String result="";
        try {
            result = HttpClientUtils.get(url);
            result= HttpClientUtils.unicodeToString(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        HistoryMessagesBo historyMessagesBo = JSON.parseObject(result , HistoryMessagesBo.class);//这里就可以做一些插入的内容

        System.out.println(historyMessagesBo);

    }





}
