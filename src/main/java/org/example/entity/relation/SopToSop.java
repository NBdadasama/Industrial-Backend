package org.example.entity.relation;


import lombok.Data;
import org.example.controller.dto.SopRelationDto;
import org.example.entity.obj.Sop;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity
@Data
public class SopToSop {


    @Id
    @GeneratedValue
    private Long nodeId;

    @Property
    private String deviceId;//故障id，自己编码对故障进行决定

    @Property
    private String faultId;//故障id，自己编码对故障进行决定

    @Property
    private String describe;//故障id，自己编码对故障进行决定

    @Property
    private int category;//故障id，自己编码对故障进行决定

    @StartNode
    private Sop from;//开始节点

    @EndNode
    private Sop to;//结束节点


    public SopToSop(Sop from, Sop newSop, SopRelationDto sopRelationDto) {
        this.from=from;
        this.to=newSop;
        this.deviceId=sopRelationDto.getDeviceId();
        this.faultId=sopRelationDto.getFaultId();
        this.describe=sopRelationDto.getDescribe();//描述
        this.category=sopRelationDto.getCategory();


    }

}
