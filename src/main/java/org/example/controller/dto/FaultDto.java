package org.example.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj.Fault;
import org.neo4j.ogm.annotation.Property;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaultDto {



    private String deviceId;//设备id
    private String name;//故障名字
    private String faultId;//故障编码
    private String describe;//故障描述,
    private String reason;//发生故障原因，字符串描述符号，包括故障部位等
    private String video;//维修视频地址
    private String rate;//故障等级
    private String notice;//注意事项

    private String inspectionTool;//维修工具

    private String relatedKnowledge;//相关知识

    public FaultDto(Fault fault){
        if (fault != null) {
            this.deviceId = fault.getDeviceId();
            this.name = fault.getName();
            this.faultId = fault.getFaultId();
            this.describe = fault.getDescribe();
            this.reason = fault.getReason();
            this.video = fault.getVideo();
            this.rate = fault.getRate();
            this.notice = fault.getNotice();
            this.inspectionTool=fault.getInspectionTool();
        } else {
            // 处理传入的fault为null的情况，例如可以初始化默认值或者抛出异常等
        }

    }



}
