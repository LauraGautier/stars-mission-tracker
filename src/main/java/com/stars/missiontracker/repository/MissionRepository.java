package com.stars.missiontracker.repository;

import com.stars.missiontracker.model.Mission;
import com.stars.missiontracker.model.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByStatus(MissionStatus status);
    List<Mission> findByAssignedAgentId(Long agentId);
    List<Mission> findByPriority(String priority);

    @Query("SELECT m FROM Mission m ORDER BY " +
            "CASE m.priority " +
            "WHEN 'CRITICAL' THEN 1 " +
            "WHEN 'HIGH' THEN 2 " +
            "WHEN 'MEDIUM' THEN 3 " +
            "WHEN 'LOW' THEN 4 " +
            "END, m.createdAt DESC")
    List<Mission> findAllOrderedByPriorityAndDate();
}