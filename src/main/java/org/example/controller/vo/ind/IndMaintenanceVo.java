package org.example.controller.vo.ind;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.vo.father.NodeVo;
import org.example.entity.industral.neo4j.IndMaintenance;
import org.neo4j.ogm.annotation.Property;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndMaintenanceVo extends NodeVo {


    private Long factoryId;
    private Long deviceId;//部位id,就是AppInf的Id
    private Long bomId;//部位id,就是AppInf的Id
    private String workDivision;//作业区分
    private String maintenanceType;//类型
    private String maintenanceContent;
    private String managementType;//管理方式
    private BigDecimal cycleTime;
    private String cycleTimeUnit;//周期时间单位
    private int cycleFrequency;//周期次数
    private BigDecimal manHr;
    private String standardData;
    private String attachmentFlg;//附件标识
    private String taskAssign;//任务归属
    private String tools;//工具，带什么工具
    private Long versionId;
    private String inspectionStandard;
    private String maintenanceStandard;

    private String video;

    public IndMaintenanceVo(IndMaintenance indMaintenance){
        this.category = indMaintenance.getCategory();
        this.color = indMaintenance.getColor();//颜色动态可变
        this.nodeId = indMaintenance.getNodeId();
        this.nodeName=indMaintenance.getMaintenanceContent();



        this.factoryId = indMaintenance.getFactoryId();
        this.deviceId = indMaintenance.getDeviceId(); // 假设IndMaintenance有getDeviceId方法
        this.bomId = indMaintenance.getBomId(); // 假设IndMaintenance有getBomId方法
        this.workDivision = indMaintenance.getWorkDivision();
        this.maintenanceType = indMaintenance.getMaintenanceType();
        this.maintenanceContent = indMaintenance.getMaintenanceContent();
        this.managementType = indMaintenance.getManagementType();
        this.cycleTime = indMaintenance.getCycleTime(); // 假设周期时间是BigDecimal类型
        this.cycleTimeUnit = indMaintenance.getCycleTimeUnit();
        this.cycleFrequency = indMaintenance.getCycleFrequency();
        this.manHr = indMaintenance.getManHr();
        this.standardData = indMaintenance.getStandardData(); // 注意字段名的准确性
        this.attachmentFlg = indMaintenance.getAttachmentFlg();
        this.taskAssign = indMaintenance.getTaskAssign();
        this.tools = indMaintenance.getTools();
        this.versionId = indMaintenance.getVersionId();
        this.inspectionStandard = indMaintenance.getInspectionStandard();
        this.maintenanceStandard = indMaintenance.getMaintenanceStandard();
        this.video=indMaintenance.getVideo();


    }

}
