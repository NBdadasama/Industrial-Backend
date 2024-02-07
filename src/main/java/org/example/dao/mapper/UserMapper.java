package org.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.obj1.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where user_id = #{userId}")
    User selectByUserId(String userId);




}
