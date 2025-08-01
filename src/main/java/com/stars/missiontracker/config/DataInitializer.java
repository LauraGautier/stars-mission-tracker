package com.stars.missiontracker.config;

import com.stars.missiontracker.model.Agent;
import com.stars.missiontracker.model.Mission;
import com.stars.missiontracker.model.MissionStatus;
import com.stars.missiontracker.repository.AgentRepository;
import com.stars.missiontracker.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Override
    public void run(String... args) throws Exception {
        Agent chris = new Agent(
                "Chris", "Redfield", "STARS-001", "Lieutenant",
                "Combat/Armes lourdes", LocalDate.of(1996, 1, 15),
                "Ancien membre des Forces Spéciales. Expert en combat rapproché et armes lourdes. " +
                        "Vétéran de multiples incidents impliquant des armes biologiques."
        );

        Agent jill = new Agent(
                "Jill", "Valentine", "STARS-002", "Sergeant",
                "Désamorçage/Infiltration", LocalDate.of(1996, 2, 1),
                "Experte en désamorçage de bombes et infiltration. Ancienne membre du Delta Force. " +
                        "Spécialisée dans les missions furtives et la reconnaissance."
        );

        Agent barry = new Agent(
                "Barry", "Burton", "STARS-003", "Sergeant",
                "Armes à feu/Support", LocalDate.of(1995, 8, 10),
                "Vétéran de l'équipe S.T.A.R.S. Expert en armement et maintenance. " +
                        "Responsable de l'équipement et du support tactique de l'équipe."
        );

        Agent wesker = new Agent(
                "Albert", "Wesker", "STARS-004", "Captain",
                "Commandement/Tactique", LocalDate.of(1995, 3, 1),
                "Capitaine de l'équipe Alpha S.T.A.R.S. Formation militaire et scientifique. " +
                        "Responsable de la planification tactique et du commandement sur le terrain."
        );
        wesker.setStatus("SUSPICIOUS"); // Petit clin d'œil à l'histoire

        Agent rebecca = new Agent(
                "Rebecca", "Chambers", "STARS-005", "Medic",
                "Médical/Chimie", LocalDate.of(1998, 5, 1),
                "Plus jeune membre de S.T.A.R.S. Diplômée en chimie et médecine. " +
                        "Spécialisée dans les premiers secours et l'analyse des substances biologiques."
        );

        agentRepository.save(chris);
        agentRepository.save(jill);
        agentRepository.save(barry);
        agentRepository.save(wesker);
        agentRepository.save(rebecca);

        Mission mansion = new Mission(
                "MANSION-INCIDENT",
                "Investigation Manoir Spencer",
                "Investigation des meurtres bizarres dans les montagnes Arklay. " +
                        "L'équipe Bravo S.T.A.R.S. a perdu le contact. Mission de recherche et sauvetage.",
                "Montagnes Arklay - Manoir Spencer",
                "CRITICAL"
        );
        mansion.setAssignedAgent(chris);
        mansion.setStatus(MissionStatus.COMPLETED);
        mansion.setStartDate(LocalDateTime.of(1998, 7, 24, 20, 0));
        mansion.setEndDate(LocalDateTime.of(1998, 7, 25, 6, 30));
        mansion.setNotes("Mission terminée avec succès. Découverte d'expériences illégales d'Umbrella. " +
                "Manoir détruit. Plusieurs agents portés disparus.");

        Mission raccoon = new Mission(
                "RC-EVACUATION",
                "Évacuation Raccoon City",
                "Évacuation d'urgence des civils de Raccoon City suite à l'épidémie T-Virus. " +
                        "Sécurisation des zones d'extraction et assistance aux survivants.",
                "Raccoon City",
                "CRITICAL"
        );
        raccoon.setAssignedAgent(jill);
        raccoon.setStatus(MissionStatus.FAILED);
        raccoon.setStartDate(LocalDateTime.of(1998, 9, 28, 18, 0));
        raccoon.setEndDate(LocalDateTime.of(1998, 10, 1, 6, 0));
        raccoon.setNotes("Échec de la mission d'évacuation. Ville détruite par missile nucléaire. " +
                "Nombre de survivants: minimal. Agent Valentine: MIA puis retrouvée.");

        Mission forest = new Mission(
                "FOREST-PATROL",
                "Patrouille Forêt Arklay",
                "Patrouille de routine dans la forêt d'Arklay pour rechercher des signes d'activité suspecte " +
                        "suite aux rapports de cannibalisme.",
                "Forêt Arklay",
                "MEDIUM"
        );
        forest.setAssignedAgent(barry);
        forest.setStatus(MissionStatus.IN_PROGRESS);
        forest.setStartDate(LocalDateTime.now().minusDays(2));
        forest.setNotes("Patrouille en cours. Découverte de traces suspectes et de restes humains.");

        Mission umbrella = new Mission(
                "UMBRELLA-RECON",
                "Reconnaissance Laboratoire Umbrella",
                "Mission de reconnaissance dans les installations souterraines d'Umbrella " +
                        "pour collecter des preuves des expériences illégales.",
                "Laboratoire souterrain - Raccoon City",
                "HIGH"
        );
        umbrella.setAssignedAgent(rebecca);
        umbrella.setStatus(MissionStatus.PENDING);
        umbrella.setNotes("Mission en attente d'autorisation. Risque biologique élevé.");

        Mission training = new Mission(
                "TRAINING-001",
                "Entraînement Anti-BOW",
                "Session d'entraînement spécialisée pour le combat contre les armes biologiques (B.O.W.). " +
                        "Formation aux nouveaux protocoles de sécurité.",
                "Base S.T.A.R.S. - Raccoon City",
                "LOW"
        );
        training.setStatus(MissionStatus.COMPLETED);
        training.setStartDate(LocalDateTime.now().minusDays(7));
        training.setEndDate(LocalDateTime.now().minusDays(7).plusHours(8));
        training.setNotes("Entraînement terminé avec succès. Tous les agents certifiés.");

        missionRepository.save(mansion);
        missionRepository.save(raccoon);
        missionRepository.save(forest);
        missionRepository.save(umbrella);
        missionRepository.save(training);

        System.out.println("=== S.T.A.R.S. Database Initialized ===");
        System.out.println("Agents créés: " + agentRepository.count());
        System.out.println("Missions créées: " + missionRepository.count());
        System.out.println("======================================");
    }
}