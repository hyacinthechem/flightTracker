package src;
import ecs100.*;

import javax.swing.plaf.PanelUI;
import java.util.*;

public class ArrivalStar {
    private String procedureName;
    private Set<Waypoint> waypoints;
    private String runway;
    private Comparator<Flight> flightComparator;


    public ArrivalStar(String procedureName, Set<Waypoint> waypoints,String runway) {
        this.procedureName = procedureName;
        this.waypoints = waypoints;
        this.runway = runway;
    }

    public String getSTARProcedureName() {
        return procedureName;
    }

    public int sizeOfStar(){
        return waypoints.size();
    }

    public Set<Waypoint> getWaypoints(){
        return Collections.unmodifiableSet(waypoints);
    }

    public String getRunway() {
        return runway;
    }


    @Override
    public String toString(){
        return procedureName + " " + waypoints;
    }

    public Comparator<Flight> getFlightComparator() {
        flightComparator = new Comparator<Flight>() {
            public int compare(Flight o1, Flight o2) {
                if(o1.isInternationalFlight() && o2.isInternationalFlight()){
                    return 0;
                }else if(o1.isInternationalFlight() && !o2.isInternationalFlight()){
                    return -1;
                }else if(o2.isInternationalFlight() && !o1.isInternationalFlight()){
                    return 1;
                }else{
                    return 0;
                }
            }
        };

        return flightComparator;
    }


    /*
    @Override
    public int compareTo(Flight o) {
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

     */





}
