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
        double startLat = Math.toRadians(startingLocation.getLatitude());
        double startLong = Math.toRadians(startingLocation.getLongitude());
        double targetLat = Math.toRadians(targetLocation.getLatitude());
        double targetLong = Math.toRadians(targetLocation.getLongitude());
        double diffLong = targetLong - startLong;
        double x = Math.cos(targetLat) * Math.sin(diffLong);
        double y = Math.cos(startLat) * Math.sin(targetLat)
                - Math.sin(startLat) * Math.cos(targetLat)
                * Math.cos(diffLong);
        double bearing = Math.atan2(x, y);
        double result = Math.toDegrees(bearing);
        return (int) Math.round(result);
    }

    public int getDirectionAzimuth() {
        return directionAzimuth;
    }
}
