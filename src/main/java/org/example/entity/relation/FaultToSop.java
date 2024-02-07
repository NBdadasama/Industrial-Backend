package org.example.entity.relation;


import lombok.Data;
import org.example.controller.dto.SopRelationDto;
import org.example.entity.obj.Fault;
import org.example.entity.obj.Sop;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity
@Data
public class FaultToSop {



    @Id
    @GeneratedValue
    private Long nodeId;



    @Property
    private String deviceId;//故障id，自己编码对故障进行决定

    @Property
    private String faultId;//故障id，自己编码对故障进行决定

    @Property
    private String describe;//描述
    @StartNode
    private Fault from;//开始节点

    @EndNode
    private Sop to;//结束节点


    public FaultToSop(Fault fault, Sop sop, SopRelationDto sopRelationDto) {//节点
        this.from=fault;
        this.to=sop;
        this.describe=sopRelationDto.getDescribe();
        this.deviceId=sopRelationDto.getDeviceId();
        this.faultId=sopRelationDto.getFaultId();


    }
}
