package org.example.dao.mapper.industral.relation;


import org.example.entity.industral.relation.IndDeviceToIndDevice;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IndDeviceToIndDeviceRepository extends Neo4jRepository<IndDeviceToIndDevice,Long> {



    @Query("MATCH (n)-[r:INDDEVICETOINDDEVICE]->(m) where r.deviceId = {deviceId} RETURN r")
    Result findByDeviceId(@Param(value = "deviceId") Long deviceId);




}
