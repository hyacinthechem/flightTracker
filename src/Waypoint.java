package src;
import ecs100.*;
import java.util.*;

public class Waypoint {
    private String waypointName;
    private  double altitude;
    private int speed;
    private double heading;
    private Waypoint prev;
    private Waypoint next;

    public Waypoint(String waypointName, double altitude, int speed, double heading){
        this.waypointName = waypointName;
        this.altitude = altitude;
        this.speed = speed;
        this.heading = heading;

    }

    public String getWaypointName(){
        return waypointName;
    }

    public double getAltitude(){
        return altitude;
    }

    public int getSpeed(){
        return speed;
    }

    public double getHeading(){
        return heading;
    }

    public void setPrevWaypoint(Waypoint prev){
        this.prev = prev;
    }

    public void setNextWaypoint(Waypoint next){
        this.next = next;
    }

    public Waypoint getPrevWaypoint(){
        return prev;
    }

    public Waypoint getNextWaypoint(){
        return next;
    }

    @Override

    public String toString(){
        return "Waypoint: " + waypointName + "Altitude " + altitude + "\nSpeed: " + speed + "\nHeading: " + heading;
    }












}
