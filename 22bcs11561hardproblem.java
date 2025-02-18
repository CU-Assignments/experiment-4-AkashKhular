import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private boolean[] seats;  // Array representing seats (true if booked, false if available)
    private Lock bookingLock = new ReentrantLock();  // Lock to ensure thread-safe booking
    
    public TicketBookingSystem(int totalSeats) {
        seats = new boolean[totalSeats];
    }

    // Book a seat, ensuring synchronization to avoid double booking
    public void bookSeat(int seatNumber) {
        bookingLock.lock();
        try {
            if (seats[seatNumber]) {
                System.out.println("Seat " + seatNumber + " is already booked.");
            } else {
                seats[seatNumber] = true;
                System.out.println(Thread.currentThread().getName() + " successfully booked seat " + seatNumber);
            }
        } finally {
            bookingLock.unlock();
        }
    }

    // Try booking a seat for a regular customer
    public void bookTicket(int seatNumber) {
        bookSeat(seatNumber);
    }

    // Try booking a seat for a VIP customer (VIP customers have higher priority)
    public void bookVIPTicket(int seatNumber) {
        bookSeat(seatNumber);
    }
}

class RegularCustomer extends Thread {
    private TicketBookingSystem system;
    private int seatNumber;

    public RegularCustomer(TicketBookingSystem system, int seatNumber) {
        this.system = system;
        this.seatNumber = seatNumber;
        setPriority(Thread.NORM_PRIORITY); // Regular priority
    }

    @Override
    public void run() {
        system.bookTicket(seatNumber);
    }
}

class VIPCustomer extends Thread {
    private TicketBookingSystem system;
    private int seatNumber;

    public VIPCustomer(TicketBookingSystem system, int seatNumber) {
        this.system = system;
        this.seatNumber = seatNumber;
        setPriority(Thread.MAX_PRIORITY); // VIP priority
    }

    @Override
    public void run() {
        system.bookVIPTicket(seatNumber);
    }
}

public class TicketBookingMain {
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem(5); // 5 seats in total
        
        // Simulate VIP and regular customers
        Thread vip1 = new VIPCustomer(system, 0);
        Thread vip2 = new VIPCustomer(system, 1);
        Thread regular1 = new RegularCustomer(system, 2);
        Thread regular2 = new RegularCustomer(system, 3);
        Thread regular3 = new RegularCustomer(system, 0); // Trying to book the same seat as VIP1

        // Start threads
        vip1.start();
        vip2.start();
        regular1.start();
        regular2.start();
        regular3.start();
    }
}
