package com.stars.missiontracker.service;

import com.stars.missiontracker.model.Agent;
import com.stars.missiontracker.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Optional<Agent> getAgentById(Long id) {
        return agentRepository.findById(id);
    }

    public List<Agent> getActiveAgents() {
        return agentRepository.findActiveAgentsOrderedByRank();
    }

    public Agent saveAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    public void deleteAgent(Long id) {
        agentRepository.deleteById(id);
    }

    public Optional<Agent> findByBadgeNumber(String badgeNumber) {
        return agentRepository.findByBadgeNumber(badgeNumber);
    }
}