package org.example.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.obj1.Color;
import org.example.entity.obj1.SopId;
import org.example.entity.obj1.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface SopIdMapper extends BaseMapper<SopId> {

    @Select("SELECT max(sop_id) from sop_id")
    Integer selectMaxId();//获得颜色的类

}
