package Functions;

import java.sql.SQLException;

public interface AdminFunctionsInterface {
public void adminMenu() throws SQLException ;
     // Method to add a new book to the inventory
    public void addBook() throws SQLException ;
    // Method to update an existing book by ISBN
    public void updateBookByISBN() throws SQLException ;

    // Method to remove a book from the inventory by ISBN
    public void removeBookByISBN() throws SQLException ;
    // Method to display books by genre
    public void displayBooksByGenre() ;
    // Method to manage VIP passes
    public void manageVIPPass() throws SQLException ;

    // Method to display all users with VIP passes
    public void displayAllVIPUsers() ;

    // Method to remove a user
    public void removeUser() throws SQLException ;

    // Method to display all users
    public void displayAllUsers() ;

    // Method to display all books in a formatted manner
    public void displayAllBooks() ;
}
