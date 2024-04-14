package org.example.controller;


import org.example.controller.dto.QuestionDto;
import org.example.controller.vo.AnswerVo;
import org.example.entity.obj1.AnswerQuestion;
import org.example.function.Result;
import org.example.service.AnswerService;
import org.example.service.FunctionService;
import org.example.service.UserDialogDescriptionService;
import org.example.service.impl.UserDialogDescriptionServiceImpl;
import org.example.service.obj.BehavioralRecordBo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RestController
@RequestMapping("/gpt")
@CrossOrigin(origins = "*",allowCredentials="true")
public class AnswerController {//问答控制层

    @Resource
    private AnswerService answerService;

    @Resource
    private FunctionService functionService;



// todo 只有在发送第一个问题时，才需要创建对话
    @GetMapping("/create/conversation/{userId}")//创建对话
    public Result createConversation(@PathVariable String userId) {
        AnswerQuestion answerQuestion = answerService.createConversation(userId);
        functionService.saveRecord(BehavioralRecordBo.RecordCode.CREATE_CONSERVATION.getMessage(), answerQuestion.toString());//创建对话
        return Result.success(new AnswerVo(answerQuestion));
    }

    @PostMapping("/ask/question")//问问题
    public Result askQuestion(@RequestBody QuestionDto questionDto) throws Exception {
        AnswerQuestion answerQuestion = answerService.askQuestion(questionDto);
        functionService.saveRecord(BehavioralRecordBo.RecordCode.ASK_QUESTION.getMessage(), answerQuestion.toString());//问问题

        return Result.success(new AnswerVo(answerQuestion));
    }

    @GetMapping("/delete/conversation/{talkId}")//删除对话
    public Result deleteConversation(@PathVariable String talkId) {//删除对话
        answerService.deleteConversationByTalkId(talkId);

        functionService.saveRecord(BehavioralRecordBo.RecordCode.DELETE_CONVERSATION.getMessage(), talkId);//删除对话

        return Result.success("删除成功");
    }

    @GetMapping("/get/conversation/talkId/{talkId}")//查询对话
    public Result getConversationByTalkId(@PathVariable String talkId) {//根据userId查找部分
        List<AnswerVo> answerQuestionVoList = answerService.getConversationByTalkId(talkId);
        return Result.success(answerQuestionVoList);//返回list列表
    }

    @GetMapping("/get/conversation/userId/{userId}")//查找一个用户所有对话
    public Result getConversationByUserId(@PathVariable String userId) {//根据userId查找所有
        return Result.success(answerService.getConversationByUserId(userId));//返回list列表
    }

    @GetMapping("/get/conversation/description/userId/{userId}")//查找一个用户所有对话描述列表
    public Result getAllConversationDescriptionByUserId(@PathVariable String userId) {//根据userId查找所有
        return Result.success(answerService.getAllConversationDescriptionByUserId(userId));//返回list列表
    }


}
