package org.example.dao.mapper.industral.neo4j;

import org.example.entity.industral.neo4j.IndInspection;
import org.example.entity.industral.neo4j.IndMaintenance;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndMaintenanceRepository extends Neo4jRepository<IndMaintenance,Long> {

    @Query("MATCH(a:IndMaintenance) where a.deviceId={deviceId} RETURN a")
    List<IndMaintenance> findByDeviceId(@Param(value = "deviceId") Long deviceId);



    @Query("MATCH(a:IndMaintenance) RETURN a SKIP {pageSize}*{pageNumber} LIMIT {pageSize} ")
    List<IndMaintenance> findByPage(@Param(value = "pageNumber") int pageNumber,
                                    @Param(value = "pageSize")int pageSize);

    @Query("MATCH (n:IndMaintenance) WHERE id(n) = {nodeId} RETURN n")//根据名字模糊查询设备
    IndMaintenance findByNodeId(Long nodeId);

}
