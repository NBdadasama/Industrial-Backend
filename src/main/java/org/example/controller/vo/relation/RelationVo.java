package org.example.controller.vo.relation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.relation.DeviceToFault;
import org.example.entity.relation.FaultToSop;
import org.example.entity.relation.SopToSop;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.QueryResult;

@Data
@QueryResult
public class RelationVo {

    private Long id;

    private Long starNode;

    private Long endNode;

    private String type;

    public RelationVo(DeviceToFault deviceToFault) {
        this.id=deviceToFault.getNodeId();
        this.starNode=deviceToFault.getFrom().getNodeId();
        this.endNode=deviceToFault.getTo().getNodeId();
        this.type=("设备->故障");


    }

    public RelationVo(FaultToSop faultToSop) {
        this.id=faultToSop.getNodeId();
        this.starNode=faultToSop.getFrom().getNodeId();
        this.endNode=faultToSop.getTo().getNodeId();
        this.type=("故障->Sop");
    }


    public RelationVo(SopToSop sopToSop) {
        this.id=sopToSop.getNodeId();
        this.starNode=sopToSop.getFrom().getNodeId();
        this.endNode=sopToSop.getTo().getNodeId();
        this.type=("Sop->Sop");

    }
}
