package org.example.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.obj1.BehavioralRecord;
import org.example.entity.obj1.Color;
import org.example.entity.obj1.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ColorMapper extends BaseMapper<Color> {//继承颜色类

    @Select("select * from node_color where user_id = #{category}")
    Color selectByCategory(String category);//获得颜色的类


}
