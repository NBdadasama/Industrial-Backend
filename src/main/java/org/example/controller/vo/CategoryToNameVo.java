package org.example.controller.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj1.CategoryToName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryToNameVo {


    private String name;//名字
    private int category;//类别
    private String color;//颜色


    public CategoryToNameVo(CategoryToName categoryToName) {
        this.name=categoryToName.getName();
        this.category=categoryToName.getCategory();
        this.color=categoryToName.getColor();

    }
}
