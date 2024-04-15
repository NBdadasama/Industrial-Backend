package org.example.entity.industral.neo4j;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.industral.sql.InspectionStandard;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.math.BigDecimal;

@Data
@NodeEntity(label="Inspection")
@AllArgsConstructor
@NoArgsConstructor
public class IndInspection {

    @Id
    @GeneratedValue
    protected Long nodeId;//自动生成节点id


    @Property
    protected String category;//类的种类

    @Property
    protected String color;//前端颜色

    @Property
    private Long id;//自己的id

    @Property
    private Long factoryId;//工厂id
    @Property
    private Long deviceId;//设备编号

    @Property
    private Long bomId;//部件id

    @Property
    private String workDivision;//部件代码

    @Property
    private String maintenanceType;//类型
    @Property
    private String inspectCondition;//作业条件
    @Property
    private String inspectionContent;
    @Property
    private String inspectionStandard;//基准
    @Property
    private String inspectionMethod;//方法
    @Property
    private BigDecimal manHr;//标准工时
    @Property
    private String managementType;
    @Property
    private int cycleTime;
    @Property
    private String cycleTimeUnit;//周期时间单位
    @Property
    private int cycleFrequency;//周期次数
    @Property
    private String standardDate;
    @Property
    private String attachmentFlg;//附件标识
    @Property
    private String taskAssign;//任务归属
    @Property
    private Long versionId;//版本Id

    @Property
    private String video;

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


    public IndInspection(InspectionStandard inspectionStandard){



        this.id=inspectionStandard.getId();
        this.factoryId=inspectionStandard.getFactoryId();
        this.deviceId=inspectionStandard.getAssetId();
        this.bomId=inspectionStandard.getAssetBomId();
        this.workDivision=inspectionStandard.getWorkDivision();
        this.maintenanceType = inspectionStandard.getMaintenanceType();
        this.inspectCondition = inspectionStandard.getInspectCondition();
        this.inspectionContent = inspectionStandard.getInspectionContent();
        this.inspectionStandard = inspectionStandard.getInspectionStandard();
        this.inspectionMethod = inspectionStandard.getInspectionMethod();
        this.manHr=inspectionStandard.getManHr();
        this.managementType=inspectionStandard.getManagementType();
        this.cycleTime=inspectionStandard.getCycleTime();
        this.cycleTimeUnit=inspectionStandard.getCycleTimeUnit();
        this.cycleFrequency=inspectionStandard.getCycleFrequency();
        this.standardDate=inspectionStandard.getInspectionStandard();
        this.attachmentFlg=inspectionStandard.getAttachmentFlg();
        this.taskAssign=inspectionStandard.getTaskAssign();
        this.versionId=inspectionStandard.getVersionId();
        this.createBy = inspectionStandard.getCreateBy();
        this.createTime = inspectionStandard.getCreateTime().toString();
        this.updateBy = inspectionStandard.getUpdateBy();
        this.updateTime = inspectionStandard.getUpdateTime().toString();
        this.remark = inspectionStandard.getRemark();
        this.isDeleted = inspectionStandard.getIsDeleted();
        this.tenantId = inspectionStandard.getTenantId();




    }


}
