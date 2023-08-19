package movie;

public abstract class Booking {
    private int bookingId;
    private int showId;
    private String customerName;
    private int bookedSeats;

    // Constructors
    public Booking() {
    }

    public Booking(int bookingId, int showId, String customerName, int bookedSeats) {
        this.bookingId = bookingId;
        this.showId = showId;
        this.customerName = customerName;
        this.bookedSeats = bookedSeats;
    }

    // Abstract method to calculate the total cost of the booking
    public abstract double calculateTotalCost();

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(int bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    // Sample toString() method for displaying booking details
    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
               "\nShow ID: " + showId +
               "\nCustomer Name: " + customerName +
               "\nBooked Seats: " + bookedSeats;
    }
}
