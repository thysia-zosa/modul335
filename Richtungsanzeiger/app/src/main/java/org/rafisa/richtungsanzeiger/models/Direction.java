package org.rafisa.richtungsanzeiger.models;

public class Direction {
    private final Location startingLocation;
    private final Location targetLocation;
    private int directionAzimuth;

    public Direction(Location startingLocation, Location targetLocation) {
        this.startingLocation = startingLocation;
        this.targetLocation = targetLocation;
        this.directionAzimuth = calculateDirectionAzimuth();
    }

    /**
     * Mit herzlichem Dank an https://www.igismap.com/formula-to-find-bearing-or-heading-angle-between-two-points-latitude-longitude/
     *
     * R = Radius der Erde
     * L = Länge
     * θ = Breite
     * β = Richtung
     *
     * X = cos θb * sin ∆L
     * Y = cos θa * sin θb – sin θa * cos θb * cos ∆L
     * β = atan2(X,Y)
     *
     * @return Richtung von startingLocation zu targetLocation in Grad
     */
    private int calculateDirectionAzimuth() {
        double x = Math.cos(targetLocation.getLatitude()) * Math.sin(targetLocation.getLongitude()
                - startingLocation.getLongitude());
        double y = Math.cos(startingLocation.getLatitude()) * Math.sin(targetLocation.getLatitude())
                - Math.sin(startingLocation.getLatitude()) * Math.cos(startingLocation.getLatitude())
                * Math.cos(targetLocation.getLongitude() - startingLocation.getLongitude());
        double bearing = Math.atan2(x, y);
        return (int) Math.round(180 * bearing / Math.PI);
    }

    public int getDirectionAzimuth() {
        return directionAzimuth;
    }
}
