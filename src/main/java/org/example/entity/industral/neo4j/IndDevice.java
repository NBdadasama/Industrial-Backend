package org.example.entity.industral.neo4j;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.industral.sql.AppInf;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@NodeEntity(label="Device")
@AllArgsConstructor
@NoArgsConstructor
public class IndDevice {

    @Id
    @GeneratedValue
    private Long nodeId;//自动生成节点id


    @Property
    protected String category;//类的种类

    @Property
    protected String color;//前端颜色



    @Property
    private Long bomId;//部件id


    @Property
    private Long factoryId;//工厂id

    @Property
    private Long deviceId;//设备编号

    @Property
    private String boneCode;//部件代码

    @Property
    private String boneName;//

    @Property
    private int seq;//序号

    @Property
    private Long parentId;//父节点id

    @Property
    private String description;

    @Property
    private Long sort;

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

    public IndDevice(AppInf appInf){
        this.bomId = appInf.getId();
        this.factoryId = appInf.getFactoryId();
        this.deviceId = appInf.getAssetId();
        this.boneCode = appInf.getBoneCode();
        this.boneName = appInf.getBoneName();
        this.seq = appInf.getSeq();
        this.parentId = appInf.getParentId();
        this.description = appInf.getDescription();
        this.createBy = appInf.getCreateBy();
        this.createTime = appInf.getCreateTime().toString();
        this.updateBy = appInf.getUpdateBy();
        this.updateTime = appInf.getUpdateTime().toString();
        this.remark = appInf.getRemark();
        this.isDeleted = appInf.getIsDeleted();
        this.tenantId = appInf.getTenantId();
        this.sort = appInf.getSort();

    }








}
