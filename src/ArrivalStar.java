package src;
import ecs100.*;
import java.util.*;

public class ArrivalStar implements Comparable<ArrivalStar>{
    private String procedureName;
    private Set<Waypoint> waypoints;
    private boolean internationalFlight;


    public ArrivalStar(String procedureName, Set<Waypoint> waypoints,boolean internationalFlight) {
        this.procedureName = procedureName;
        this.waypoints = waypoints;
        this.internationalFlight = internationalFlight;
    }

    public String getSTARProcedureName() {
        return procedureName;
    }

    public int sizeOfSid(){
        return waypoints.size();
    }

    public Set<Waypoint> getWaypoints(){
        return Collections.unmodifiableSet(waypoints);
    }

    public boolean isInternationalFlight() {
        return internationalFlight;
    }

    @Override
    public String toString(){
        return procedureName + " " + waypoints;
    }

    @Override
    public int compareTo(ArrivalStar o) {
        // Prioritize international flights first
        if (this.internationalFlight && !o.internationalFlight) {
            return -1; // This flight is international, so it comes first
        }
        if (!this.internationalFlight && o.internationalFlight) {
            return 1; // This flight is domestic, so it comes after international
        }

        // If both are international or both are domestic, compare by the number of waypoints
        return Integer.compare(this.waypoints.size(), o.waypoints.size());
    }





}
