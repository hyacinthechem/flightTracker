package src;

import ecs100.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.*;
import java.util.List;

public class FlightPathSimulation extends JFrame {

    private JTextField flightNumberField;
    private JLabel imageLabel;
    private boolean sid;
    private boolean star;
    private String currentFlight;

    private List<String> allProcedures = new ArrayList<>();

    private List<String> sidProcedures;
    private List<String> starProcedures;
    private String[] runways = {"RWY05R, RWY23L"};


    public void loadSidData(){


        for(int i = 0; i<runways.length; i++){
            String runway = runways[i];
            if(runway.equals("RWY05R")){
                sidProcedures = new ArrayList<>();
                sidProcedures.add(runway);
            }
        }
    }

    public void loadStarData(){

    }

    public FlightPathSimulation(){
        setTitle("Flight Path Simulation");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel flightLabel = new JLabel("Flight Number:");
        flightNumberField = new JTextField(10);
        add(flightLabel);
        add(flightNumberField);

        //run simulation button

        JButton runSimulation = new JButton("Run Simulation");
        add(runSimulation);

        //departure process buttons

        JButton departureButton = new JButton("Departure");
        add(departureButton);
        departureButton.addActionListener(e -> {
            sid = true;
            star = false;
            if(currentFlight!=null) {
                departureRoutingProcess(currentFlight);
            }else{
                UI.println("Please Select a Flight");
            }

    });

        //arrival process buttons

        JButton arrivalButton = new JButton("Arrival");
        add(arrivalButton);
        arrivalButton.addActionListener(e -> {
            sid = false;
            star = true;
            if(currentFlight!=null){
                arrivalRoutingProcess(currentFlight);
            }else{
                UI.println("Please Select a Flight");
            }
        });

        imageLabel = new JLabel();
        loadImage();
        add(imageLabel);





    }

    public void setupGUI(){

        UI.addButton("Run Simulation" , this::runSimulation);
        UI.addTextField("Flight Number",(String flightNum) ->{ this.currentFlight = flightNum;});
        UI.addButton("Departure Procedures", () ->{
            sid = true;
            star = false;
            if(currentFlight!=null){
                departureRoutingProcess(currentFlight);
            }else{
                UI.println("Select A Flight Number");
            }
        });
        UI.addButton("Arrival Procedures", () ->{
            sid = false;
            star = true;
            if(currentFlight!=null){
                arrivalRoutingProcess(currentFlight);
            }else{
                UI.println("Select A Flight Number");
            }
        });
    }

    public void runSimulation(){
        //run the flight simulation
    }

    public void departureRoutingProcess(String flightNumber){


    }

    public void arrivalRoutingProcess(String flightNumber){

    }

    public static void loadImage(){
        String filePath = "src/airport.png";
        UI.drawImage(filePath, 100,200);
    }





    public static void main(String[] args){
       SwingUtilities.invokeLater(() ->{
           FlightPathSimulation fp = new FlightPathSimulation();
           fp.setVisible(true);
       });
       // fp.setupGUI();
       // fp.loadImage();

    }
}
