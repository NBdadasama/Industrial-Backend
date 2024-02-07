package org.example.service.obj;


import lombok.Data;
import org.example.entity.obj1.AnswerQuestion;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@Data
@NodeEntity
public class AnswerQuestionBo implements Serializable {

    String answer;//回答

    String question;//问题


    public AnswerQuestionBo(AnswerQuestion answerQuestion) {
        this.answer=answerQuestion.getAnswer();

        this.question=answerQuestion.getQuestion();

    }
}
