package movie;

import java.util.Date;

public class Show {
    private int showId;
    private int movieId;
    private Date showTime;
    private int availableSeats;

    // Constructors
    public Show() {
    }

    public Show(int showId, int movieId, Date showTime, int availableSeats) {
        this.showId = showId;
        this.movieId = movieId;
        this.showTime = showTime;
        this.availableSeats = availableSeats;
    }

    // Getters and Setters
    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    // Sample toString() method for displaying show details
    @Override
    public String toString() {
        return "Show ID: " + showId +
               "\nMovie ID: " + movieId +
               "\nShow Time: " + showTime +
               "\nAvailable Seats: " + availableSeats;
    }

    // Get ticket price (Polymorphism)
    public double getTicketPrice() {
        // Logic to calculate ticket price based on the show or movie characteristics
        // For simplicity, we'll return a fixed ticket price for all shows.
        return 10.0;
    }

    // Setter for ticket price (Polymorphism)
    public void setTicketPrice(double ticketPrice) {
        // This setter is provided for future use or updates if required.
        // In this example, we don't update the ticket price of shows.
    }
}
