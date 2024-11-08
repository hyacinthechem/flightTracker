package src;
import ecs100.*;

import java.awt.*;
import java.util.*;

public class Flight {
    private String flightNumber;
    private String aircraftType;
    private String airline;
    private boolean internationalFlight;


    public Flight(String flightNumber, String aircraftType, String airline,boolean internationalFlight){
        this.flightNumber = flightNumber;
        this.aircraftType = aircraftType;
        this.airline = airline;
        this.internationalFlight = internationalFlight;

    }

    public String getFlightNumber(){
        return flightNumber;
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

    @Override
    public String toString(){
        return "Airline: " + airline + "Aircraft" + aircraftType + "Flight Number: " + flightNumber + "\n";
    }





}
