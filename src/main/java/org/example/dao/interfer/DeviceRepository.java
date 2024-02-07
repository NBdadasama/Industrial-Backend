package org.example.dao.interfer;

import org.example.controller.vo.relation.RelationVo;
import org.example.entity.obj.Device;
import org.example.entity.obj.Fault;
import org.neo4j.ogm.model.Result;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DeviceRepository extends Neo4jRepository<Device,Long> {


    @Query("MATCH (n) RETURN n")
    Result getAllNode();

    @Query("MATCH ()-[r]->() RETURN r")
    Result getAllRelation();


    @Query("MATCH ()-[r]->() RETURN r")
    List<RelationVo> getAllRelationVo();

    @Query("MATCH(a:Device) WHERE a.deviceId={deviceId} RETURN a")
    Device findByDeviceId(@Param(value = "deviceId") String deviceId);
    @Query(" MATCH (device:Device)-[r]-(to) WHERE device.deviceId={deviceId} DELETE device,r,to")
    void deleteRelatedByDeviceId(@Param(value = "deviceId") String deviceId);//根据deviceId删除节点,删除全部信息
    @Query(" MATCH (device:Device) WHERE device.deviceId={deviceId} DELETE device")
    void deleteDeviceByDeviceId(@Param(value = "deviceId") String deviceId);//根据deviceId删除节点,删除全部信息
    @Query("MATCH (fromNode:Device)-[:FAULTCODE]->(toNode:Fault)WHERE fromNode.DeviceId = {DeviceId} RETURN toNode")
    List<Fault> findFaultByDeviceId(@Param(value = "DeviceId") String deviceId);//根据deviceId查找所有错误


    @Query("MATCH (fromNode:Device)-[r:FAULTCODE]->(toNode:Fault)WHERE fromNode.DeviceId = {DeviceId} AND r.faultId={faultId} RETURN toNode")
    Fault findOneFaultByDeviceId(@Param(value = "DeviceId") String deviceId,@Param(value = "faultId") String faultId);//根据deviceId和faultId查找
    @Query(" MATCH (device:Device) return device order by device.createTime desc skip {pageId} limit{number}")
    List<Device> findByPage(@Param(value = "pageId") int pageId,@Param(value = "number") int number);//前面是页码，后面是分页个数
    @Query(" MATCH (device:Device) return device")
    List<Device> findAllDevice();
}
