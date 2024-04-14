package org.example.controller.vo.ind;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.vo.father.NodeVo;
import org.example.entity.industral.neo4j.IndInspection;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndInspectionVo extends NodeVo {



    private Long factoryId;//工厂id
    private Long deviceId;//设备编号
    private Long bomId;//部件id
    private String workDivision;//部件代码
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
    private String standardDate;
    private String attachmentFlg;//附件标识
    private String taskAssign;//任务归属
    private Long versionId;//版本Id
    private Long sort;

    public IndInspectionVo(IndInspection indInspection){
        this.category = indInspection.getCategory();
        this.color = indInspection.getColor();//颜色动态可变
        this.nodeId = indInspection.getNodeId();
        this.nodeName=indInspection.getInspectionStandard();//检查标准为名字


        this.factoryId = indInspection.getFactoryId();
        this.deviceId = indInspection.getDeviceId();
        this.bomId = indInspection.getBomId();
        this.workDivision = indInspection.getWorkDivision();
        this.maintenanceType = indInspection.getMaintenanceType();
        this.inspectCondition = indInspection.getInspectCondition();
        this.inspectionContent = indInspection.getInspectionContent();
        this.inspectionStandard = indInspection.getInspectionStandard();
        this.inspectionMethod = indInspection.getInspectionMethod();
        this.manHr = indInspection.getManHr();
        this.managementType = indInspection.getManagementType();
        this.cycleTime = indInspection.getCycleTime();
        this.cycleTimeUnit = indInspection.getCycleTimeUnit();
        this.cycleFrequency = indInspection.getCycleFrequency();
        this.standardDate = indInspection.getStandardDate();
        this.attachmentFlg = indInspection.getAttachmentFlg();
        this.taskAssign = indInspection.getTaskAssign();
        this.versionId = indInspection.getVersionId();
        this.sort = indInspection.getSort();
        // 根据实际情况初始化 category 和 color
        this.category = indInspection.getCategory();
        this.color = indInspection.getColor();
        // 假设 nodeId 是从 IndInspection 复制过来的
        this.nodeId = indInspection.getNodeId();


    }





}
