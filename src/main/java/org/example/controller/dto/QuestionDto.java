package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {//问题

    private String userId;//问题的人

    private int keyId;//第几个对话框
    private String talkId;//隶属对话框的id，userId+key
    private String question;//问题
    private int number;//第几个问题，这个需要递增,就是要比当前的数量大一


}
