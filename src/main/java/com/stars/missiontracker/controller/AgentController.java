package com.stars.missiontracker.controller;

import com.stars.missiontracker.model.Agent;
import com.stars.missiontracker.service.AgentService;
import com.stars.missiontracker.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private MissionService missionService;

    @GetMapping
    public String listAgents(Model model) {
        model.addAttribute("agents", agentService.getAllAgents());
        return "agents/list";
    }

    @GetMapping("/{id}")
    public String agentDetail(@PathVariable Long id, Model model) {
        Optional<Agent> agent = agentService.getAgentById(id);
        if (agent.isPresent()) {
            model.addAttribute("agent", agent.get());
            model.addAttribute("missions", missionService.getMissionsByAgent(id));
            return "agents/detail";
        }
        return "redirect:/agents";
    }
}