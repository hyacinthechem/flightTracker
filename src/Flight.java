package src;

public class Flight {
    private String flightNumber;
    private String aircraftType;
    private String airline;


    public Flight(String flightNumber, String aircraftType, String airline){
        this.flightNumber = flightNumber;
        this.aircraftType = aircraftType;
        this.airline = airline;

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



}
