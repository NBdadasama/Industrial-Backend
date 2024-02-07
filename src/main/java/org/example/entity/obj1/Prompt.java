package org.example.entity.obj1;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Prompt {

    @TableId
    private Long id;


    private String template;

    private String promptId;//对应的模型类型


}
