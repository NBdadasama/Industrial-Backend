package org.example.entity.obj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.dto.FaultDto;
import org.example.entity.obj.father.Node;
import org.example.function.NodeColor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@NodeEntity(label="Fault")
@AllArgsConstructor
@NoArgsConstructor
public class Fault {//故障节点



    @Id
    @GeneratedValue
    protected Long nodeId;//节点id,主键

    @Property
    protected String category;//类的种类

    @Property
    protected String color;//前端颜色


    @Property
    private String deviceId;//设备id

    @Property
    private String name;//故障名字

    @Property
    private String faultId;//故障编码
    @Property
    private String describe;//故障描述,
    @Property
    private String reason;//发生故障原因，字符串描述符号，包括故障部位等

    @Property
    private String video;//维修视频地址

    @Property
    private String rate;//故障等级

    @Property
    private String notice;//注意事项

    @Property
    private String inspectionTool;


    @Property
    private String relatedKnowledge;//相关知识

    public Fault(FaultDto faultDto) {
        this.deviceId=faultDto.getDeviceId();
        this.faultId=faultDto.getFaultId();
        this.describe=faultDto.getDescribe();
        this.reason=faultDto.getReason();
        this.video=faultDto.getVideo();
        this.rate=faultDto.getRate();
        this.name=faultDto.getName();
        this.notice=faultDto.getNotice();
        this.inspectionTool=faultDto.getInspectionTool();//维修工具

        if(this.rate.equals("A")) {//第一级别的故障
            this.color = NodeColor.nodeColor.FAULT_A_COLOR.getColor();
            this.category= Node.nodeCategory.FAULT_A_CATEGORY.getMessage();
        }
        else if(this.rate.equals("B")){
            this.color = NodeColor.nodeColor.FAULT_B_COLOR.getColor();
            this.category= Node.nodeCategory.FAULT_B_CATEGORY.getMessage();
        }
        else if(this.rate.equals("C")){
            this.color = NodeColor.nodeColor.FAULT_C_COLOR.getColor();
            this.category= Node.nodeCategory.FAULT_C_CATEGORY.getMessage();
        }



    }
}
