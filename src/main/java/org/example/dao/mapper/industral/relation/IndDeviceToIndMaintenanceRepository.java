package org.example.dao.mapper.industral.relation;


import org.example.entity.relation.DeviceToMaintenance;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface IndDeviceToIndMaintenanceRepository extends Neo4jRepository<DeviceToMaintenance,Long> {
}
