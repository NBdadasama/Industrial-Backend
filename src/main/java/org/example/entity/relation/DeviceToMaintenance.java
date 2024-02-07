package org.example.entity.relation;


import lombok.Data;
import org.example.entity.obj.Device;
import org.example.entity.obj.Fault;
import org.example.entity.obj.Maintenance;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity
@Data
public class DeviceToMaintenance {
    @Id
    @GeneratedValue
    private Long nodeId;



    @Property
    private String maintenanceId;//故障id，自己编码对故障进行决定

    @StartNode
    private Device from;//开始节点

    @EndNode
    private Maintenance to;//结束节点


    public DeviceToMaintenance(Device device, Maintenance maintenance) {
        this.from=device;
        this.to=maintenance;
        this.maintenanceId=maintenance.getMaintenanceId();
    }
}
