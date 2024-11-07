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
    private String currentFlight;

    public String priorityRunway;

    private List<String> allProcedures = new ArrayList<>();

    private List<DepartureSid> sidProcedures;
    private List<ArrivalStar> starProcedures;
    private String[] runways = {"RWY05R, RWY23L"};


    public void loadSidData() {
        String filePath = "src/data/RWY05R - BASIV9B";
        try {
            Scanner sc = new Scanner(Path.of(filePath));
            String procedureName = sc.next();
            while (sc.hasNextLine()) {
                String waypointName = sc.next();
                double altitude = sc.nextDouble();
                int speed = sc.nextInt();
                double heading = sc.nextDouble();
                Waypoint wp = new Waypoint(waypointName, altitude, speed, heading);
                //DepartureSid dp = new DepartureSid(procedureName,)
            }


        } catch (IOException e) {
            UI.println("File Failure" + e);
        }
    }

    public void loadStarData() {

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
            int result = JOptionPane.showConfirmDialog(this,"Do you want RWY05R as priority?", "Set Prioirty Runway", JOptionPane.YES_NO_OPTION );
            if(result == JOptionPane.YES_OPTION){
                setPriorityRunway(true);
                System.out.println("RWY05R Selected");
            }else{
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
    }

    public void runSimulation() {
        //run the flight simulation

    }

    public void departureRoutingProcess(String flightNumber) {


    }

    public void arrivalRoutingProcess(String flightNumber) {

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
        // fp.setupGUI();
        // fp.loadImage();

    }

}

