package org.example.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj.Device;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto{//继承类
    private String deviceId;//设备的id，用来索引设备，和企业设备id对接
    private String devicePicture;//设备的图片
    private String name;//设备名字
    private String describe;//设备描述

    private String notes;//设备注意事项，非必要
    public DeviceDto(Device device){
        this.deviceId=device.getDeviceId();
        this.devicePicture=device.getDevicePicture();
        this.name=device.getName();
        this.describe=device.getDescribe();
        this.notes=device.getNotes();

    }



}
