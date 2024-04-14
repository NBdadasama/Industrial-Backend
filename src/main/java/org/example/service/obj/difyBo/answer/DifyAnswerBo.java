package org.example.service.obj.difyBo.answer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DifyAnswerBo {



    private String event;

    private String taskId;

    private String id;

    private String messageId;
    private String mode;
    private String answer;

    private String createdAt;
    private String conversationId;

    private MetaData metaData;






}
