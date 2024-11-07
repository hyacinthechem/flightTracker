package src;
import ecs100.*;
import java.util.*;



public class ArrivalStar  {
    private String procedureName;
    private Set<Waypoint> waypoints;


    public ArrivalStar(String procedureName, Set<Waypoint> waypoints) {
        this.procedureName = procedureName;
        this.waypoints = waypoints;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public int sizeOfSid(){
        return waypoints.size();
    }

    public Set<Waypoint> getWaypoints(){
        return Collections.unmodifiableSet(waypoints);
    }

    @Override
    public String toString(){
        return procedureName + " " + waypoints;
    }

//    public int compare;




}
