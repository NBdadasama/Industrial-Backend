package org.example.entity.obj1;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category_to_name")
public class CategoryToName {

    @TableId
    private Long id;

    private String name;

    private int category;


    private String color;//颜色


}
