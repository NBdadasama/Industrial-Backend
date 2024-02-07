package org.example.controller.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj1.AnswerQuestion;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVo {

    private String talkId;//userId+key
    private String userId;
    private int keyId;//第几个对话框
    private String answer;
    private String question;
    private int number;//这个对话框的第几个问题
    private Timestamp answerTime;//回答时间
    private Timestamp questionTime;//提问时间

    private int showOrNot;

    public AnswerVo(AnswerQuestion answerQuestion) {

        this.talkId = answerQuestion.getTalkId();
        this.userId = answerQuestion.getUserId();
        this.keyId = answerQuestion.getKeyId();
        this.answer = answerQuestion.getAnswer();
        this.question = answerQuestion.getQuestion();
        this.number = answerQuestion.getNumber();
        this.answerTime = answerQuestion.getAnswerTime();
        this.questionTime = answerQuestion.getQuestionTime();

        this.showOrNot=answerQuestion.getShowOrNot();
    }
}
