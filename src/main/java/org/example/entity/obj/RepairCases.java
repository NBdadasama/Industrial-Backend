package org.example.entity.obj;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@NodeEntity
@AllArgsConstructor
@NoArgsConstructor
public class RepairCases {
    @Id
    @GeneratedValue
    protected Long nodeId;//节点id,主键
    @Property
    protected String category;//类的种类
    @Property
    protected String color;//前端颜色



    @Property
    protected String deviceId;//设备id

    @Property
    protected String deviceNumber;//设备编号












}
