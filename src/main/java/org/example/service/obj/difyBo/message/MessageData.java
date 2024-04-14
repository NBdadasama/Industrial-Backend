package org.example.service.obj.difyBo.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageData {

    private String id;
    private String conversationId;
    private Map<String,String> inputs;
    private String query;
    private String answer;
    private List<Object> messageFile;
    private String feedBack;//点赞或者点踩
    private Long createdAt;

}
