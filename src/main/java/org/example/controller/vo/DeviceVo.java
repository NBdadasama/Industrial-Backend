package org.example.controller.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.vo.father.NodeVo;
import org.example.dao.mapper.ColorMapper;
import org.example.entity.obj.Device;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceVo extends NodeVo {
    private String deviceId;//设备的id，用来索引设备，和企业设备id对接
    private String devicePicture;//设备的图片
    private String name;//设备名字

    private String describe;//设备描述


    private List<String> notice;


    public DeviceVo(Device device) {

        this.category = device.getCategory();
        this.color = device.getColor();//颜色动态可变
        this.nodeId = device.getNodeId();

        this.deviceId = device.getDeviceId();
        this.devicePicture = device.getDevicePicture();
        this.name = device.getName();
        this.describe = device.getDescribe();

        if (device.getNotes() != null) {
            this.notice = Arrays.stream(device.getNotes().split(";")).collect(Collectors.toList());
        } else this.notice = null;


    }
}
