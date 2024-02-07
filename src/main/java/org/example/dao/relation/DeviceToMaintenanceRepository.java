package org.example.dao.relation;


import org.example.entity.relation.DeviceToMaintenance;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DeviceToMaintenanceRepository extends Neo4jRepository<DeviceToMaintenance,Long> {




}
