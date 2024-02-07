package org.example;


import org.example.service.AnswerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerServiceTest {


    @Resource
    public AnswerService answerService;





    @Test
    public void getAnswerAndQuestionBoTest(){
        System.out.println(answerService.getAnswerAndQuestionBo("100010"));

    }




}
