package org.example.dao.relation;

import org.example.entity.relation.SopToSop;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface SopToSopRepository extends Neo4jRepository<SopToSop,Long> {


    @Query("MATCH (fault:SOP)-[r]->(sop:SOP) RETURN r")
    List<SopToSop> findAllRelation();
}
