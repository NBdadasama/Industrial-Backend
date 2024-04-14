package org.example.entity.industral.neo4j;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.industral.sql.MaintenanceStandard;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.math.BigDecimal;

@Data
@NodeEntity(label="Maintenance")
@AllArgsConstructor
@NoArgsConstructor
public class IndMaintenance {



    @Id
    @GeneratedValue
    protected Long nodeId;//自动生成节点id



    @Property
    protected String category;//类的种类

    @Property
    protected String color;//前端颜色

    @Property
    private Long id;//编号
    @Property
    private Long factoryId;
    @Property
    private Long deviceId;//部位id,就是AppInf的Id
    @Property
    private Long bomId;//部位id,就是AppInf的Id
    @Property
    private String workDivision;//作业区分
    @Property
    private String maintenanceType;//类型
    @Property
    private String maintenanceContent;
    @Property
    private String managementType;//管理方式
    @Property
    private BigDecimal cycleTime;
    @Property
    private String cycleTimeUnit;//周期时间单位
    @Property
    private int cycleFrequency;//周期次数
    @Property
    private BigDecimal manHr;
    @Property
    private String standardData;
    @Property
    private String attachmentFlg;//附件标识
    @Property
    private String taskAssign;//任务归属
    @Property
    private String tools;//工具，带什么工具
    @Property
    private Long versionId;
    @Property
    private String inspectionStandard;
    @Property
    private String maintenanceStandard;
    @Property
    private Long createBy;
    @Property
    private String createTime;
    @Property
    private Long updateBy;
    @Property
    private String updateTime;
    @Property
    private String remark;
    @Property
    private int isDeleted;
    @Property
    private Long tenantId;
    @Property
    private Long sort;
    public IndMaintenance(MaintenanceStandard maintenance){
        this.id=maintenance.getId();
        this.factoryId=maintenance.getFactoryId();
        this.deviceId=maintenance.getAssetId();
        this.bomId=maintenance.getAssetBomId();
        this.workDivision=maintenance.getWorkDivision();
        this.maintenanceType = maintenance.getMaintenanceType();
        this.maintenanceContent=maintenance.getMaintenanceContent();
        this.managementType=maintenance.getManagementType();
        this.manHr=maintenance.getManHr();
        this.tools=maintenance.getTools();
        this.cycleTime=maintenance.getCycleTime();
        this.cycleTimeUnit=maintenance.getCycleTimeUnit();
        this.cycleFrequency=maintenance.getCycleFrequency();
        this.standardData=maintenance.getInspectionStandard();
        this.attachmentFlg=maintenance.getAttachmentFlg();
        this.taskAssign=maintenance.getTaskAssign();
        this.versionId=maintenance.getVersionId();
        this.createBy = maintenance.getCreateBy();
        this.createTime = maintenance.getCreateTime().toString();
        this.updateBy = maintenance.getUpdateBy();
        this.updateTime = maintenance.getUpdateTime().toString();
        this.remark = maintenance.getRemark();
        this.isDeleted = maintenance.getIsDeleted();
        this.tenantId = maintenance.getTenantId();
        this.inspectionStandard=maintenance.getInspectionStandard();
        this.maintenanceStandard=maintenance.getMaintenanceStandard();


    }


}
