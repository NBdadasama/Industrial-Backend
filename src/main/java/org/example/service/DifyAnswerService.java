package org.example.service;


import com.alibaba.fastjson.JSON;
import org.example.controller.dto.DifyQuetionDto;
import org.example.function.HttpClientUtils;
import org.example.service.obj.difyBo.answer.DifyAnswerBo;
import org.example.service.obj.difyBo.message.HistoryMessagesBo;
import org.example.service.obj.difyBo.next.question.NestQuestionBo;
import org.example.service.obj.difyBo.user.history.UserHistoryBo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DifyAnswerService {

    @Value("${Dify.api}")
    String URL;
    public DifyAnswerBo askQuestion(DifyQuetionDto difyQuetionDto){
        String url=URL+"/v1/chat-messages";
        String str= JSON.toJSONString(difyQuetionDto);
        String result="";
        try {
            result = HttpClientUtils.post(url, str);
            result= HttpClientUtils.unicodeToString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DifyAnswerBo answerBo = JSON.parseObject(result , DifyAnswerBo.class);//这里就可以做一些插入的内容
        return answerBo;
    }


    public UserHistoryBo getUserHistory(String userId, int limit) {
        String url=URL+"/v1/conversations?user="+userId+"&last_id=&limit="+limit;
        String result="";
        try {
            result = HttpClientUtils.get(url);

            result= HttpClientUtils.unicodeToString(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        UserHistoryBo userHistoryBo = JSON.parseObject(result , UserHistoryBo.class);//这里就可以做一些插入的内容
        return userHistoryBo;
    }

    public NestQuestionBo getNextMessage(String messageId) {

        String url=URL+"/v1/messages/"+messageId+"/suggested";//获得这句话的下一句记录
        String result="";
        try {
            result = HttpClientUtils.get(url);
            result= HttpClientUtils.unicodeToString(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        NestQuestionBo nestQuestionBo = JSON.parseObject(result , NestQuestionBo.class);//这里就可以做一些插入的内容
        return nestQuestionBo;


    }

    public HistoryMessagesBo getTalkHistory(String userId, String talkId, int limit) {



        String url=URL+"/v1/messages/?user="+userId+"&conversation_id="+talkId+"&"+limit;//获得这句话的下一句记录
        String result="";
        try {
            result = HttpClientUtils.get(url);
            result= HttpClientUtils.unicodeToString(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        HistoryMessagesBo historyMessagesBo = JSON.parseObject(result , HistoryMessagesBo.class);//这里就可以做一些插入的内容
        return historyMessagesBo;

    }
}
