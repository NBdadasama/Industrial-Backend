package org.example.entity.obj;

//维护节点
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.controller.dto.MaintenanceDto;
import org.example.entity.obj.father.Node;
import org.example.function.NodeColor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@NodeEntity
@AllArgsConstructor
@NoArgsConstructor
public class Maintenance  {


    @Id
    @GeneratedValue
    protected Long nodeId;//节点id,主键
    @Property
    protected String category;//类的种类
    @Property
    protected String color;//前端颜色
    @Property
    private String deviceId;//设备的id，用来关联设备
    @Property
    private String maintenanceId;//维护节点的id
    @Property
    private String describe;//保养描述，如何进行保养，以及对保养的介绍
    @Property
    private String video;//保养视频
    @Property
    private String notice;//注意事项

    @Property
    private String name;//名字
    public Maintenance(MaintenanceDto maintenanceDto) {

        this.deviceId=maintenanceDto.getDeviceId();
        this.maintenanceId=maintenanceDto.getMaintenanceId();
        this.describe=maintenanceDto.getDescribe();
        this.video=maintenanceDto.getVideo();
        this.notice=maintenanceDto.getNotice();
        this.name=maintenanceDto.getName();
        this.color= NodeColor.nodeColor.MAINTENANCE_COLOR.getColor();//颜色
        this.category= Node.nodeCategory.MAINTENANCE_CATEGORY.getMessage();//种类
    }
}
