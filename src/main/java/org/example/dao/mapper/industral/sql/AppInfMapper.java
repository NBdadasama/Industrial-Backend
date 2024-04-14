package org.example.dao.mapper.industral.sql;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.industral.sql.AppInf;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AppInfMapper extends BaseMapper<AppInf> {



    @Select("select * from em_asset_bom")
    List<AppInf> selectAll();


    @Select("SELECT * from em_asset_bom where asset_id=#{assetId} and parent_id=-1")
    AppInf selectDeviceByAssetId(Long assetId);//直接找到设备


    @Select("select * from em_asset_bom limit 10000 offset 5999")
    List<AppInf> selectAll3000();
}
