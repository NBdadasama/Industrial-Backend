package org.example.dao.interfer;


import org.example.entity.obj.Device;
import org.example.entity.obj.Fault;
import org.example.entity.obj.Maintenance;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MaintenanceRepository extends Neo4jRepository<Maintenance,Long> {


    @Query("MATCH(a:Maintenance) WHERE a.maintenanceId={maintenanceId} RETURN a")
    Maintenance findByMaintenanceId(@Param(value = "maintenanceId") String maintenanceId);


    @Query("MATCH (Device)-[:DEVICETOMAINTENANCE]->(Maintenance) WHERE Device.deviceId = {deviceId} RETURN Maintenance")
    List<Maintenance> findAllByDeviceId(@Param(value = "deviceId") String deviceId);



    @Query("MATCH (Device)-[:DEVICETOMAINTENANCE]->(Maintenance) WHERE Device.deviceId = {deviceId} AND Maintenance.maintenanceId = {maintenanceId} RETURN Maintenance")
    Maintenance findByDeviceIdAndMaintenanceId(@Param(value = "deviceId") String deviceId,
                                   @Param(value = "maintenanceId") String maintenanceId);


    @Query("MATCH (Device)-[r]->(maintenance:Maintenance) WHERE Device.deviceId = {deviceId} AND maintenance.maintenanceId = {maintenanceId} DELETE r,maintenance")//删除节点信息
    void deleteByMaintenanceId(@Param(value = "deviceId") String deviceId,
                                               @Param(value = "maintenanceId") String maintenanceId);






}
