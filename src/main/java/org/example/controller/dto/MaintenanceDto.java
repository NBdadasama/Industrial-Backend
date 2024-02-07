package org.example.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.obj.Maintenance;
import org.neo4j.ogm.annotation.Property;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDto {
    private String deviceId;//设备的id，用来关联设备
    private String maintenanceId;//维护节点的id
    private String describe;//保养描述，如何进行保养，以及对保养的介绍
    private String video;//保养视频
    private String notice;//注意事项
    private String name;

    public MaintenanceDto(Maintenance maintenance) {
        if (maintenance != null) {
            this.deviceId = maintenance.getDeviceId();
            this.maintenanceId = maintenance.getMaintenanceId();
            this.describe = maintenance.getDescribe();
            this.video = maintenance.getVideo();
            this.notice = maintenance.getNotice();
            this.name = maintenance.getName();
        } else {
            // 处理maintenance为null的情况，例如初始化默认值或抛出异常等
        }

    }
}
