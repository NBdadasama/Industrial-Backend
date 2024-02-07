package org.example.controller;

import com.tencent.tcvectordb.client.VectorDBClient;
import io.milvus.client.MilvusClient;
import org.example.controller.vo.AnswerVo;
import org.example.function.Result;
import org.example.service.AisearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@Controller
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/aisearch")
public class AiSearchController {

    @Autowired
    public MilvusClient milvusClient;

    @Autowired
    public VectorDBClient vectorDBClient;

    @Resource
    public AisearchService aisearchService;

    /**
     * 向 ChatGLM 模型提问获取回答
     *
     * @param question
     * @return
     */
    @GetMapping("/get/chatglm/response/{question}")
    public Result getChatglmResponseByQuestion(@PathVariable(value="question") String question) throws Exception {
        String llmResponse = aisearchService.askBaiChuanAndTXVectorDB(question, vectorDBClient);
        return Result.success(llmResponse);
    }



}
