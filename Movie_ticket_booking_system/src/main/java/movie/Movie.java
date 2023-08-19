package movie;

import java.util.Date;

public class Movie {
    private int movieId;
    private String movieName;
    private String genre;
    private Date releaseDate;
    private int duration;

    // Constructors
    public Movie() {
    }

    public Movie(int movieId, String movieName, String genre, Date releaseDate, int duration) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    // Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Sample toString() method for displaying movie details
    @Override
    public String toString() {
        return "Movie ID: " + movieId +
               "\nMovie Name: " + movieName +
               "\nGenre: " + genre +
               "\nRelease Date: " + releaseDate +
               "\nDuration: " + duration + " minutes";
    }
}
