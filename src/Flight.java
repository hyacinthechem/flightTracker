package src;
import ecs100.*;

import java.awt.*;
import java.util.*;

public class Flight {
    private String flightDetail;
    private String aircraftType;
    private String airline;
    private String runway;
    private boolean internationalFlight;
    private boolean takeoff;
    private boolean landed;


    public Flight(String flightDetail, String aircraftType, String airline,boolean internationalFlight){
        this.flightDetail = flightDetail;
        this.aircraftType = aircraftType;
        this.airline = airline;
        this.internationalFlight = internationalFlight;

    }

    public String getFlightDetail(){
        return flightDetail;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public String getAirline(){
        return airline;
    }

    public boolean isInternationalFlight(){
        return internationalFlight;
    }

    public void draw(double x, double y){
        switch(airline){
            case "Air New Zealand":
                UI.setColor(Color.blue);
                UI.fillOval(x,y,x+100, y+100);
            case "Emirates":
                UI.setColor(new Color(199,151,61));
                UI.fillOval(x,y,x+100, y+100);
            case "Singapore Airlines":
                UI.setColor(new Color(212,175,55));
                UI.fillOval(x,y,x+100, y+100);
            case "Cathay Pacific":
                UI.setColor(new Color(0,102,102));
                UI.fillOval(x,y,x+100, y+100);
            case "Qantas":
                UI.setColor(Color.red);
                UI.fillOval(x,y,x+100, y+100);
        }
    }

    public void assignRunway(String runway){
        this.runway = runway;
    }

    public void setTakeoff(boolean takeoff){
        this.takeoff = takeoff;
    }

    public void setLanded(boolean landed){
        this.landed = landed;
    }

    public boolean hasTakenOff(){
        return takeoff;
    }

    public boolean hasLanded(){
        return landed;
    }

    public String getRunway(){
        return runway;
    }

    @Override
    public String toString(){
        return "Airline: " + airline + "Aircraft" + aircraftType + "Flight Number: " + flightDetail + "\n";
    }





}
