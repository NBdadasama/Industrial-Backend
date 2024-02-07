package org.example.entity.obj;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.dto.SopDto;
import org.example.entity.obj.father.Node;
import org.example.function.NodeColor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@NodeEntity(label="SOP")
@AllArgsConstructor
@NoArgsConstructor
public class Sop {



    @Id
    @GeneratedValue
    protected Long nodeId;//节点id,主键


    @Property
    protected String category;//类的种类

    @Property
    protected String color;//前端颜色


    @Property
    private String deviceId;//隶属的设备

    @Property
    private String faultId;//隶属的刮故障

    @Property
    private String sopId;//隶属的刮故障




    private String checkPoint;//检查部位

    private String notice;//注意事项
    @Property
    private String describe;//工作描述

    @Property
    private String video;//图片和视频

    @Property
    private String picture;//图片和视频




    public Sop(SopDto sopDto) {

        this.deviceId = sopDto.getDeviceId();
        this.faultId = sopDto.getFaultId();
        this.describe = sopDto.getDescribe();
        this.video = sopDto.getVideo();
        this.picture = sopDto.getPicture();
        this.checkPoint=sopDto.getCheckPoint();
        this.notice=sopDto.getNotice();


        this.color= NodeColor.nodeColor.SOP_COLOR.getColor();
        this.category= Node.nodeCategory.SOP_CATEGORY.getMessage();//类别

    }
}
