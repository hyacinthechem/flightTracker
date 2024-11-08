package src;
import ecs100.*;
import java.util.*;

public class DepartureSid implements Comparable<DepartureSid> {
    private String procedureName;
    private Set<Waypoint> waypoints;
    private String runway;

    public DepartureSid(String procedureName, Set<Waypoint> waypoints, String runway) {
        this.procedureName = procedureName;
        this.waypoints = waypoints;
        this.runway = runway;
    }

    public String getSIDProcedureName() {
        return procedureName;
    }

    public int sizeOfStar() {
        return waypoints.size();
    }

    public String getRunway() {
        return runway;
    }

    public Set<Waypoint> getWaypoints() {
        return Collections.unmodifiableSet(waypoints);
    }


    @Override
    public String toString() {
        return procedureName + " " + waypoints;
    }


    @Override
    public int compareTo(DepartureSid o) {
        FlightPathSimulation fps = new FlightPathSimulation();
        if (this.runway == fps.priorityRunway && o.runway != fps.priorityRunway) {
            return -1;
        } else if (this.runway != fps.priorityRunway && o.runway == fps.priorityRunway) {
            return 1;
        } else {
            return 0;
        }
    }
}
