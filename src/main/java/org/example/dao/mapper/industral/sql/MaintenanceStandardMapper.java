package org.example.dao.mapper.industral.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.industral.sql.MaintenanceStandard;

import java.util.List;

public interface MaintenanceStandardMapper extends BaseMapper<MaintenanceStandard> {


    @Select("select * from em_asset_maintenance_standard")
    List<MaintenanceStandard> selectAll();

    @Select("select * from em_asset_maintenance_standard limit 3000")
    List<MaintenanceStandard> selectAll3000();

}
