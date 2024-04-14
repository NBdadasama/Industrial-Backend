package org.example.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DifyQuetionDto {


    private Map<String,String> inputs;
    private String response_mode;
    private String query;
    private String conversation_id;
    private String user;//用户名
    private Object files;


}
