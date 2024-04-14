package org.example.controller.vo.father;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeVo {

    protected Long nodeId;//节点id,主键

    protected String category;//类的种类

    protected String color;//前端颜色
    protected String nodeName;

    private float x;//坐标

    private float y;


}
