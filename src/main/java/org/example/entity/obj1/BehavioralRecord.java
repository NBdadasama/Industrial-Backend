package org.example.entity.obj1;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.function.TimeUtil;
import org.example.service.obj.BehavioralRecordBo;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("behavioral_record")
public class BehavioralRecord {


    @TableId
    private Long id;

    private String userName;

    private String userId;


    private Timestamp time;


    private String userFunction;

    private String object;


    public BehavioralRecord(BehavioralRecordBo behavioralRecordBo) {

        this.time= TimeUtil.getNowTime();//获取时间

        this.userName=behavioralRecordBo.getName();

        this.userId=behavioralRecordBo.getUserId();
        this.userFunction=behavioralRecordBo.getMsg();
        this.object=behavioralRecordBo.getObject();

    }
}
