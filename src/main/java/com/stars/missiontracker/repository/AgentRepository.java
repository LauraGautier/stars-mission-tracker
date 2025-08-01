package com.stars.missiontracker.repository;

import com.stars.missiontracker.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByBadgeNumber(String badgeNumber);
    List<Agent> findByStatus(String status);
    List<Agent> findByRank(String rank);

    @Query("SELECT a FROM Agent a WHERE a.status = 'ACTIVE' ORDER BY a.rank, a.lastName")
    List<Agent> findActiveAgentsOrderedByRank();
}