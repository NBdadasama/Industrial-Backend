package org.example.controller.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.vo.father.NodeVo;
import org.example.entity.obj.Fault;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaultVo extends NodeVo {


    private String deviceId;//设备id
    private String name;//故障名字
    private String faultId;//故障编码
    private String describe;//故障描述,
    private String reason;//发生故障原因，字符串描述符号，包括故障部位等
    private String video;//维修视频地址
    private String rate;//故障等级
    private List<String> notice;//注意事项
    private String inspectionTool;

    public FaultVo(Fault fault) {//

        this.category=fault.getCategory();
        this.color=fault.getColor();
        this.nodeId=fault.getNodeId();


        this.deviceId=fault.getDeviceId();
        this.name=fault.getName();
        this.faultId=fault.getFaultId();
        this.describe=fault.getDescribe();
        this.reason=fault.getReason();
        this.video=fault.getVideo();
        this.rate=fault.getRate();
        this.inspectionTool = fault.getInspectionTool();


        if(fault.getNotice()!=null){
            this.notice= Arrays.stream(fault.getNotice().split(";")).collect(Collectors.toList());
        }
        else this.notice=null;


    }
}
