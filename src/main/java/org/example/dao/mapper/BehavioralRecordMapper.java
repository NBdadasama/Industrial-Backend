package org.example.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.entity.obj1.BehavioralRecord;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BehavioralRecordMapper extends BaseMapper<BehavioralRecord> {


}
