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
@TableName("em_asset_maintenance_standard")
public class MaintenanceStandard extends Father{


    @TableId
    private Long id;
    private Long factoryId;

    private Long assetId;//部位id,就是AppInf的Id

    private Long assetBomId;//部位id,就是AppInf的Id
    @TableField(value = "work_division")
    private String workDivision;//作业区分
    private String maintenanceType;//类型


    private String maintenanceContent;
    private String managementType;//管理方式

    private BigDecimal cycleTime;
    private String cycleTimeUnit;//周期时间单位
    private int cycleFrequency;//周期次数

    private BigDecimal manHr;

    private Timestamp standardData;
    private String attachmentFlg;//附件标识
    private String taskAssign;//任务归属
    private String tools;//工具，带什么工具
    private Long versionId;

    private String inspectionStandard;

    private String maintenanceStandard;














}
