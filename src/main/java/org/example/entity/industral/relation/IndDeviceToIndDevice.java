package org.example.entity.industral.relation;


import lombok.Data;
import org.example.entity.industral.neo4j.IndDevice;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity
@Data
public class IndDeviceToIndDevice {


    @Id
    @GeneratedValue
    private Long nodeId;


    @Property
    private Long deviceId;//故障id，自己编码对故障进行决定
    @StartNode
    private IndDevice from;//开始节点

    @EndNode
    private IndDevice to;//结束节点


    public IndDeviceToIndDevice(IndDevice from, IndDevice to) {
        this.from=from;
        this.to=to;
        this.deviceId=from.getDeviceId();

    }

}
