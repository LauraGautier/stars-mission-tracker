package com.stars.missiontracker.controller;

import com.stars.missiontracker.model.Mission;
import com.stars.missiontracker.model.MissionStatus;
import com.stars.missiontracker.service.AgentService;
import com.stars.missiontracker.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/missions")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @Autowired
    private AgentService agentService;

    // READ - Liste des missions
    @GetMapping
    public String listMissions(Model model) {
        model.addAttribute("missions", missionService.getAllMissions());
        return "missions/list";
    }

    // READ - Détail d'une mission
    @GetMapping("/{id}")
    public String missionDetail(@PathVariable Long id, Model model) {
        Optional<Mission> mission = missionService.getMissionById(id);
        if (mission.isPresent()) {
            model.addAttribute("mission", mission.get());
            return "missions/detail";
        }
        return "redirect:/missions";
    }

    // CREATE - Formulaire de création
    @GetMapping("/new")
    public String newMissionForm(Model model) {
        model.addAttribute("mission", new Mission());
        model.addAttribute("agents", agentService.getActiveAgents());
        model.addAttribute("priorities", new String[]{"LOW", "MEDIUM", "HIGH", "CRITICAL"});
        model.addAttribute("statuses", MissionStatus.values());
        return "missions/form";
    }

    // CREATE - Traitement du formulaire
    @PostMapping
    public String createMission(@ModelAttribute Mission mission,
                                @RequestParam(required = false) Long assignedAgentId,
                                RedirectAttributes redirectAttributes) {
        try {
            // Assigner l'agent si spécifié
            if (assignedAgentId != null) {
                agentService.getAgentById(assignedAgentId).ifPresent(mission::setAssignedAgent);
            }

            // Générer un code mission automatique si pas spécifié
            if (mission.getCodeName() == null || mission.getCodeName().isEmpty()) {
                long count = missionService.getAllMissions().size() + 1;
                mission.setCodeName(String.format("MISSION-%03d", count));
            }

            // Date de création
            mission.setCreatedAt(LocalDateTime.now());

            Mission savedMission = missionService.saveMission(mission);
            redirectAttributes.addFlashAttribute("success",
                    "Mission " + savedMission.getCodeName() + " créée avec succès !");
            return "redirect:/missions/" + savedMission.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la création de la mission : " + e.getMessage());
            return "redirect:/missions/new";
        }
    }

    // UPDATE - Formulaire de modification
    @GetMapping("/{id}/edit")
    public String editMissionForm(@PathVariable Long id, Model model) {
        Optional<Mission> mission = missionService.getMissionById(id);
        if (mission.isPresent()) {
            model.addAttribute("mission", mission.get());
            model.addAttribute("agents", agentService.getActiveAgents());
            model.addAttribute("priorities", new String[]{"LOW", "MEDIUM", "HIGH", "CRITICAL"});
            model.addAttribute("statuses", MissionStatus.values());
            return "missions/form";
        }
        return "redirect:/missions";
    }

    // UPDATE - Traitement de la modification
    @PostMapping("/{id}")
    public String updateMission(@PathVariable Long id, @ModelAttribute Mission mission,
                                @RequestParam(required = false) Long assignedAgentId,
                                RedirectAttributes redirectAttributes) {
        try {
            mission.setId(id);

            // Assigner l'agent
            if (assignedAgentId != null) {
                agentService.getAgentById(assignedAgentId).ifPresent(mission::setAssignedAgent);
            } else {
                mission.setAssignedAgent(null);
            }

            // Conserver la date de création originale
            Optional<Mission> originalMission = missionService.getMissionById(id);
            originalMission.ifPresent(original -> mission.setCreatedAt(original.getCreatedAt()));

            Mission savedMission = missionService.saveMission(mission);
            redirectAttributes.addFlashAttribute("success",
                    "Mission " + savedMission.getCodeName() + " modifiée avec succès !");
            return "redirect:/missions/" + savedMission.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la modification : " + e.getMessage());
            return "redirect:/missions/" + id + "/edit";
        }
    }

    // DELETE - Suppression d'une mission
    @PostMapping("/{id}/delete")
    public String deleteMission(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Mission> mission = missionService.getMissionById(id);
            if (mission.isPresent()) {
                missionService.deleteMission(id);
                redirectAttributes.addFlashAttribute("success",
                        "Mission " + mission.get().getCodeName() + " supprimée avec succès !");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/missions";
    }

    // ACTION - Démarrer une mission
    @PostMapping("/{id}/start")
    public String startMission(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Mission> mission = missionService.getMissionById(id);
            if (mission.isPresent()) {
                Mission m = mission.get();
                m.setStatus(MissionStatus.IN_PROGRESS);
                m.setStartDate(LocalDateTime.now());
                missionService.saveMission(m);
                redirectAttributes.addFlashAttribute("success",
                        "Mission " + m.getCodeName() + " démarrée !");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors du démarrage : " + e.getMessage());
        }
        return "redirect:/missions/" + id;
    }

    // ACTION - Terminer une mission
    @PostMapping("/{id}/complete")
    public String completeMission(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Mission> mission = missionService.getMissionById(id);
            if (mission.isPresent()) {
                Mission m = mission.get();
                m.setStatus(MissionStatus.COMPLETED);
                m.setEndDate(LocalDateTime.now());
                missionService.saveMission(m);
                redirectAttributes.addFlashAttribute("success",
                        "Mission " + m.getCodeName() + " terminée avec succès !");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la finalisation : " + e.getMessage());
        }
        return "redirect:/missions/" + id;
    }
}