package org.example.controller.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.vo.father.NodeVo;
import org.example.entity.obj.Maintenance;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MaintenanceVo extends NodeVo {

    private String deviceId;//设备的id，用来关联设备
    private String maintenanceId;//维护节点的id
    private String describe;//保养描述，如何进行保养，以及对保养的介绍
    private String video;//保养视频
    private String notice;//注意事项

    private String name;




    public MaintenanceVo(Maintenance maintenance) {

        this.nodeId=maintenance.getNodeId();
        this.color= maintenance.getColor();
        this.category= maintenance.getCategory();//类别


        this.deviceId=maintenance.getDeviceId();
        this.maintenanceId=maintenance.getMaintenanceId();
        this.describe=maintenance.getDescribe();
        this.video=maintenance.getVideo();
        this.notice=maintenance.getNotice();
        this.name=maintenance.getName();


    }
}
