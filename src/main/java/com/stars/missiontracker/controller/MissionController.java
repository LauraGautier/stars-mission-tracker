package com.stars.missiontracker.controller;

import com.stars.missiontracker.model.Mission;
import com.stars.missiontracker.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/missions")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @GetMapping
    public String listMissions(Model model) {
        model.addAttribute("missions", missionService.getAllMissions());
        return "missions/list";
    }

    @GetMapping("/{id}")
    public String missionDetail(@PathVariable Long id, Model model) {
        Optional<Mission> mission = missionService.getMissionById(id);
        if (mission.isPresent()) {
            model.addAttribute("mission", mission.get());
            return "missions/detail";
        }
        return "redirect:/missions";
    }
}