package movie;

import java.util.Date;

class PremiumMovie extends Movie {
    private double premiumPrice;

    // Constructors
    public PremiumMovie() {
        super();
    }

    public PremiumMovie(int movieId, String movieName, String genre, Date releaseDate, int duration, double premiumPrice) {
        super(movieId, movieName, genre, releaseDate, duration);
        this.premiumPrice = premiumPrice;
    }

    // Getter and Setter for premiumPrice
    public double getPremiumPrice() {
        return premiumPrice;
    }

    public void setPremiumPrice(double premiumPrice) {
        this.premiumPrice = premiumPrice;
    }

    // Sample toString() method for displaying premium movie details
    @Override
    public String toString() {
        return super.toString() +
               "\nPremium Price: " + premiumPrice;
    }
}
