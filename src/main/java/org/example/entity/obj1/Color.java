package org.example.entity.obj1;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("node_color")
public class Color {

    @TableId
    private Long id;

    private Long nodeId;


    private String category;//节点种类


    private String nodeColor;//节点颜色


}
