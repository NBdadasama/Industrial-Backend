package org.example.entity.obj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.dto.DeviceDto;
import org.example.entity.obj.father.Node;
import org.example.function.NodeColor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;


@Data
@NodeEntity(label="Device")
@AllArgsConstructor
@NoArgsConstructor
public class Device  {//设备类



    @Id
    @GeneratedValue
    protected Long nodeId;//节点id,主键

    @Property
    protected String category;//类的种类

    @Property
    protected String color;//前端颜色

    @Property
    private String deviceId;//设备的id，用来索引设备，和企业设备id对接

    @Property
    private String devicePicture;//设备的图片
    @Property
    private String name;//设备名字
    @Property
    private String describe;//设备描述

    @Property
    private String notes;//设备注意事项，预留的一个属性


    public Device(DeviceDto deviceDto) {
        this.devicePicture=deviceDto.getDevicePicture();//图片
        this.deviceId=deviceDto.getDeviceId();
        this.name=deviceDto.getName();
        this.describe=deviceDto.getDescribe();//描述
        this.notes=deviceDto.getNotes();//注意事项

        this.category= Node.nodeCategory.DEVICE_CATEGORY.getMessage();//设备种类默认为1
        this.color= NodeColor.nodeColor.DEVICE_COLOR.getColor();//颜色

    }


}
