package com.stars.missiontracker.model;

public enum MissionStatus {
    PENDING("En attente"),
    IN_PROGRESS("En cours"),
    COMPLETED("Terminée"),
    FAILED("Échec"),
    CANCELLED("Annulée");

    private final String displayName;

    MissionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}