package org.example.controller.vo.ind;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.vo.father.NodeVo;
import org.example.entity.industral.neo4j.IndDevice;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndDeviceVo extends NodeVo {


    private Long bomId;//部件id
    private Long factoryId;//工厂id
    private Long deviceId;//设备编号
    private String boneCode;//部件代码
    private String boneName;//
    private int seq;//序号
    private Long parentId;//父节点id
    private String description;
    private Long sort;
    public IndDeviceVo(IndDevice indDevice){

        this.category = indDevice.getCategory();
        this.color = indDevice.getColor();//颜色动态可变
        this.nodeId = indDevice.getNodeId();

        this.nodeName=indDevice.getBoneName();//部件名字作为节点名字


        this.bomId = indDevice.getBomId();
        this.factoryId = indDevice.getFactoryId();
        this.deviceId = indDevice.getDeviceId(); // 假设IndDevice有getDeviceId()方法
        this.boneCode = indDevice.getBoneCode(); // 假设IndDevice有getBoneCode()方法
        this.boneName = indDevice.getBoneName(); // 假设IndDevice有getBoneName()方法
        this.seq = indDevice.getSeq(); // 假设IndDevice有getSeq()方法
        this.parentId = indDevice.getParentId(); // 假设IndDevice有getParentId()方法
        this.description = indDevice.getDescription(); // 假设IndDevice有getDescription()方法
        this.sort = indDevice.getSort(); // 假设IndDevice有getSort()方法



    }




}
