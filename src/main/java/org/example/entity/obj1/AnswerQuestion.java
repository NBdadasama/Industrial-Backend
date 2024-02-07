package org.example.entity.obj1;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.dto.QuestionDto;
import org.example.function.TimeUtil;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("answer_question")
public class AnswerQuestion {

    @TableId
    private Long id;
    private String talkId;

    private String userId;
    private String answer;
    private String question;
    private int number;

    private int keyId;

    private int showOrNot;//是否展示
    private Timestamp answerTime;
    private Timestamp questionTime;

    public AnswerQuestion(QuestionDto questionDto) {

        this.talkId = questionDto.getTalkId();
        this.userId=questionDto.getUserId();
        this.question = questionDto.getQuestion();
        this.number = questionDto.getNumber();//第几条对话
        this.keyId=questionDto.getKeyId();//第几个对话框
        this.questionTime= TimeUtil.getNowTime();//获取时间
        this.showOrNot=1;
    }
}
