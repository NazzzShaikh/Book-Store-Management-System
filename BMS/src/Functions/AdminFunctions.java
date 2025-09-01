package Functions;

import java.sql.*;
import java.util.Scanner;

public class AdminFunctions implements AdminFunctionsInterface {
    Connection connection;

    AdminFunctions(Connection con) {
        this.connection = con;
    }

    Scanner scanner = new Scanner(System.in);

    // Method to display the admin menu
    public void adminMenu() throws SQLException {
        boolean f = true;
        while (f) {
            System.out.println("""

                                Admin Menu
                    ------------------------------------
                        1 --> Add Book
                        2 --> Remove Book
                        3 --> Update Book
                        4 --> View All Books
                        5 --> Display Books by Genre
                        6 --> View all Admin
                        7 --> Manage VIP Pass
                        8 --> View Sales History
                        9 --> Display All Users
                       10 --> Logout
                    -------------------------------------

                            """);
            System.out.print("Enter Your Choice : ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> addBook();
                case 2 -> removeBookByISBN();
                case 3 -> updateBookByISBN();
                case 4 -> displayAllBooks();
                case 5 -> displayBooksByGenre();
                case 6 -> viewAllAdmins();
                case 7 -> manageVIPPass();
                case 8 -> viewSalesHistory();
                case 9 -> displayAllUsers();
                case 10 -> f = false;

                default -> System.out.println("Enter Proper Choice ");
            }
        }
    }

    // Method to add a new book to the inventory
    public void addBook() throws SQLException {
        System.out.print("Enter ISBN :");
        String isbn = scanner.next();
        scanner.nextLine();
        System.out.print("Enter Title :");
        String title = scanner.nextLine();
        System.out.print("Enter Author :");
        String author = scanner.nextLine();
        System.out.print("Enter Genre : ");
        String genre = scanner.nextLine();
        System.out.println("Publised Date (yyyy-mm-dd):");
        String published_date = scanner.nextLine();
        System.out.println("enter publisher :");
        String publisher = scanner.nextLine();
        System.out.print("Enter Price :");
        double price = scanner.nextDouble();
        System.out.println("Enter Rating :");
        double rating = scanner.nextDouble();
        System.out.print("Enter Quantity :");
        int stock = scanner.nextInt();

        String query = "INSERT INTO books (isbn, title, author, genre, price, stock_quantity ,published_date, publisher, rating) VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, isbn);
        pstmt.setString(2, title);
        pstmt.setString(3, author);
        pstmt.setString(4, genre);
        pstmt.setDouble(5, price);
        pstmt.setInt(6, stock);
        pstmt.setString(7, published_date);
        pstmt.setString(8, publisher);
        pstmt.setDouble(9, rating);
        pstmt.executeUpdate();
        System.out.println("Book added successfully.");
    }

    // Method to update an existing book by ISBN
    public void updateBookByISBN() throws SQLException {
        displayAllBooks();
        System.out.print("Do you want to Update any Book (yes/no) :");
        String ans = scanner.next();
        if (ans.equalsIgnoreCase("yes")) {
            System.out.print("Enter ISBN of the book to be removed :");
            String updateIsbn = scanner.next();
            String fetchbook = "select * from books Where isbn=?";
            PreparedStatement pst = connection.prepareStatement(fetchbook);
            pst.setString(1, updateIsbn);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                double price = rs.getDouble("price"); // Price (Fifth column)
                int stock_quantity = rs.getInt(6); // Quantity (Sixth column)

                System.out.println("Enter new price to update else press Enter to skip: ");
                String newbookPriceInput = scanner.nextLine();
                System.out.println("Enter new quantity to update else enter 0: ");
                int newbookQuantity = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                if (newbookPriceInput.equals("")) {
                    // Keep the existing price if the input is empty
                } else {
                    try {
                        double bookPrice = Double.parseDouble(newbookPriceInput);
                        price = bookPrice;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price format. Keeping the existing price.");
                    }
                }

                if (newbookQuantity == 0) {
                    newbookQuantity = stock_quantity;
                } else {
                    stock_quantity = newbookQuantity;
                }

                String query = "UPDATE books SET  price = ?, stock_quantity = ? WHERE isbn = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {

                    pstmt.setDouble(1, price);
                    pstmt.setInt(2, stock_quantity);
                    pstmt.setString(3, updateIsbn);
                    int r1 = pstmt.executeUpdate();
                    if (r1 > 0) {
                        System.out.println("Book updated successfully.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid ISBN.");
            }
        } else {
            System.out.println("No book updated"); // Changed to print a message instead of returning null

        }
    }

    // Method to remove a book from the inventory by ISBN
    public void removeBookByISBN() throws SQLException {
        displayAllBooks();
        System.out.print("Do you want to Remove any Book (yes/no) :");
        String ans = scanner.next();
        if (ans.equalsIgnoreCase("yes")) {
            System.out.print("Enter ISBN of the book to be removed :");
            String isbn = scanner.next();
            String query = "DELETE FROM books WHERE isbn = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("No book removed");
            System.out.println("Back to Admin Menu");
        }
    }

    // Method to display books by genre
    // Method to display books by genre in a formatted manner
    public void displayBooksByGenre() {
        System.out.println("""
                    Search By Genre
                --------------------------------
                    1  --> Fiction
                    2  --> Non-Fiction
                    3  --> Novel
                    4  --> Fantasy
                    5  --> Mystery
                    6  --> Biography
                    7  --> History
                    8  --> Romance
                    9  --> Back to Admin Menu
                --------------------------------

                    """);

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        String genre = "";

        switch (choice) {
            case 1:
                genre = "Fiction";
                break;
            case 2:
                genre = "Non-Fiction";
                break;
            case 3:
                genre = "Science Fiction";
                break;
            case 4:
                genre = "Fantasy";
                break;
            case 5:
                genre = "Mystery";
                break;
            case 6:
                genre = "Biography";
                break;
            case 7:
                genre = "History";
                break;
            case 8:
                genre = "Romance";
                break;
            case 9:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice. Exiting...");
                return;
        }

        String query = "SELECT * FROM books WHERE genre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, genre);
            ResultSet rs = pstmt.executeQuery();

            // Print table header
            System.out.printf("%-15s %-30s %-20s %-15s %-10s %-6s%n", "ISBN", "Title", "Author", "Genre", "Price",
                    "Stock");
            System.out.println("------------------------------------------------------------");

            // Print each book's details
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String bookGenre = rs.getString("genre");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock_quantity");

                System.out.printf("%-15s %-30s %-20s %-15s $%-9.2f %d%n", isbn, title, author, bookGenre, price, stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to manage VIP passes
    public void manageVIPPass() throws SQLException {

        boolean flag = true;
        int choice;
        while (flag) {
            System.out.println("""
                                Manage VIP Pass
                    -----------------------------------------
                        1 --> Add VIP Pass
                        2 --> Remove VIP Pass
                        3 --> Display VIP Pass
                        4 --> Back to admin menu
                    -----------------------------------------

                        """);
            System.out.print("Enter your choice :");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addVIPPass();
                    break;
                case 2:
                    removeVIPPass();
                    break;
                case 3:
                    displayAllVIPUsers();
                    break;
                case 4:
                    flag = false;
                    return;
                default:
                    System.out.println("Enter Proper Choice");
            }
        }
    }

    // Method to add a VIP pass to a user
    public void addVIPPass() throws SQLException {

        displayAllUsers();
        System.out.print("Do you want to Remove any Book (yes/no) :");
        String ans = scanner.next();
        if (ans.equalsIgnoreCase("yes")) {
        System.out.print("Enter username :");
        String username = scanner.next();
        String query = "UPDATE users SET vip_pass = TRUE WHERE username = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, username);
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("VIP pass added successfully.");
            return;
        } else {
            System.out.println("User not found or VIP pass already exists.");
        }}else{
            System.out.println("No VIP pass adeed");
        }
    }

    // Method to remove a VIP pass from a user
    public void removeVIPPass() {
        displayAllVIPUsers();
        System.out.print("Do you want to Remove any Book (yes/no) :");
        String ans = scanner.next();
        if (ans.equalsIgnoreCase("yes")) {
            System.out.print("Enter username :");
            String username = scanner.next();
            String query = "UPDATE users SET vip_pass = FALSE WHERE username = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("VIP pass removed successfully.");
                    return;
                } else {
                    System.out.println("User not found or VIP pass does not exist.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Pass Removed");
        }
    }

    // Method to display all users with VIP passes
    public void displayAllVIPUsers() {
        String query = "SELECT * FROM users WHERE vip_pass = TRUE";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            // Print table header
            System.out.printf("%-10s %-20s %-25s %-25s %-15s %-30s %-10s%n",
                    "User ID", "Username", "Full Name", "Email", "Phone", "Address", "VIP Pass");
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------");

            // Print each VIP user's details
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String fullName = rs.getString("userFullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                String address = rs.getString("address");
                boolean vipPass = rs.getBoolean("vip_pass");

                System.out.printf("%-10d %-20s %-25s %-25s %-15s %-30s %-10s%n",
                        userId, username, fullName, email, phone, address, vipPass ? "Yes" : "No");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to remove a user
    public void removeUser() throws SQLException {
        System.out.print("Enter username :");
        String username = scanner.next();
        String query = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            System.out.println("User removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display all users
    public void displayAllUsers() {
        String query = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            // Print table header
            System.out.printf("%-5s %-10s %-25s %-20s %-15s %-30s %-10s%n",
                    "ID", "Username", "Full Name", "Email", "Phone", "Address", "VIP Pass");
            System.out.println(
                    "----------------------------------------------------------------------------------------------------");

            // Print each user's details
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String fullName = rs.getString("userFullname");
                String email = rs.getString("email");
                String phone = rs.getString("phone_number");
                String address = rs.getString("address");
                boolean vipPass = rs.getBoolean("vip_pass");
                System.out.printf("%-5d %-10s %-25s %-20s %-15s %-30s %-10s%n",
                        userId, username, fullName, email, phone, address, vipPass ? "Yes" : "No");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view sales history
    public void viewSalesHistory() throws SQLException {
        String query = "SELECT * FROM orders";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            // Print table header
            System.out.println("+---------+---------+--------------+---------------------+-----------+");
            System.out.printf("| %-7s | %-7s | %-12s | %-19s | %-9s |\n", "Order ID", "User ID", "Total Amount",
                    "Order Date", "Status");
            System.out.println("+---------+---------+--------------+---------------------+-----------+");

            // Print each row in the result set
            while (rs.next()) {
                System.out.printf("| %-7d | %-7d | $%-11.2f | %-19s | %-9s |\n",
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("total_amount"),
                        rs.getTimestamp("order_date"),
                        rs.getString("status"));
            }

            // Print table footer
            System.out.println("+---------+---------+--------------+---------------------+-----------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // public void viewSalesHistory() throws SQLException {
    // String query = "SELECT * FROM orders";
    // try (Statement stmt = connection.createStatement()) {
    // ResultSet rs = stmt.executeQuery(query);
    // while (rs.next()) {
    // System.out.println("Order ID: " + rs.getInt("order_id"));
    // System.out.println("User ID: " + rs.getInt("user_id"));
    // System.out.println("Total Amount: $" + rs.getDouble("total_amount"));
    // System.out.println("Order Date: " + rs.getTimestamp("order_date"));
    // System.out.println("Status: " + rs.getString("status"));
    // System.out.println("-----");
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    // Method to update admin details
    // public void updateAdminDetails() throws SQLException {
    // System.out.print("Enter Admin Username :");
    // String adminUsername = scanner.nextLine();
    // System.out.print("Enter New Password :");
    // String newPassword = scanner.nextLine();
    // String query = "UPDATE admin SET password = ? WHERE username = ?";
    // try (PreparedStatement pstmt = connection.prepareStatement(query)) {
    // pstmt.setString(1, newPassword);
    // pstmt.setString(2, adminUsername);
    // pstmt.executeUpdate();
    // System.out.println("Admin details updated.");
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    // Method to view all admins
    public void viewAllAdmins() throws SQLException {

        String query = "SELECT * FROM admin";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("Admin Username: " + rs.getString("username"));
                // Admin details
                System.out.println("-----");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display all books in a formatted manner
    public void displayAllBooks() {
        String query = "SELECT * FROM books";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            // Print table header
            System.out.printf("%-15s %-30s %-20s %-15s %-10s %-6s%n", "ISBN", "Title", "Author", "Genre", "Price",
                    "stock");
            System.out.println("------------------------------------------------------------");

            // Print each book's details
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock_quantity");

                System.out.printf("%-15s %-30s %-20s %-15s $%-9.2f %d%n", isbn, title, author, genre, price, stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
