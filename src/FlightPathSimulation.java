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
    private boolean prioirty=false; //default
    private String currentFlight;
    private FlightData flightData = new FlightData();

    public String priorityRunway;   // Sets the priority runway ( either 05R or 23L )
    public String[] runway = {"RWY05R", "RWY23L"}; //runway options
    public static final double RUNWAY_LIMIT = 3;
    public static final double RUNWAY_TOP_LIMIT = 1;
    public static final double APPROACH_LIMIT = 4;



    private List<Flight> allFlights = flightData.getAllFlights();
    private Queue<Flight> flightQueue; //main queue where flights wait to then be put in runway queue
    private Queue<Lineup<Flight>> lineupQueue = new ArrayDeque<>();
    private Map<String, Queue<Flight>> runwayQueue = new HashMap();
    private Map<String, Flight> flightMap = new HashMap(); //Key Value pairs mapping flightNumber String to Flight Object
    public int delay = 400;

    public int offset = 5;
    public int positionX = 520 + ( lineupQueue.size() * offset );
    public int poisitionY = 340 + ( lineupQueue.size() * offset );

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
            while(!flightQueue.isEmpty()) {
                departureRoutingProcess(currentFlight);
            }

        });

        //arrival process buttons

        JButton arrivalButton = new JButton("Arrival");
        add(arrivalButton);
        arrivalButton.addActionListener(e -> {
            sid = false;
            star = true;
            while(!flightQueue.isEmpty()) {
                arrivalRoutingProcess(currentFlight);
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
        UI.addButton("Run Priority Simulation" , () -> {
            this.prioirty = true;
            initialiseSimulation();
            runSimulation();
        });
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
        UI.setMouseListener(this::doMouse);
    }

    public void doMouse(String action, double x, double y){
        if(action.equals("pressed")){
            UI.println("Pressed at X: " + x + " Y: " + y);
        }
    }

    public void runSimulation() {
        //run the flight simulation
        int time = 0;
        while (!flightQueue.isEmpty()) { //base case ( keep simulation running when there is a flight in the queue )
            time++; //each iteration advances timer by one tick
            Flight flight = flightQueue.poll();
            if(flight.getFlightDetail().contains("Departure")){
                departureRoutingProcess(flight.getFlightDetail()); //departing flights
            }else{
                arrivalRoutingProcess(flight.getFlightDetail()); //arrival flights
            }

        }

    }

    public void departureRoutingProcess(String flightDetail) {
        Flight flight = flightMap.get(flightDetail);
        Random rand = new Random();
        flight.assignRunway(runway[rand.nextInt(2)]);
         Queue<Flight> runway = runwayQueue.get(flight.getRunway()); //retrieve runway for flight assigned the runwayQueue
            if (runway!=null &&runway.size() < RUNWAY_TOP_LIMIT && !flightQueue.isEmpty()) { //check that runway queue has space and that flightqueue isnt empty
                if(lineupQueue.size()<RUNWAY_LIMIT){ //if the queue is less than 3 on lineup line then offer it to queue
                    if(flight.getRunway().equals("RWY05R")) {
                        lineupQueue.offer(new Lineup(flight, positionX, poisitionY, true));
                    }else{
                        lineupQueue.offer(new Lineup(flight, 251, 250 + (lineupQueue.size() * 3), true));
                    }
                    flight.draw(lineupQueue.peek().getX(),lineupQueue.peek().getY()); //draw the flight on Queue
                    runway.offer(flight); //put the flight in the runwayqueue system
                }
                UI.println("Flight Number: " + flight.getFlightDetail() + " Lined Up " + flight.getRunway());
                UI.sleep(delay);
                runway.poll();
                lineupQueue.poll();
            }


            // assign a runway

            //generate a SID Departure Process
            List<DepartureSid> sidProcedures = flightData.getSidProcedures();
            for (DepartureSid sid : sidProcedures) {
                if (flight.getRunway().equals(sid.getRunway())) {
                    UI.println("Flight Number: " + flight.getFlightDetail() + " Assigned SID: " + sid.getSIDProcedureName());
                    UI.sleep(delay);
                    flight.setTakeoff(true);
                    UI.println("Flight Number: " + flight.getFlightDetail() + " Cleared for Takeoff " + flight.getRunway());
                }
            }

        if(flight.hasTakenOff()){
            UI.println("Takeoff at " + flight.getRunway());
        }
        UI.sleep(delay);
    }

    public void arrivalRoutingProcess(String flightDetail) {
        Flight flight = flightMap.get(flightDetail); //retrieve flight Object with associated flight detail
        Random rand = new Random();
        flight.assignRunway(runway[rand.nextInt(2)]); //assign random runway between 23L and 05R
        for (Queue<Flight> runway : runwayQueue.values()) {
            if (runway.size() < APPROACH_LIMIT && runway.size() < RUNWAY_TOP_LIMIT && !flightQueue.isEmpty()) {
                runway.offer(flight); //offer the flight to the runwayQueue
                UI.println("Flight Number: " + flight.getFlightDetail() + " Entered approach for Runway:  " + flight.getRunway());
                UI.sleep(delay);
                runway.poll(); //remove from runwayQueue
            }
        }
        List<ArrivalStar> arrivalProcedures = flightData.getArrivalProcedures();

        for (ArrivalStar STAR : arrivalProcedures) {
            if (flight.getRunway().equals(STAR.getRunway())) {
                UI.println("Flight Number: " + flight.getFlightDetail() + " Assigned STAR " + STAR.getSTARProcedureName());
                UI.sleep(delay);
                UI.println("Flight Number: " + flight.getFlightDetail() + " Cleared to Land Runway  " + STAR.getRunway());
                UI.sleep(delay);
                flight.setLanded(true);
            }

        }
        if (flight.hasLanded()) {
            UI.println("Landed at " + flight.getRunway());
            UI.sleep(delay);
        }
    }


    public static void loadImage() {
        String filePath = "src/airport.jpg";
        UI.drawImage(filePath, 200, 200);
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

