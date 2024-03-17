package org.example.dao.relation;

import org.example.entity.relation.SopToSop;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SopToSopRepository extends Neo4jRepository<SopToSop,Long> {


    @Query("MATCH (fault:SOP)-[r]->(sop:SOP) RETURN r")
    List<SopToSop> findAllRelation();

    @Query("MATCH ()-[r]->(sop:SOP) where r.deviceId={deviceId} AND r.faultId={faultId} RETURN r")
    Result findByDeviceIdAndFaultId(@Param(value = "deviceId")String deviceId,
                                   @Param(value = "faultId")String faultId);
}
