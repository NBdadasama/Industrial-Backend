package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {//问题

    private String userId;//问题的人

    // todo 只用talkId确定对话主题，用number确定主题内顺序，用userId来查询所有的主题，
    // todo keyId决定对话主题的时间顺序（待废弃想用时间戳）
    private int keyId;//第几个对话框（待废弃）
    private String talkId;//隶属对话框的id，userId+key
    private String question;//问题
    private int number;//第几个问题，这个需要递增,就是要比当前的数量大一


}
