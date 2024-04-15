package org.example.dao.mapper.industral.neo4j;


import org.example.entity.industral.neo4j.IndDevice;
import org.example.entity.industral.neo4j.IndInspection;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndInspectionRepository extends Neo4jRepository<IndInspection,Long> {




    @Query("MATCH(a:IndInspection) where a.deviceId={deviceId} RETURN a")
    List<IndInspection> findByDeviceId(@Param(value = "deviceId") Long deviceId);


    @Query("MATCH(a:IndInspection) RETURN a SKIP {pageSize}*{pageNumber} LIMIT {pageSize} ")
    List<IndInspection> findByPage(@Param(value = "pageNumber") int pageNumber,
                                   @Param(value = "pageSize")int pageSize);


    @Query("MATCH (n:IndInspection) WHERE id(n) = {nodeId} RETURN n")//根据名字模糊查询设备
    IndInspection findByNodeId(Long nodeId);

}
