package com.stars.missiontracker.controller;

import com.stars.missiontracker.model.Agent;
import com.stars.missiontracker.service.AgentService;
import com.stars.missiontracker.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private MissionService missionService;

    // READ - Liste des agents
    @GetMapping
    public String listAgents(Model model) {
        model.addAttribute("agents", agentService.getAllAgents());
        return "agents/list";
    }

    // READ - Détail d'un agent
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

    // CREATE - Formulaire de création
    @GetMapping("/new")
    public String newAgentForm(Model model) {
        model.addAttribute("agent", new Agent());
        model.addAttribute("ranks", new String[]{"Medic", "Officer", "Sergeant", "Lieutenant", "Captain"});
        model.addAttribute("specializations", new String[]{
                "Combat/Armes lourdes", "Désamorçage/Infiltration", "Armes à feu/Support",
                "Commandement/Tactique", "Médical/Chimie", "Reconnaissance/Sniper", "Communications/Tech"
        });
        return "agents/form";
    }

    // CREATE - Traitement du formulaire
    @PostMapping
    public String createAgent(@ModelAttribute Agent agent, RedirectAttributes redirectAttributes) {
        try {
            // Générer un badge number automatique
            if (agent.getBadgeNumber() == null || agent.getBadgeNumber().isEmpty()) {
                long count = agentService.getAllAgents().size() + 1;
                agent.setBadgeNumber(String.format("STARS-%03d", count));
            }

            // Date d'aujourd'hui si pas spécifiée
            if (agent.getJoinDate() == null) {
                agent.setJoinDate(LocalDate.now());
            }

            Agent savedAgent = agentService.saveAgent(agent);
            redirectAttributes.addFlashAttribute("success",
                    "Agent " + savedAgent.getFullName() + " créé avec succès !");
            return "redirect:/agents/" + savedAgent.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la création de l'agent : " + e.getMessage());
            return "redirect:/agents/new";
        }
    }

    // UPDATE - Formulaire de modification
    @GetMapping("/{id}/edit")
    public String editAgentForm(@PathVariable Long id, Model model) {
        Optional<Agent> agent = agentService.getAgentById(id);
        if (agent.isPresent()) {
            model.addAttribute("agent", agent.get());
            model.addAttribute("ranks", new String[]{"Medic", "Officer", "Sergeant", "Lieutenant", "Captain"});
            model.addAttribute("specializations", new String[]{
                    "Combat/Armes lourdes", "Désamorçage/Infiltration", "Armes à feu/Support",
                    "Commandement/Tactique", "Médical/Chimie", "Reconnaissance/Sniper", "Communications/Tech"
            });
            model.addAttribute("statuses", new String[]{"ACTIVE", "INACTIVE", "SUSPICIOUS", "MIA"});
            return "agents/form";
        }
        return "redirect:/agents";
    }

    // UPDATE - Traitement de la modification
    @PostMapping("/{id}")
    public String updateAgent(@PathVariable Long id, @ModelAttribute Agent agent,
                              RedirectAttributes redirectAttributes) {
        try {
            agent.setId(id);
            Agent savedAgent = agentService.saveAgent(agent);
            redirectAttributes.addFlashAttribute("success",
                    "Agent " + savedAgent.getFullName() + " modifié avec succès !");
            return "redirect:/agents/" + savedAgent.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la modification : " + e.getMessage());
            return "redirect:/agents/" + id + "/edit";
        }
    }

    // DELETE - Suppression d'un agent
    @PostMapping("/{id}/delete")
    public String deleteAgent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Agent> agent = agentService.getAgentById(id);
            if (agent.isPresent()) {
                // Vérifier s'il a des missions assignées
                if (!missionService.getMissionsByAgent(id).isEmpty()) {
                    redirectAttributes.addFlashAttribute("error",
                            "Impossible de supprimer l'agent " + agent.get().getFullName() +
                                    " : il a des missions assignées.");
                    return "redirect:/agents/" + id;
                }

                agentService.deleteAgent(id);
                redirectAttributes.addFlashAttribute("success",
                        "Agent " + agent.get().getFullName() + " supprimé avec succès !");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/agents";
    }
}