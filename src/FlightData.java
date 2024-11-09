package src;
import ecs100.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class FlightData {
    private List<Object> allProcedures = new ArrayList<>();
    private Set<Waypoint> waypoints;
    private List<DepartureSid> sidProcedures;
    private List<ArrivalStar> starProcedures;
    private List<Flight> allFlights = new ArrayList<>();
    private String[] runways = {"RWY05R, RWY23L"};

    public FlightData(){
        this.allProcedures = new ArrayList<>();
        this.waypoints = new HashSet<>();
        this.sidProcedures = new ArrayList<>();
        this.starProcedures = new ArrayList<>();
    }
    public void loaders() {
        List<String> procedureFiles = Arrays.asList("src/data/RWY05R - BATOS3Q", "src/data/RWY23L - LENGU4P","src/data/RWY05R - BASIV9B", "src/data/RWY23L - LUNBI1N");
        loadSidStarData(procedureFiles);
        loadFlightData();
    }


    public void loadSidStarData(List<String> procedureFiles) {
        for (String filePath : procedureFiles) {
            try (Scanner sc = new Scanner(Path.of(filePath))) {
                waypoints = new HashSet<>();
                String procedureName = sc.next();
                String runway = sc.next();
                String procedureType = sc.next();

                while(sc.hasNextLine()){
                    String waypointName = sc.next();
                    double altitude = sc.nextDouble();
                    int speed = sc.nextInt();
                    double heading = sc.nextDouble();
                    Waypoint wp = new Waypoint(waypointName, altitude, speed, heading);
                    waypoints.add(wp);
                }
                if(procedureType.equals("SID")){
                    DepartureSid ds = new DepartureSid(procedureName,waypoints,runway);
                    allProcedures.add(ds);
                }else{
                    ArrivalStar ar = new ArrivalStar(procedureName, waypoints, runway);
                    allProcedures.add(ar);
                }

            } catch (IOException e) {
                UI.println("File Failure for " + filePath + " : " + e);
            }
        }
    }

    public void loadFlightData(){
        String[] emirates = {"EK12 Dubai"};
        String[] airNewZealand = {"NZ148 Brisbane","NZ99 Tokyo","NZ81 Hong Kong","NZ401 Wellington","NZ515 Christchurch","NZ671 Dunedin"};
        String[] qantas = {"QF154 Melbourne","QF144 Sydney","QF149 Sydney","QF157 Melbourne"};
        String[] singaporeAirlines = {"SQ286 Singapore","SQ282 Singapore","SQ285 Singapore"};
        String[] cathayPacific = {"CX198 Hong Kong","CX7401 Hong Kong", "CX113 Hong Kong"};

        Flight f1 = new Flight(emirates[0],"Airbus A380","Emirates",true );
        for(int i=0; i<airNewZealand.length; i++){
            if(i==1 || i== 2){
                String aircraftType = "Boeing 787-9 Dreamliner";
                Flight flight = new Flight(airNewZealand[i],aircraftType,"Air New Zealand",true);
                allFlights.add(flight);
            }else if(i==0){
                Flight f = new Flight(airNewZealand[i],"Airbus A321neo","Air New Zealand",true );
                allFlights.add(f);
            }else{
                Flight f = new Flight(airNewZealand[i],"Airbus A321neo","Air New Zealand",false );
                allFlights.add(f);
            }
        }

        for(int i=0; i<qantas.length; i++){
            Flight f = new Flight(qantas[i],"Boeing 737-300","Qantas",true );
            allFlights.add(f);
        }

        for(int i=0; i<singaporeAirlines.length; i++){
            Flight f = new Flight(singaporeAirlines[i],"Airbus A350-900", "Singapore Airlines",true);
            allFlights.add(f);
        }

        for(int i=0; i<cathayPacific.length; i++){
            Flight f = new Flight(cathayPacific[i], "Boeing 777-300ER","Cathay Pacific",true);
            allFlights.add(f);
        }
    }

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    public Set<Waypoint> getAllWaypoints() {
        return Collections.unmodifiableSet(waypoints);
    }

    public List<ArrivalStar> getStarProcedures() {
        return Collections.unmodifiableList(starProcedures);
    }

    public List<DepartureSid> getSidProcedures() {
        return Collections.unmodifiableList(sidProcedures);
    }
    public List<ArrivalStar> getArrivalProcedures() {
        return Collections.unmodifiableList(starProcedures);
    }

    @Override
    public String toString(){
        return "All Flights: " + allFlights;
    }
}
