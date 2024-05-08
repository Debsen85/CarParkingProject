import java.util.Scanner;

import ParkingInformation.ParkingInformation;
import Tickets.Tickets;
import Vehicles.Vehicles;

public class ParkingLot {

    String parkingLotId;
    Integer noOfFloors;
    Integer noOfSlotsPerFloor;
    ParkingInformation parkingInformation[][];
    static ParkingLot parkingLot;

    public ParkingLot(String parkingLotId, Integer noOfFloors, Integer noOfSlotsPerFloor) {
        this.parkingLotId = parkingLotId;
        this.noOfFloors = noOfFloors;
        this.noOfSlotsPerFloor = noOfSlotsPerFloor;
        init_parkingInformation();  
    } 

    public void init_parkingInformation() {
        this.parkingInformation = new ParkingInformation[this.noOfFloors][this.noOfSlotsPerFloor];
        for (int i = 0; i < noOfFloors; i++) {
            for (int j = 0; j < noOfSlotsPerFloor; j++) {
                parkingInformation[i][j] = null;
            }
        }
    }

    public Tickets create_ticket(int i, int j, String parkingLotId) {
        return new Tickets(parkingLotId, i, j);
    }

    public void display_free_count(String vehicleType) {
        int totalVehicle;
        for (int i = 0; i < noOfFloors; i++) {
            totalVehicle = 0;
            if (vehicleType.equals("TRUCK")) {
                if (parkingInformation[i][0] == null) {
                    totalVehicle++;
                }
            }
            else if (vehicleType.equals("BIKE")) {
                for (int j = 1; j < 3; j++) {
                    if (parkingInformation[i][j] == null) {
                        totalVehicle++;
                    }
                }
            }
            else {
                for (int j = 3; j < noOfSlotsPerFloor; j++) {
                    if (parkingInformation[i][j] == null) {
                        totalVehicle++;
                    }
                }
            }
            System.out.println("No. of free slots for " + vehicleType + " on Floor " + (i + 1) + ": " + totalVehicle);
        }
    }

    public void display_free_slots(String vehicleType) {
        for (int i = 0; i < noOfFloors; i++) {
            System.out.print("Free slots for " + vehicleType + " on Floor " + (i + 1) + ": ");
            if (vehicleType.equals("TRUCK")) {
                if (parkingInformation[i][0] == null) {
                    System.out.print(1);
                }
            }
            else if (vehicleType.equals("BIKE")) {
                for (int j = 1; j < 3; j++) {
                    if (parkingInformation[i][j] == null) {
                        System.out.print((j + 1) + " ");
                    }
                }
            }
            else {
                for (int j = 3; j < noOfSlotsPerFloor; j++) {
                    if (parkingInformation[i][j] == null) {
                        System.out.print((j + 1) + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    public void display_occupied_slots(String vehicleType) {
        for (int i = 0; i < noOfFloors; i++) {
            System.out.print("Occupied slots for " + vehicleType + " on Floor " + (i + 1) + ": ");
            if (vehicleType.equals("TRUCK")) {
                if (parkingInformation[i][0] != null) {
                    System.out.print(1);
                }
            }
            else if (vehicleType.equals("BIKE")) {
                for (int j = 1; j < 3; j++) {
                    if (parkingInformation[i][j] != null) {
                        System.out.print((j + 1) + " ");
                    }
                }
            }
            else {
                for (int j = 3; j < noOfSlotsPerFloor; j++) {
                    if (parkingInformation[i][j] != null) {
                        System.out.print((j + 1) + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    public Boolean check_availbility_and_return_status(Vehicles vehicle) {
        for (int i = 0; i < noOfFloors; i++) {
            if (vehicle.vehicleType.equals("TRUCK")) {
                if (parkingInformation[i][0] == null) {
                    Tickets ticket = parkingLot.create_ticket(i, 0, parkingLotId);
                    ParkingInformation parkingInformationLocal = new ParkingInformation(vehicle, ticket);
                    parkingInformation[i][0] = parkingInformationLocal;
                    System.out.println("Parked vehicle. Ticket ID: " + parkingLotId + "_" + Integer.toString(i + 1) + "_" + Integer.toString(1));
                    return true;
                }
            
            }
            else if (vehicle.vehicleType.equals("BIKE")) {
                for (int j = 1; j < 3; j++) {
                    if (parkingInformation[i][j] == null) {
                        Tickets ticket = parkingLot.create_ticket(i, j, parkingLotId);
                        ParkingInformation parkingInformationLocal = new ParkingInformation(vehicle, ticket);
                        parkingInformation[i][j] = parkingInformationLocal;
                        System.out.println("Parked vehicle. Ticket ID: " + parkingLotId + "_" + Integer.toString(i + 1) + "_" + Integer.toString(j + 1));
                        return true;
                    }
                }
            }
            else {
                for (int j = 3; j < noOfSlotsPerFloor; j++) {
                    if (parkingInformation[i][j] == null) {
                        Tickets ticket = parkingLot.create_ticket(i, j, parkingLotId);
                        ParkingInformation parkingInformationLocal = new ParkingInformation(vehicle, ticket);
                        parkingInformation[i][j] = parkingInformationLocal;
                        System.out.println("Parked vehicle. Ticket ID: " + parkingLotId + "_" + Integer.toString(i + 1) + "_" + Integer.toString(j + 1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void park_vehicle(String vehicleType, String regNo, String color) {
        if (!parkingLot.check_availbility_and_return_status(new Vehicles(vehicleType, regNo, color))) {
            System.out.println("Parking Lot Full");
        } 
    }

    public void unpark_vehicle(String ticket) {
        if (!parkingLot.check_parked_and_return_status(ticket)) {
            System.out.println("Invalid Ticket");
        }
    }

    private Boolean check_parked_and_return_status(String ticket) {
        String ticketComponent[] = ticket.split("_");
        int i = Integer.parseInt(ticketComponent[1]) - 1;
        int j = Integer.parseInt(ticketComponent[2]) - 1;
        if ((i >= 0 && i < noOfFloors) && (j >= 0 && j < noOfSlotsPerFloor) && parkingInformation[i][j] != null) {
            System.out.println("Unparked vehicle with Registration Number: " + parkingInformation[i][j].vehicle.regNo + " and Color: " + parkingInformation[i][j].vehicle.color);
            parkingInformation[i][j] = null;
        }
        return false;
    }

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner command = new Scanner(System.in);
        System.out.println();
        System.out.println();
        System.out.println("<--------Car Parking System-------->");
        System.out.println();
        System.out.println();
        while(true) {
            String inputCommand[] = command.nextLine().split(" ");
            if (inputCommand[0].equals("create_parking_lot")) 
            {
                System.out.println();
                System.out.println();
                String parkingLotIdLocal = inputCommand[1];
                Integer noOfFloorsLocal = Integer.parseInt(inputCommand[2]);
                Integer noOfSlotsPerFloorLocal = Integer.parseInt(inputCommand[3]);
                parkingLot = new ParkingLot(parkingLotIdLocal, noOfFloorsLocal, noOfSlotsPerFloorLocal);
                System.out.println("Created parking lot with " +  noOfFloorsLocal + " floors and " + noOfSlotsPerFloorLocal + " slots per floor");
                System.out.println();
                System.out.println();
            }
             else if (inputCommand[0].equals("park_vehicle")) 
            {
                System.out.println();
                System.out.println();
                String vehicleTypeLocal = inputCommand[1];
                String regNoLocal = inputCommand[2];
                String colorLocal = inputCommand[3];
                parkingLot.park_vehicle(vehicleTypeLocal, regNoLocal, colorLocal);
                System.out.println();
                System.out.println();
            }
            else if (inputCommand[0].equals("display")) {
                System.out.println();
                System.out.println();
                String displayType = inputCommand[1];
                String vehicleType = inputCommand[2];
                if (displayType.equals("free_count")) {
                    parkingLot.display_free_count(vehicleType);
                }
                else if (displayType.equals("free_slots")) {
                    parkingLot.display_free_slots(vehicleType);
                }
                else {
                    parkingLot.display_occupied_slots(vehicleType);
                }
                System.out.println();
                System.out.println();
            }
            else if (inputCommand[0].equals("unpark_vehicle")) {
                System.out.println();
                System.out.println();
                String ticketLocal = inputCommand[1];
                parkingLot.unpark_vehicle(ticketLocal);
                System.out.println();
                System.out.println();
            }
            else {
                System.out.println();
                System.out.println();
                break;
            }
        }
    }
}