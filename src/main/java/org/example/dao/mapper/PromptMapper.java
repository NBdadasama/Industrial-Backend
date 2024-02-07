package org.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.obj1.Prompt;

public interface PromptMapper extends BaseMapper<Prompt> {


    @Select("select * from prompt where prompt_id = #{promptId}")
    Prompt selectPrompt(String promptId);//获得颜色的类

}
