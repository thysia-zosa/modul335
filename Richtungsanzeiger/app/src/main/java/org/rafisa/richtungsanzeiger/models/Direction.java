package org.rafisa.richtungsanzeiger.models;

public class Direction {
    private final Location startingLocation;
    private final Location targetLocation;
    private double directionAzimuth;

    public Direction(Location startingLocation, Location targetLocation) {
        this.startingLocation = startingLocation;
        this.targetLocation = targetLocation;
        this.directionAzimuth = calculateDirectionAzimuth();
    }

    private double calculateDirectionAzimuth() {
        // TODO: funktion implementieren
        return 45;
    }

    public double getDirectionAzimuth() {
        return directionAzimuth;
    }
}
