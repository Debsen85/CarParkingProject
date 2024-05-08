package ParkingInformation;

import Tickets.Tickets;
import Vehicles.Vehicles;

public class ParkingInformation {
    
    public Vehicles vehicle;
    public Tickets ticket;
    
    public ParkingInformation(Vehicles vehicle, Tickets ticket) {
        this.vehicle = vehicle;
        this.ticket = ticket;
    }

}