package org.example.dao.relation;


import org.example.entity.relation.DeviceToFault;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DeviceToFaultRepository extends Neo4jRepository<DeviceToFault,Long> {


    @Query("MATCH ()-[r]->() RETURN r")
    Result findAllRelation();



    @Query("MATCH (device:Device)-[r:DEVICETOFAULT]->(fault:Fault) where device.deviceId = {deviceId} RETURN r")
    Result findByDeviceId(@Param(value = "deviceId") String deviceId);

}
