package org.example.controller.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.vo.father.NodeVo;
import org.example.entity.obj.Sop;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SopVo extends NodeVo {


    private String deviceId;//隶属的设备

    private String faultId;//隶属的刮故障

    private String SOPId;//隶属的刮故障


    private String describe;//工作描述


    private List<String> notice;

    private String checkPoint;//检查部位

    private String video;//图片和视频

    private String picture;//图片和视频


    public SopVo(Sop sop) {
        this.nodeId=sop.getNodeId();
        this.color= sop.getColor();
        this.category= sop.getCategory();//类别

        this.deviceId = sop.getDeviceId();
        this.faultId = sop.getFaultId();
        this.SOPId = sop.getSopId();
        this.describe = sop.getDescribe();
        this.video = sop.getVideo();
        this.picture = sop.getPicture();
        this.checkPoint=sop.getCheckPoint();



        if(sop.getNotice()!=null){
            this.notice= Arrays.stream(sop.getNotice().split(";")).collect(Collectors.toList());
        }
        else this.notice=null;


    }




}
