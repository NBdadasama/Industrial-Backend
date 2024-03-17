package org.example.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencent.tcvectordb.client.VectorDBClient;
import io.milvus.client.MilvusServiceClient;
import org.example.controller.dto.QuestionDto;
import org.example.controller.vo.AnswerVo;
import org.example.dao.mapper.AnswerQuestionMapper;
import org.example.dao.mapper.PromptMapper;
import org.example.entity.obj1.AnswerQuestion;
import org.example.entity.obj1.UserDialogDescription;
import org.example.function.RedisUtil;
import org.example.function.TimeUtil;
import org.example.service.impl.UserDialogDescriptionServiceImpl;
import org.example.service.obj.AnswerAndQuestionBo;
import org.example.service.obj.AnswerQuestionBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnswerService {


    @Resource
    private AnswerQuestionMapper answerQuestionMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AisearchService aisearchService;

    @Resource
    private UserDialogDescriptionServiceImpl userDialogDescriptionService;

    @Autowired
    private VectorDBClient vectorDBClient;

    @Autowired
    private MilvusServiceClient milvusServiceClient;

    @Value("${v1.ChooseDBAndModel}")
    private Integer ChooseDBAndModel;


    private PromptMapper promptMapper;

    /**
     * 新建一个主题
     *
     * @param userId
     * @return
     */
    public AnswerQuestion createConversation(String userId) {
        AnswerQuestion answerQuestion = this.getFirstAnswer(userId);
        System.out.println(answerQuestion);
        answerQuestionMapper.insert(answerQuestion);//插入数据库内
        return answerQuestion;

    }


    /**
     * 提问并获取大模型回答
     *
     * @param questionDto
     * @return
     * @throws Exception
     */
    public AnswerQuestion askQuestion(QuestionDto questionDto) throws Exception {

        AnswerQuestion answerQuestion = new AnswerQuestion(questionDto);
        String question = answerQuestion.getQuestion();
        String userId = answerQuestion.getUserId();
        int keyId = answerQuestion.getKeyId();
        String talkId = userId + String.valueOf(keyId);
        String answer = null;

        // 查看对话实复创建且只存在 1 个提示语（不存在则创建一个新的对话描述）
        if (answerQuestionMapper.selectAllByTalkId(talkId).size()==1) {
            UserDialogDescription userDialogDescription = new UserDialogDescription();
            userDialogDescription.setUserId(userId);
            userDialogDescription.setKeyId(keyId);
            userDialogDescription.setTalkId(talkId);
            // 截取question前20个字符作为描述
            userDialogDescription.setDescription(question.substring(0, Math.min(question.length(), 30)));
            userDialogDescriptionService.save(userDialogDescription);
        }


        //获取答案
        if (ChooseDBAndModel == 1) {
            answer = aisearchService.askBaiChuanAndTXVectorDB(question, vectorDBClient);
        } else if (ChooseDBAndModel == 2) {
            answer = aisearchService.askChatglmAndMilvus(question, milvusServiceClient);
        }
        //这里跨模块调用问题模块
        answerQuestion.setAnswer(answer);
        //获取时间
        answerQuestion.setAnswerTime(TimeUtil.getNowTime());
        //回答序号加1
        answerQuestion.setNumber(answerQuestion.getNumber() + 1);
        answerQuestionMapper.insert(answerQuestion);
        return answerQuestion;
    }

    /**
     * 获得第一个answer
     * todo （弃用）
     *
     * @param userId
     * @return
     */
    public AnswerQuestion getFirstAnswer(String userId) {
        Integer key = answerQuestionMapper.findUserKey(userId);
        if (key == null)
            key = 0;
        else key += 1;
        AnswerQuestion answerQuestion = new AnswerQuestion(null, userId + String.valueOf(key),
                userId, "欢迎使用空调设备运维问答系统，请输入您的问题：", null, 1, key, 1,
                TimeUtil.getNowTime(), TimeUtil.getNowTime());
        return answerQuestion;
    }


    /**
     * 删除一个主题
     *
     * @param talkId
     */
    public void deleteConversationByTalkId(String talkId) {
        AnswerQuestion answerQuestion = answerQuestionMapper.deleteByTalkId(talkId);//其实就是把字段更新为1
        userDialogDescriptionService.remove(new QueryWrapper<UserDialogDescription>().eq("talk_id", talkId));
    }


    /**
     * 查询主题下所有对话（刷新页面常用）
     *
     * @param talkId
     * @return
     */
    public List<AnswerVo> getConversationByTalkId(String talkId) {
        List<AnswerQuestion> answerQuestionList = answerQuestionMapper.selectAllByTalkId(talkId);
        List<AnswerVo> answerVoList = answerQuestionList.stream().map((AnswerQuestion answerQuestion) -> {
            return new AnswerVo(answerQuestion);
        }).collect(Collectors.toList());
        return answerVoList;
    }


    /**
     * 查询该用户下所有的主题（用来显示已经创建的所有主题供用户选择）
     *
     * @param userId
     * @return
     */
    public List<List<AnswerVo>> getConversationByUserId(String userId) {
        List<AnswerQuestion> answerQuestionList = answerQuestionMapper.selectAllByUserId(userId);
        Map<Integer, List<AnswerVo>> map = answerQuestionList.stream().map((AnswerQuestion answerQuestion) -> {
            return new AnswerVo(answerQuestion);//先转化为Vo对象
        }).filter(obj -> obj.getShowOrNot() == 1).//过滤掉不展示的对象
                collect(Collectors.groupingBy(AnswerVo::getKeyId));//按照keyId分类

        List<List<AnswerVo>> answerVoList = new ArrayList<>();
        map.forEach((keyId, value) -> answerVoList.add(value));//按照keyId分类

        // 排除只有提示语的对话主题
        List<List<AnswerVo>> collect = answerVoList.stream().filter(obj -> obj.size() > 1).collect(Collectors.toList());
        return collect;

    }

    /**
     * @param userId
     * @return
     */
    public List<UserDialogDescription>getAllConversationDescriptionByUserId(String userId) {
        QueryWrapper<UserDialogDescription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserDialogDescription> list = userDialogDescriptionService.list(queryWrapper);

        return list;

    }


    //这个函数是返回问答历史
    public AnswerAndQuestionBo getAnswerAndQuestionBo(String talkId) {//根据talkId获得问题获得历史

        if (redisUtil.get("HIS" + talkId) != null) {//有这个玩意

            return (AnswerAndQuestionBo) redisUtil.get("HIS" + talkId);//如果有就直接返回
        } else {//如果没有
            List<AnswerQuestion> answerQuestionList = answerQuestionMapper.selectAllByTalkId(talkId);

            List<AnswerQuestionBo> answerQuestionBos = answerQuestionList.stream().map((AnswerQuestion answerQuestion) -> {
                return new AnswerQuestionBo(answerQuestion);
            }).collect(Collectors.toList());

            AnswerAndQuestionBo answerAndQuestionBo = new AnswerAndQuestionBo(answerQuestionList, talkId);
            redisUtil.set("HIS" + talkId, answerQuestionBos, 60 * 60);//一个小时
            return answerAndQuestionBo;
        }
    }


    public String typeJudge(String question) {//类型判断
        AnswerAndQuestionBo answerAndQuestionBo = new AnswerAndQuestionBo();
        String talkId = "000000";
        if (redisUtil.get("HIS" + talkId) != null) {//有这个玩意
            answerAndQuestionBo = (AnswerAndQuestionBo) redisUtil.get("HIS000000");//如果有就直接返回
            //这里就问，然后返回
        } else {//如果没有
            List<AnswerQuestion> answerQuestionList = answerQuestionMapper.selectAllByTalkId(talkId);//获得谈话历史
            List<AnswerQuestionBo> answerQuestionBos = answerQuestionList.stream().map((AnswerQuestion answerQuestion) -> {
                return new AnswerQuestionBo(answerQuestion);
            }).collect(Collectors.toList());
            redisUtil.set("HIS" + talkId, answerQuestionBos);//永不过期
        }
        return "1";//返回类型
    }


    public String generatePrompt(String type, String question) {//获得prompt
        if (redisUtil.get("PROMPT" + type) != null) {
            String template = (String) redisUtil.get("PROMPT" + type);//如果有就直接返回
            template = template + "问题:" + question + "知识:" + "知识内容";
            return template;
        } else {

            String template = promptMapper.selectPrompt(type).getTemplate();
            redisUtil.set("PROMPT" + type, template);//写入redis
            return template;

        }


    }


}
