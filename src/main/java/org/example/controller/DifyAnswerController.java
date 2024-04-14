package org.example.controller;


import org.example.controller.dto.DifyQuetionDto;
import org.example.function.Result;
import org.example.service.DifyAnswerService;
import org.example.service.obj.difyBo.answer.DifyAnswerBo;
import org.example.service.obj.difyBo.message.HistoryMessagesBo;
import org.example.service.obj.difyBo.user.history.UserHistoryBo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RestController
@RequestMapping("/dify")
@CrossOrigin(origins = "*",allowCredentials="true")
public class DifyAnswerController {


    @Resource
    private DifyAnswerService difyAnswerService;


    @PostMapping("/ask/question")//问问题
    public Result askQuestion(@RequestBody DifyQuetionDto difyQuetionDto) {
        DifyAnswerBo difyAnswerBo=difyAnswerService.askQuestion(difyQuetionDto);
        return Result.success(difyAnswerBo);
    }


    @GetMapping("/get/user/history/{userId}/{limit}")//获取用户会话历史
    public Result GetUserHistory(@PathVariable String userId,@PathVariable int limit)  {//获取对话历史

        UserHistoryBo userHistoryBo=difyAnswerService.getUserHistory(userId,limit);

        return Result.success();
    }


    /**
     * @param userId 用户id
     * @param talkId 对话框id
     * @return
     */
    @GetMapping("/get/talk/history/{userId}/{talkId}/{limit}")//获取某个对话框对话历史
    public Result GetTalkHistory(@PathVariable String userId, @PathVariable String talkId,@PathVariable int limit) {//获取对话历史


        HistoryMessagesBo historyMessagesBo=difyAnswerService.getTalkHistory( userId, talkId,limit);

        return Result.success();
    }


    /**
     * 获取下一个问题的建议
     * @param messageId 信息id
     * @return
     */
    @GetMapping("/get/next/suggested/{messageId}")//获取消息的下一个问题建议
    public Result GetNextMessage(@PathVariable String messageId) {//下一个问题建议


        difyAnswerService.getNextMessage(messageId);


        return Result.success();
    }
















}
