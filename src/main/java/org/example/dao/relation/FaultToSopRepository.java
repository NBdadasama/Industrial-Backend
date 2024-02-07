package org.example.dao.relation;


import org.example.entity.relation.FaultToSop;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface FaultToSopRepository extends Neo4jRepository<FaultToSop,Long> {


    @Query("MATCH (fault:Fault)-[r]->(sop:SOP) RETURN r")
    List<FaultToSop> findAllRelation();
}
