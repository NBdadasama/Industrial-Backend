package org.example.service.obj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj1.AnswerQuestion;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerAndQuestionBo implements Serializable {



    private List<AnswerQuestion> answerQuestionList;//这里面是问答
    private String talkId;//userId+key









}
