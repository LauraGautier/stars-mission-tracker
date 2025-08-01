package com.stars.missiontracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String badgeNumber;

    @Column(nullable = false)
    private String rank;

    @Column(nullable = false)
    private String specialization;

    private LocalDate joinDate;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String status = "ACTIVE";

    @OneToMany(mappedBy = "assignedAgent", fetch = FetchType.LAZY)
    private List<Mission> missions;

    // Constructeurs
    public Agent() {}

    public Agent(String firstName, String lastName, String badgeNumber,
                 String rank, String specialization, LocalDate joinDate, String bio) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.badgeNumber = badgeNumber;
        this.rank = rank;
        this.specialization = specialization;
        this.joinDate = joinDate;
        this.bio = bio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBadgeNumber() { return badgeNumber; }
    public void setBadgeNumber(String badgeNumber) { this.badgeNumber = badgeNumber; }

    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Mission> getMissions() { return missions; }
    public void setMissions(List<Mission> missions) { this.missions = missions; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}