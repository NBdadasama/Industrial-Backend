package org.example.dao.mapper.industral.relation;


import org.example.entity.industral.relation.IndDeviceToIndInspection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional

public interface IndDeviceToIndInspectionRepository extends Neo4jRepository<IndDeviceToIndInspection,Long> {
}
