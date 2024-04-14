package org.example.entity.industral.sql;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("em_asset_inspection_standard")
public class InspectionStandard extends Father {

    @TableId
    private Long id;
    private Long factoryId;

    private Long assetId;//部位id,就是AppInf的Id

    private Long assetBomId;//部位id,就是AppInf的Id

    @TableField(value = "work_division")
    private String workDivision;//作业区分

    private String maintenanceType;//类型
    private String inspectCondition;//作业条件
    private String inspectionContent;
    private String inspectionStandard;//基准
    private String inspectionMethod;//方法
    private BigDecimal manHr;//标准工时
    private String managementType;
    private int cycleTime;
    private String cycleTimeUnit;//周期时间单位
    private int cycleFrequency;//周期次数
    private Timestamp standardDate;
    private String attachmentFlg;//附件标识
    private String taskAssign;//任务归属
    private Long versionId;//版本Id






}
