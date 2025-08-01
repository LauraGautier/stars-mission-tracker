package com.stars.missiontracker.service;

import com.stars.missiontracker.model.Mission;
import com.stars.missiontracker.model.MissionStatus;
import com.stars.missiontracker.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    public List<Mission> getAllMissions() {
        return missionRepository.findAllOrderedByPriorityAndDate();
    }

    public Optional<Mission> getMissionById(Long id) {
        return missionRepository.findById(id);
    }

    public List<Mission> getMissionsByStatus(MissionStatus status) {
        return missionRepository.findByStatus(status);
    }

    public List<Mission> getMissionsByAgent(Long agentId) {
        return missionRepository.findByAssignedAgentId(agentId);
    }

    public Mission saveMission(Mission mission) {
        return missionRepository.save(mission);
    }

    public void deleteMission(Long id) {
        missionRepository.deleteById(id);
    }

    public List<Mission> getActiveMissions() {
        return missionRepository.findByStatus(MissionStatus.IN_PROGRESS);
    }
}