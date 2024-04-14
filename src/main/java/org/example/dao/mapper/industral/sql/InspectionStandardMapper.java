package org.example.dao.mapper.industral.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.industral.sql.InspectionStandard;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Repository
public interface InspectionStandardMapper extends BaseMapper<InspectionStandard> {


    @Select("select * from em_asset_inspection_standard")
    List<InspectionStandard> selectAll();



    //9833   12003
    @Select("select * from em_asset_inspection_standard limit 3000 offset 17780")
    List<InspectionStandard> selectAll3000();


}
