package com.stars.missiontracker.controller;

import com.stars.missiontracker.service.AgentService;
import com.stars.missiontracker.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private MissionService missionService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalAgents", agentService.getAllAgents().size());
        model.addAttribute("activeAgents", agentService.getActiveAgents().size());
        model.addAttribute("totalMissions", missionService.getAllMissions().size());
        model.addAttribute("activeMissions", missionService.getActiveMissions().size());

        return "index";
    }
}