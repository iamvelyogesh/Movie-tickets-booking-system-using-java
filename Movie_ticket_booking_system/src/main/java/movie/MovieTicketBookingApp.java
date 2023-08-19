package movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum UserRole {
    ADMIN, THEATER_OWNER, USER
}

public class MovieTicketBookingApp {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        try (Connection connection = MySQLJDBCConnection.getConnection()) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
            	System.out.print("           ____   _   _  ___  __  __ \n");
            	System.out.print("          / ___| | \\ | ||_ _| \\ \\/ / \n");
            	System.out.print("          \\___ \\ |  \\| | | |   \\  /  \n");
            	System.out.print("           ___) || |\\  | | |   /  \\  \n");
            	System.out.print("          |____/ |_| \\_||___| /_/\\_\\ \n");



                  System.out.println("\n           "  + getCurrentDateAndTime());
                  
                System.out.println("===== Movie Ticket Booking System ===============");
                System.out.println("=================================================");
                System.out.print("\n===== About MY Movie Ticket Booking System ======\n\n" +
                        "Welcome to the Movie Ticket Booking System!\n\n" +
                        "This system allows users to book movie tickets\n            for various movies.\n\n" +
                        "It offers a wide range of movie genres and\n             premium movie options.\n\n" +
                        "         Developed by: Vel Yogesh C B\n\n" +
                        "             Version: 1.0\n\n" +
                        "         Release Date: 01-08-2023\n\n" +
                        "==================================================\n\n");
                System.out.println("                   HomePage\n");
                System.out.println("\"==================================================");
                System.out.println("1. Admin Login");
                System.out.println("2. Theater Owner Login");
                System.out.println("3. User Sign In/Sign Up");
                System.out.println("4. Exit");
                System.out.print("Please Choose Your Catogary of Login: ");
                int choice = scanner.nextInt();
                System.out.println("\n==================================================");
                

                switch (choice) {
                    case 1:
                        adminLogin(connection, scanner);
                        break;
                    case 2:
                        theaterOwnerLogin(connection, scanner);
                        break;
                    case 3:
                        userSignInOrSignUp(connection, scanner);
                        break;
                    case 4:
                        System.out.println("Thank you for using Movie Ticket Booking System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void displayBookings(Connection connection) throws SQLException {
        String query = "SELECT * FROM bookings";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("=== Bookings Table ===");
            while (resultSet.next()) {
                int bookingId = resultSet.getInt("booking_id");
                int showId = resultSet.getInt("show_id");
                String customerName = resultSet.getString("customer_name");
                int bookedSeats = resultSet.getInt("booked_seats");
                double ticketPrice = resultSet.getDouble("ticketPrice");

                System.out.println("Booking ID: " + bookingId);
                System.out.println("Show ID: " + showId);
                System.out.println("Customer Name: " + customerName);
                System.out.println("Booked Seats: " + bookedSeats);
                System.out.println("Ticket Price: " + ticketPrice);
                System.out.println("--------------------");
            }
        }
    }


    private static String getCurrentDateAndTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Date: " + currentDateTime.format(formatter);
    }
    private static void adminLogin(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter admin username: ");
        String adminUsername = scanner.next();

        System.out.print("Enter admin password: ");
        String adminPassword = scanner.next();

        // Replace this with proper authentication mechanisms
        if ("admin".equals(adminUsername) && "admin123".equals(adminPassword)) {
            adminMenu(connection, scanner);
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    
    private static String getLastPremiumMovie(Connection connection) throws SQLException {
        String movieNameAndPrice = "";

        String query = "SELECT movie_name, premium_price FROM movies WHERE genre = 'Premium' ORDER BY movie_id DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                String movieName = resultSet.getString("movie_name");
                double premiumPrice = resultSet.getDouble("premium_price");

                movieNameAndPrice = "Movie "+movieName + " is available for a premium price of " + premiumPrice;
            }
        }

        return movieNameAndPrice;
    }



    private static void adminMenu(Connection connection, Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("\n\n===== Admin Menu =====");
            System.out.println("1. Add Theater Owner");
            System.out.println("2. Delete Theater Owner");
            System.out.println("3. Upload Movie");
            System.out.println("4. See Bookings and Paymnets");
            System.out.println("5. See Feedback");
            System.out.println("6. View Movies"); // Option to view movies
            System.out.println("7. Delete Movie"); 
            System.out.println("8. Add Premium Movies"); 
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addTheaterOwner(connection, scanner);
                    break;
                case 2:
                    deleteTheaterOwner(connection, scanner);
                    break;
                case 3:
                    uploadMovie(connection, scanner);
                    break;
                case 4:
                	displayBookings(connection);
                	break;
                case 5:
                	displayFeedback(connection);
                case 6:
                    viewMovies(connection); // Call the viewMovies method
                    break;
                case 7:
                    deleteMovie(connection, scanner); // Call the deleteMovie method
                    break;
            
                case 8:
                    addPremiumMovie(connection, scanner); // Call the new method to add a premium movie
                    break;
                case 9:
                    System.out.println("Logging out from Admin account.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    
    private static void displayFeedback(Connection connection) throws SQLException {
        String query = "SELECT * FROM feedback";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("=== Feedback Table ===");
            while (resultSet.next()) {
                int feedbackId = resultSet.getInt("feedback_id");
                int showId = resultSet.getInt("movie_id");
                String customerName = resultSet.getString("customer_name");
                String feedback = resultSet.getString("feedback_text");

                System.out.println("Feedback ID: " + feedbackId);
                System.out.println("Show ID: " + showId);
                System.out.println("Customer Name: " + customerName);
                System.out.println("Feedback: " + feedback);
                System.out.println("--------------------");
            }
        }
    }

    
    

    
    
    private static void addPremiumMovie(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter movie name: ");
        String movieName = scanner.next();

        System.out.print("Enter movie description: ");
        String description = scanner.next();

        System.out.print("Enter movie duration (in minutes): ");
        int duration = scanner.nextInt();

        System.out.print("Enter the theater owner ID who is uploading this movie: ");
        int theaterOwnerId = scanner.nextInt();

        System.out.print("Enter movie genre: ");
        String genre = scanner.next();

        System.out.print("Enter premium price: ");
        double premiumPrice = scanner.nextDouble(); // Get the premium price for the movie

        // Insert new premium movie into the 'movies' table
        String insertQuery = "INSERT INTO movies (movie_name, description, duration, theater_owner_id, genre, premium_price) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, movieName);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, duration);
            preparedStatement.setInt(4, theaterOwnerId);
            preparedStatement.setString(5, genre);
            preparedStatement.setDouble(6, premiumPrice);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Premium Movie uploaded successfully!");
            } else {
                System.out.println("Failed to upload the Premium Movie.");
            }
        }
    }


    
    private static void viewMovies(Connection connection) throws SQLException {
        List<Movie> movies = new ArrayList<>();

        String query = "SELECT * FROM Movies";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String movieName = resultSet.getString("movie_name");
                String genre = resultSet.getString("genre");
                int duration = resultSet.getInt("duration");

                Movie movie = new Movie();
                movie.setMovieId(movieId);
                movie.setMovieName(movieName);
                movie.setGenre(genre);
                movie.setDuration(duration);

                movies.add(movie);
            }
        }

        if (!movies.isEmpty()) {
            System.out.println("=== Available Movies ===");
            for (Movie movie : movies) {
                System.out.println(movie);
                System.out.println("----------------------------");
            }
        } else {
            System.out.println("No movies found.");
        }
    }

    
    private static void deleteMovie(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the movie ID to delete: ");
        int movieIdToDelete = scanner.nextInt();

        // Delete the movie from the 'movies' table
        String deleteQuery = "DELETE FROM movies WHERE movie_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, movieIdToDelete);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie with ID " + movieIdToDelete + " deleted successfully!");
            } else {
                System.out.println("No movie found with the specified ID.");
            }
        }
    }

    
    private static void addTheaterOwner(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter theater owner email: ");
        String ownerEmail = scanner.next();

        System.out.print("Enter theater owner password: ");
        String ownerPassword = scanner.next();

        // Insert new theater owner into the 'theater_owners' table
        String insertQuery = "INSERT INTO theater_owners (email, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, ownerEmail);
            preparedStatement.setString(2, ownerPassword);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Theater owner added successfully!");
            } else {
                System.out.println("Failed to add theater owner.");
            }
        }
    }


    private static void deleteTheaterOwner(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter theater owner email to delete: ");
        String ownerEmailToDelete = scanner.next();

        // Delete theater owner from the 'theater_owners' table
        String deleteQuery = "DELETE FROM theater_owners WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, ownerEmailToDelete);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Theater owner deleted successfully!");
            } else {
                System.out.println("No theater owner found with the specified email.");
            }
        }
    }


    private static void uploadMovie(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter movie name: ");
        String movieName = scanner.next();

        System.out.print("Enter movie description: ");
        String description = scanner.next();

        System.out.print("Enter movie duration (in minutes): ");
        int duration = scanner.nextInt();  // Add this line to capture the movie duration input

        System.out.print("Enter the theater owner ID who is uploading this movie: ");
        int theaterOwnerId = scanner.nextInt();

        System.out.print("Enter movie genre: ");
        String genre = scanner.next();

        // Insert new movie into the 'movies' table
        String insertQuery = "INSERT INTO movies (movie_name, description, duration, theater_owner_id, genre) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, movieName);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, duration);  // Set the movie duration in the PreparedStatement
            preparedStatement.setInt(4, theaterOwnerId);
            preparedStatement.setString(5, genre);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie uploaded successfully!");
            } else {
                System.out.println("Failed to upload the movie.");
            }
        }
    }




    private static void theaterOwnerLogin(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter theater owner email: ");
        String ownerEmail = scanner.next();

        System.out.print("Enter theater owner password: ");
        String ownerPassword = scanner.next();

        // Check if the theater owner exists in the database and if the provided password matches
        String selectQuery = "SELECT * FROM theater_owners WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, ownerEmail);
            preparedStatement.setString(2, ownerPassword);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Theater owner login successful!\n\n");
                    theaterOwnerMenu(connection, scanner);
                } else {
                    System.out.println("Invalid email or password.\n\n");
                    theaterOwnerLogin(connection,scanner);
                }
            }
        }
    }

    private static void theaterOwnerMenu(Connection connection, Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("===== Theater Owner Menu =====");
            System.out.println("1. Add Movies");
            System.out.println("2. Add Shows");
            System.out.println("3. LogOut");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    uploadMovie(connection, scanner);
                    break;
                case 2:
                    addShow(connection, scanner); // Call the new method to add shows
                    break;
                case 3:
                    System.out.println("Logging out from Admin account.");
                    return;
                	
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        

    }

    

    private static void addShow(Connection connection, Scanner scanner) throws SQLException {
	   
    	System.out.print("Enter movie ID for the show: ");
        int movieId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        System.out.print("Enter show time (yyyy-MM-dd HH:mm:ss): ");
        String showTimeStr = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date showTime;
        try {
            showTime = sdf.parse(showTimeStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Show addition failed.");
            return;
        }

        System.out.print("Enter available seats for the show: ");
        int availableSeats = scanner.nextInt();

        // Insert new show into the 'shows' table
        String insertQuery = "INSERT INTO shows (movie_id, show_time, available_seats) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, movieId);
            preparedStatement.setTimestamp(2, new Timestamp(showTime.getTime()));
            preparedStatement.setInt(3, availableSeats);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Show added successfully!");
            } else {
                System.out.println("Failed to add the show.");
            }
        }
		
	}

	private static void userSignInOrSignUp(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("1. Sign In");
        System.out.println("2. Sign Up");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
//                userSignIn(connection, scanner);
            	 int userId = userSignIn(connection, scanner);
                 if (userId != -1) {
                     userMenu(connection, scanner, userId);
                 }
                break;
            case 2:
                userSignUp(connection, scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }


	
	 private static int userSignIn(Connection connection, Scanner scanner) throws SQLException {
	        System.out.print("Enter your email: ");
	        String email = scanner.next();

	        System.out.print("Enter your password: ");
	        String password = scanner.next();

	        // Check if the user exists and the provided password matches
	        int userId = getUserIDFromDatabase(connection, email, password);
	        if (userId != -1) {
	            System.out.println("User login successful!");
	            return userId;
	        } else {
	            System.out.println("Invalid email or password.");
	            return -1;
	        }
	    }
	 private static int getUserIDFromDatabase(Connection connection, String email, String password) throws SQLException {
	        // Query the database to check if the user exists and the provided password matches
	        String selectQuery = "SELECT id FROM users WHERE email = ? AND password = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
	            preparedStatement.setString(1, email);
	            preparedStatement.setString(2, password);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getInt("id");
	                }
	            }
	        }
	        return -1;
	    }


    private static boolean isUserValid(Connection connection, String email, String password) throws SQLException {
        // Query the database to check if the user exists and the provided password matches
        String selectQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If a row is returned, the user is valid; otherwise, not valid
            }
        }
    }
    
    private static void displayOrders(Connection connection, int userId) throws SQLException {
        String query = "SELECT * FROM orders WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int showId = resultSet.getInt("show_id");
                    String customerName = resultSet.getString("customer_name");
                    int bookedSeats = resultSet.getInt("booked_seats");
                    double totalPrice = resultSet.getDouble("ticket_price");
//                    String paymentMode = resultSet.getString("payment_mode");
                    String Datee = resultSet.getString("order_date");

                    System.out.println("Order ID: " + orderId);
                    System.out.println("Show ID: " + showId);
                    System.out.println("Customer Name: " + customerName);
                    System.out.println("Booked Seats: " + bookedSeats);
                    System.out.println("Total Price: " + totalPrice);
                    System.out.println("Payment Status: " + Datee);
                    System.out.println("------------------------------");
                }
            }
        }
    }







    private static void userSignUp(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter your email: ");
        String email = scanner.next();

        System.out.print("Enter your password: ");
        String password = scanner.next();

        String insertQuery = "INSERT INTO users (email, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User signed up successfully!");
            } else {
                System.out.println("Failed to sign up. Please try again.");
            }
        }
    }

    private static void userMenu(Connection connection, Scanner scanner, int userId) throws SQLException {
        while (true) {
        	
        	System.out.println("Here Sre Some Movie Updates\n");
            System.out.print(getLastPremiumMovie(connection)+"\n\n");
            System.out.println("===== User Menu =====");
            System.out.println("1. Search Movies");
            System.out.println("2. Book Tickets");
            System.out.println("3. Your Orders");
            System.out.println("4. Send Feedback");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    searchMovies(connection,scanner);
                    break;
                case 2:
                    bookTickets(connection, scanner,userId);
                    break;
                case 3:
                    displayOrders(connection, userId);
                    break;
                case 4:
                    feedback(connection,scanner); // Call the feedback method
                    break;
                case 5:
                    System.out.println("Logging out from User account.");
                    return;
                
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void searchMovies(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter the movie name you want to search: ");
        String searchMovieName = scanner.next();

        List<Movie> movies = new ArrayList<>();

        String query = "SELECT * FROM Movies WHERE movie_name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + searchMovieName + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int movieId = resultSet.getInt("movie_id");
                    String movieName = resultSet.getString("movie_name");
                    String genre = resultSet.getString("genre");
                    int duration = resultSet.getInt("duration");

                    Movie movie = new Movie();
                    movie.setMovieId(movieId);
                    movie.setMovieName(movieName);
                    movie.setGenre(genre);
                    movie.setDuration(duration);

                    movies.add(movie);
                }
            }
        }

        if (!movies.isEmpty()) {
            System.out.println("=== Available Movies with \"" + searchMovieName + "\" in the name ===");
            for (Movie movie : movies) {
                System.out.println(movie);
                System.out.println("----------------------------");
            }
        } else {
            System.out.println("No movies found with \"" + searchMovieName + "\" in the name.");
        }
    }


    private static void bookTickets(Connection connection, Scanner scanner,int userId) throws SQLException {
    	 System.out.print("Enter the show ID for which you want to book tickets: ");
         int showId = scanner.nextInt();
         scanner.nextLine(); // Consume newline left-over
 
         Show selectedShow = getShowById(connection, showId);
         if (selectedShow == null) {
             System.out.println("Show with ID " + showId + " not found.");
             return;
         }
          
         
         
         System.out.println("=== Show Details ===");
         System.out.println(selectedShow);
         System.out.println("--------------------");
 
         System.out.print("Enter your name: ");
         String customerName = scanner.nextLine();
 
         System.out.print("Enter the number of seats you want to book: ");
         int bookedSeats = scanner.nextInt();
 
         if (bookedSeats <= 0 || bookedSeats > selectedShow.getAvailableSeats()) {
             System.out.println("Invalid number of seats. Please try again.");
             return;
         }
 
         // Update available seats in the database
         int updatedSeats = selectedShow.getAvailableSeats() - bookedSeats;
         updateAvailableSeats(connection, showId, updatedSeats);
         
         double ticketPrice = updatedSeats * 150;
 
         // Insert booking details into the database
         insertBooking(connection, showId, customerName, bookedSeats,ticketPrice,userId);
        
        System.out.println("Payment Options:");
        System.out.println("1. UPI Payment");
        System.out.println("2. Credit Card Payment");
        System.out.println("3. Debit Card Payment");
        System.out.print("Enter your choice: ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        
        switch (paymentChoice) {
            case 1:
                boolean isUpiPaymentSuccessful = performUpiPayment(scanner);
                if (!isUpiPaymentSuccessful) {
                    System.out.println("UPI Payment failed. Booking cancelled.");
                    return;
                }
                break;
            case 2:
            case 3:
                boolean isCardPaymentSuccessful = performCardPayment(scanner);
                if (!isCardPaymentSuccessful) {
                    System.out.println("Card Payment failed. Booking cancelled.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid payment choice. Booking cancelled.");
                return;
        }
        
      
        displayTicketDetails(connection, showId, customerName, bookedSeats);
        
        // Store ticket details in the orders table for the respective user ID
        storeTicketInOrders(connection, showId, customerName, bookedSeats, ticketPrice,userId);
    }
    
    
    
    
    
    
    private static boolean performUpiPayment(Scanner scanner) {
        System.out.print("Enter UPI app name: ");
        String upiAppName = scanner.nextLine();
        
        System.out.print("Enter UPI number: ");
        String upiNumber = scanner.nextLine();
        
        // Send OTP to the provided UPI number for verification
        int otp = generateOtp();
        System.out.println("Please enter the captcha for Verification " + otp);
        
        System.out.print("Enter OTP: ");
        int enteredOtp = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        
        // Verify OTP
        if (enteredOtp == otp) {
            System.out.println("UPI Payment successful!");
            return true;
        } else {
            System.out.println("Invalid OTP. UPI Payment failed.");
            return false;
        }
    }
    
    private static boolean performCardPayment(Scanner scanner) {
        System.out.print("Enter cardholder name: ");
        String cardholderName = scanner.nextLine();
        
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        
        System.out.print("Enter CVV: ");
        String cvv = scanner.nextLine();
        
        System.out.print("Enter expiry date (MM/YY): ");
        String expiryDate = scanner.nextLine();
        
        // Dummy card validation (using a simple check of card number length)
        if (cardNumber.length() == 16) {
            System.out.println("Card Payment successful!");
            return true;
        } else {
            System.out.println("Invalid card details. Card Payment failed.");
            return false;
        }
    }
    
    private static int generateOtp() {
        // Dummy OTP generation (in a real-world application, use a secure method to generate OTP)
        return (int) (Math.random() * 10000);
    }
    
    private static void displayTicketDetails(Connection connection, int showId, String customerName, int bookedSeats) throws SQLException {
        System.out.println("=== Ticket Details ===");
        System.out.println("Show ID: " + showId);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Booked Seats: " + bookedSeats);
        // ... Add more ticket details as needed ...
        System.out.println("======================");
    }
    
    private static void storeTicketInOrders(Connection connection, int showId, String customerName, int bookedSeats, double ticketPrice,int userId) throws SQLException {
        String insertQuery = "INSERT INTO orders (show_id, customer_name, booked_seats, ticket_price, order_date,user_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, showId);
            preparedStatement.setString(2, customerName);
            preparedStatement.setInt(3, bookedSeats);
            preparedStatement.setDouble(4, ticketPrice);
            preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));
            preparedStatement.setInt(6, userId);// Use current date as the order date

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
            	System.out.print("Here is Your Ticket!! Enjoy your flim\n");    	
                System.out.println("Ticket details stored in the orders table successfully!");
            } else {
                System.out.println("Failed to store ticket details in the orders table.");
            }
        }
    } 
        private static Show getShowById(Connection connection, int showId) throws SQLException {
          String query = "SELECT * FROM Shows WHERE show_id = ?";
          try (PreparedStatement statement = connection.prepareStatement(query)) {
              statement.setInt(1, showId);
              try (ResultSet resultSet = statement.executeQuery()) {
                  if (resultSet.next()) {
                      int movieId = resultSet.getInt("movie_id");
                      Date showTime = resultSet.getTimestamp("show_time");
                      int availableSeats = resultSet.getInt("available_seats");
  
                      Show show = new Show();
                      show.setShowId(showId);
                      show.setMovieId(movieId);
                      show.setShowTime(showTime);
                      show.setAvailableSeats(availableSeats);
  
                      return show;
                  }
              }
          }
          return null;
      }
        
        private static void updateAvailableSeats(Connection connection, int showId, int availableSeats) throws SQLException {
            String query = "UPDATE Shows SET available_seats = ? WHERE show_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, availableSeats);
                statement.setInt(2, showId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Available seats updated successfully!");
                } else {
                    System.out.println("Failed to update available seats.");
                }
            }
        }

  
      private static void insertBooking(Connection connection, int showId, String customerName, int bookedSeats,double ticketPrice,int userId) throws SQLException {
          String query = "INSERT INTO Bookings (show_id, customer_name, booked_seats , ticketPrice,user_id) VALUES (?, ?, ?, ?, ?)";
          try (PreparedStatement statement = connection.prepareStatement(query)) {
              statement.setInt(1, showId);
              statement.setString(2, customerName);
              statement.setInt(3, bookedSeats);
              statement.setDouble(4, ticketPrice);
              statement.setInt(5, userId);
              statement.executeUpdate();
          }
      }
      
      private static void feedback(Connection connection, Scanner scanner) throws SQLException {
    	    System.out.println("=== Provide Feedback ===");
    	    System.out.print("Enter your name: ");
    	    String customerName = scanner.next();

    	    // Display available movies to the user
    	    viewMovies(connection);

    	    System.out.print("Enter the ID of the movie you want to provide feedback for: ");
    	    int movieId = scanner.nextInt();

    	    // Check if the movie with the provided ID exists in the database
    	    String selectQuery = "SELECT * FROM movies WHERE movie_id = ?";
    	    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
    	        preparedStatement.setInt(1, movieId);
    	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
    	            if (!resultSet.next()) {
    	                System.out.println("No movie found with the specified ID.");
    	                return;
    	            }
    	        }
    	    }

    	    scanner.nextLine(); // Consume newline left-over

    	    System.out.print("Enter your feedback: ");
    	    String feedbackText = scanner.nextLine();

    	    // Insert feedback details into the database
    	    String insertQuery = "INSERT INTO feedback (movie_id, customer_name, feedback_text) VALUES (?, ?, ?)";
    	    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
    	        preparedStatement.setInt(1, movieId);
    	        preparedStatement.setString(2, customerName);
    	        preparedStatement.setString(3, feedbackText);
    	        int rowsAffected = preparedStatement.executeUpdate();
    	        if (rowsAffected > 0) {
    	            System.out.println("Feedback submitted successfully!");
    	        } else {
    	            System.out.println("Failed to submit feedback. Please try again.");
    	        }
    	    }
    	}
}

      

