package Functions;

import java.io.IOException;
import java.sql.SQLException;

public interface UserFunctionsInterface {

    // Method to add a book to the cart
    void addToCart(String username) throws SQLException;

    // Method to make a payment
    void makePayment(String username);

    // Method to view user's bills
    void viewBills(String username);

    // Method to view the user's cart
    void viewCart(String username);

    // Method to search for a book by ISBN
    void searchBookByIsbn(String username);

    // Method to view books by genre
    void viewBooksByGenre(String username);

    // Method to give a rating to a book
    void giveRatingToBook();

    // Method to handle the user menu
    void userMenu(String username) throws SQLException,IOException;

    void displayBooksMenu(String username);
    

}