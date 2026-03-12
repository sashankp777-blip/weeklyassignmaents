import java.util.*;

class Vehicle {
    String plate;
    long entryTime;

    Vehicle(String plate) {
        this.plate = plate;
        this.entryTime = System.currentTimeMillis();
    }
}

public class Parking {

    private Vehicle[] table;
    private int capacity = 500;
    private int occupied = 0;

    public Parking() {
        table = new Vehicle[capacity];
    }

    // Hash function
    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % capacity;
    }

    // Park vehicle using linear probing
    public void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % capacity;
            probes++;
        }

        table[index] = new Vehicle(plate);
        occupied++;

        System.out.println("parkVehicle(\"" + plate + "\") -> Assigned spot #" + index +
                " (" + probes + " probes)");
    }

    // Exit vehicle
    public void exitVehicle(String plate) {

        int index = hash(plate);

        while (table[index] != null) {

            if (table[index].plate.equals(plate)) {

                long exitTime = System.currentTimeMillis();
                long duration = exitTime - table[index].entryTime;

                double hours = duration / 3600000.0;
                double fee = hours * 5;

                System.out.println("exitVehicle(\"" + plate + "\") -> Spot #" + index +
                        " freed, Duration: " +
                        String.format("%.2f", hours) +
                        " hours, Fee: $" +
                        String.format("%.2f", fee));

                table[index] = null;
                occupied--;
                return;
            }

            index = (index + 1) % capacity;
        }

        System.out.println("Vehicle not found");
    }

    // Parking statistics
    public void getStatistics() {

        double occupancy = (occupied * 100.0) / capacity;

        System.out.println("getStatistics() -> Occupancy: "
                + String.format("%.2f", occupancy) + "%");
    }

    public static void main(String[] args) throws InterruptedException {

        Parking lot = new Parking();

        lot.parkVehicle("ABC-1234");
        lot.parkVehicle("ABC-1235");
        lot.parkVehicle("XYZ-9999");

        Thread.sleep(2000);

        lot.exitVehicle("ABC-1234");

        lot.getStatistics();
    }
}