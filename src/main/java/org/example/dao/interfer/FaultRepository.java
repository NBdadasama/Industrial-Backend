package org.example.dao.interfer;

import org.example.entity.obj.Fault;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface FaultRepository extends Neo4jRepository<Fault,Long> {

    @Query("MATCH(a:Fault) WHERE a.faultId={faultId} RETURN a")
    Fault findByFaultId(@Param(value = "faultId") String faultId);



    @Query("MATCH (Device)-[:DEVICETOFAULT]->(Fault) WHERE Device.deviceId = {deviceId} RETURN Fault")
    List<Fault> findAllByDeviceId(@Param(value = "deviceId") String deviceId);

    @Query("MATCH (Device)-[:DEVICETOFAULT]->(Fault) WHERE Device.deviceId = {deviceId} AND Fault.faultId = {faultId} RETURN Fault")
    Fault findByDeviceIdAndFaultId(@Param(value = "deviceId") String deviceId,
                                   @Param(value = "faultId") String faultId);

    @Query(" MATCH (device:Device)-[r]-(fault:Fault) WHERE device.deviceId={deviceId} AND fault.faultId={faultId} DETACH DELETE fault")
    void deleteByFaultId(@Param(value = "deviceId") String deviceId,
                            @Param(value = "faultId") String faultId);


    @Query(" MATCH (fault:Fault) return fault")
    List<Fault> findAllFault();
}
