package src;

import ecs100.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Array;
import java.util.*;
import java.util.List;

public class FlightPathSimulation extends JFrame {

    private JTextField flightNumberField;
    private JLabel imageLabel;
    private boolean sid;
    private boolean star;
    private boolean prioirty = false;
    private String currentFlight;
    private FlightData flightData = new FlightData();

    public String priorityRunway;   // Sets the priority runway ( either 05R or 23L )
    public String[] runway = {"RWY05R", "RWY23L"}; //runway options
    public static final double RUNWAY_LIMIT = 3;
    public static final double RUNWAY_TOP_LIMIT = 1;
    public static final double APPROACH_LIMIT = 4;


    private List<Flight> allFlights = flightData.getAllFlights();
    private Queue<Flight> flightQueue; //main queue where flights wait to then be put in runway queue
    private Map<String, Queue<Flight>> runwayQueue = new HashMap();
    private Map<String, Flight> flightMap = new HashMap(); //Key Value pairs mapping flightNumber String to Flight Object
    public int delay = 400;

    private void initialiseSimulation() {
        flightData.loaders();
        if (prioirty) {
            flightQueue = new PriorityQueue<>();
        } else {
            flightQueue = new ArrayDeque<>();
        }

        for (Flight flight : allFlights) {
            flightQueue.offer(flight);
            flightMap.put(flight.getFlightDetail(), flight);
        }

        for (int i = 0; i < runway.length; i++) {
            runwayQueue.put(runway[i], new ArrayDeque<>());
        }
    }

    public FlightPathSimulation() {
        setTitle("Flight Path Simulation");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel flightLabel = new JLabel("Flight Number:");
        flightNumberField = new JTextField(10);
        add(flightLabel);
        add(flightNumberField);

        //run simulation button

        JButton runSimulation = new JButton("Run Simulation");
        add(runSimulation);

        //button that sets the priority runway

        JButton priorityRunway = new JButton("Set Priority Runway between RWY05R and RWY23L");
        add(priorityRunway);
        priorityRunway.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Do you want RWY05R as priority?", "Set Prioirty Runway", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                setPriorityRunway(true);
                System.out.println("RWY05R Selected");
            } else {
                setPriorityRunway(false);
                System.out.println("RWY23L Selected");
            }
        });

        //departure process buttons

        JButton departureButton = new JButton("Departure");
        add(departureButton);
        departureButton.addActionListener(e -> {
            sid = true;
            star = false;
            if (currentFlight != null) {
                departureRoutingProcess(currentFlight);
            } else {
                UI.println("Please Select a Flight");
            }

        });

        //arrival process buttons

        JButton arrivalButton = new JButton("Arrival");
        add(arrivalButton);
        arrivalButton.addActionListener(e -> {
            sid = false;
            star = true;
            if (currentFlight != null) {
                arrivalRoutingProcess(currentFlight);
            } else {
                UI.println("Please Select a Flight");
            }
        });

        imageLabel = new JLabel();
        loadImage();
        add(imageLabel);

    }

    public void setPriorityRunway(boolean runway) {
        if (runway) {
            priorityRunway = "RWY05R";
        } else {
            priorityRunway = "RWY23L";
        }
    }

    public void setupGUI() {

        UI.addButton("Run Simulation", this::runSimulation);
        UI.addTextField("Flight Number", (String flightNum) -> {
            this.currentFlight = flightNum;
        });
        UI.addButton("Departure Procedures", () -> {
            sid = true;
            star = false;
            if (currentFlight != null) {
                departureRoutingProcess(currentFlight);
            } else {
                UI.println("Select A Flight Number");
            }
        });

        UI.addButton("Arrival Procedures", () -> {
            sid = false;
            star = true;
            if (currentFlight != null) {
                arrivalRoutingProcess(currentFlight);
            } else {
                UI.println("Select A Flight Number");
            }
        });

        UI.addSlider("Speed of Simulation",1,400,(double val) ->{val=delay;});
    }

    public void runSimulation() {
        //run the flight simulation
        int time = 0;
        while (!flightQueue.isEmpty()) { //base case ( keep simulation running when there is a flight in the queue )
            time++; //each iteration advances timer by one tick
            Flight flight = flightQueue.poll();
            if(flight.getFlightDetail().contains("Departure")){
                departureRoutingProcess(flight.getFlightDetail());
            }else{
                arrivalRoutingProcess(flight.getFlightDetail());
            }

        }

        //departingflights


        //generateDepartingFlight()  => create separate class to deal with this
        //generateSidDeparture()

        //Arrival Flights
        //generateArrivalFlight()  => create separate class to deal with this
        //generateStarDeparture()


    }

    public void departureRoutingProcess(String flightDetail) {
        Flight flight = flightMap.get(flightDetail);
        Random rand = new Random();
        flight.assignRunway(runway[rand.nextInt(1)]);
        for (Queue<Flight> runway : runwayQueue.values()) {
            if (runway.size() < RUNWAY_LIMIT && !flightQueue.isEmpty()) { //check that runway queue has space and that flightqueue isnt empty
                runway.offer(flight);
                UI.println("Flight Number: " + flight.getFlightDetail() + " Entered runway queue");
                UI.sleep(delay);
            }

            // assign a runway

            //generate a SID Departure Process
            List<DepartureSid> sidProcedures = flightData.getSidProcedures();
            for (DepartureSid sid : sidProcedures) {
                if (flight.getRunway().equals(sid.getRunway())) {
                    UI.println("Flight Number: " + flight.getFlightDetail() + "Assigned SID: " + sid.getSIDProcedureName());
                    UI.sleep(delay);
                }
            }
            flight.setTakeoff(true);
            runway.poll();
        }

    }

    public void arrivalRoutingProcess(String flightDetail) {
        Flight flight = flightMap.get(flightDetail);
        Random rand = new Random();
        flight.assignRunway(runway[rand.nextInt(1)]);
        for (Queue<Flight> runway : runwayQueue.values()) {
            if (runway.size() < APPROACH_LIMIT && runway.size() < RUNWAY_TOP_LIMIT && !flightQueue.isEmpty()) {
                runway.offer(flight);
                UI.println("Flight Number: " + flight.getFlightDetail() + " Entered approach for Runway:  " + flight.getRunway());
                UI.sleep(delay);
            }
            runway.poll();
        }
        List<ArrivalStar> arrivalProcedures = flightData.getArrivalProcedures();

        for (ArrivalStar STAR : arrivalProcedures) {
            if (flight.getRunway().equals(STAR.getRunway())) {
                UI.println("Flight Number: " + flight.getFlightDetail() + " Assigned STAR " + STAR.getSTARProcedureName());
                UI.sleep(delay);
                UI.println("Flight Number: " + flight.getFlightDetail() + "Cleared to Land Runway  " + STAR.getRunway());
                UI.sleep(delay);
                flight.setLanded(true);
            }
        }
        if (flight.hasLanded()) {
            UI.println("Landed");
            UI.sleep(delay);
        }
    }


    public static void loadImage() {
        String filePath = "src/airport.png";
        UI.drawImage(filePath, 100, 200);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlightPathSimulation fp = new FlightPathSimulation();
            fp.setVisible(true);
        });
        FlightPathSimulation fps = new FlightPathSimulation();
        fps.initialiseSimulation();
        fps.setupGUI();

        // fp.loadImage();

    }

}

