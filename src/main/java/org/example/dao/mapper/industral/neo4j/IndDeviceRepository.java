package org.example.dao.mapper.industral.neo4j;


import org.example.entity.industral.neo4j.IndDevice;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional

public interface IndDeviceRepository extends Neo4jRepository<IndDevice,Long> {


    @Query("MATCH(a:IndDevice) WHERE a.bomId={bomId} RETURN a")
    IndDevice findByBomId(@Param(value = "bomId") Long bomId);

    @Query("MATCH(a:IndDevice) where a.parentId=-1 RETURN a LIMIT {limit}")
    List<IndDevice> findByLimit(@Param(value = "limit") int limit);


    @Query("MATCH(a:IndDevice) where a.deviceId={deviceId} RETURN a")
    List<IndDevice> findByDeviceId(@Param(value = "deviceId") Long deviceId);

    @Query("MATCH(a:IndDevice) RETURN a SKIP {pageSize}*{pageNumber} LIMIT {pageSize} ")
    List<IndDevice> findByPage(@Param(value = "pageNumber") int pageNumber,
                               @Param(value = "pageSize")int pageSize);


    @Query("MATCH(a:IndDevice) where a.parentId=-1  RETURN a SKIP {pageSize}*{pageNumber} LIMIT {pageSize} ")
    List<IndDevice> findParentIndDeviceByPage(@Param(value = "pageNumber") int pageNumber,
                               @Param(value = "pageSize")int pageSize);


}
