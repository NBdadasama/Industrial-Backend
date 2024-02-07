package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj.Sop;
import org.example.entity.relation.SopToSop;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SopDto {
    private String deviceId;//隶属的设备
    private String faultId;//隶属的刮故障
    private String sopId;//隶属的SOP号码

    private String checkPoint;//检查部位
    private String describe;//工作描述

    private String notice;//注意事项
    private String video;//图片和视频
    private String picture;//图片和视频

    public SopDto(Sop sop) {

        this.deviceId = sop.getDeviceId();
        this.faultId = sop.getFaultId();
        this.sopId = sop.getSopId();
        this.describe = sop.getDescribe();
        this.video = sop.getVideo();
        this.picture = sop.getPicture();
        this.checkPoint=sop.getCheckPoint();
        this.notice=sop.getNotice();
    }

}
