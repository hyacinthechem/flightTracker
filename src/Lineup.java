package src;
import ecs100.*;
import java.util.*;

public class Lineup<E> {
    private E flight;
    private double lineupX;
    private double lineupY;
    private boolean occupied;

    public Lineup( E flight,double x, double y, boolean occupied) {
        this.flight = flight;
        lineupX = x;
        lineupY = y;
        this.occupied = occupied;
    }

    public double getX(){
        return lineupX;
    }

    public double getY(){
        return lineupY;
    }

    public E getFlight(){
        return flight;
    }


    public boolean isOccupied(){
        return occupied;
    }

}
