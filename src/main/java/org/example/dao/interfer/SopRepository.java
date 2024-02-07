package org.example.dao.interfer;


import org.example.controller.dto.SopDto;
import org.example.entity.obj.Sop;
import org.example.entity.relation.FaultToSop;
import org.example.entity.relation.SopToSop;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SopRepository extends Neo4jRepository<Sop,Long> {//保存节点

    @Query("MATCH(a:SOP) WHERE a.sopId={sopId} RETURN a")//根据sopId查找节点
    Sop findBySopID(@Param(value = "sopId") String sopId);


    @Query("MATCH(a:SOP) WHERE a.deviceId={deviceId} AND a.faultId={faultId} RETURN a")//根据sopId查找节点
    List<Sop> findAllSopByFaultID(@Param(value = "deviceId") String deviceId,
                                     @Param(value = "faultId") String faultId);
    @Query("MATCH (sop:SOP)-[r]->(a:SOP) WHERE a.deviceId={deviceId} AND a.faultId={faultId} RETURN r")//根据sopId查找节点
    List<SopToSop> findAllSopToSopRelation(String deviceId, String faultId);

    @Query("MATCH (fault:Fault)-[r]->(a:SOP) WHERE a.deviceId={deviceId} AND a.faultId={faultId} RETURN r")//根据sopId查找节点
    FaultToSop findFaultToSopRelation(String deviceId, String faultId);


    @Query(" MATCH (sop:SOP) return sop")
    List<Sop> findAllSop();

    @Query(" MATCH (node:SOP) WHERE node.sopId={sopId} DETACH DELETE node")
    void deleteBySopId(String sopId);


    @Query(" MATCH (node:SOP) WHERE node.deviceId={deviceId} AND node.faultId={faultId} DETACH DELETE node")
    void deleteByDeviceIdAndFaultId(String deviceId,String faultId);
}
