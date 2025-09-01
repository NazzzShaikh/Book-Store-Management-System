package BookStoreManagment;

import DBConnect.DBConnection;
import Functions.LoginSignup;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BookStoreSystem {
    static Connection con;

    public static void main(String[] args) throws Exception {
        DBConnection db = new DBConnection();
        Scanner scanner = new Scanner(System.in);
        Connection con = db.connect();
        //System.out.println("heyy");
        if (con != null) {
            System.out.println("Connected to the database");
            welcomeMsgPrint();
            boolean flag = true;
            while (flag) {
                try {
                    displayMainMenu();
                    int roleChoice = scanner.nextInt();
                    LoginSignup obLoginSignup = new LoginSignup(con);
                    switch (roleChoice) {
                        case 1 -> // Admin Login
                            obLoginSignup.adminLogin();

                        case 2 -> // User Login/Signup
                            obLoginSignup.userInteraction();
                        case 3 -> {
                            // Exit
                            System.out.println("Exiting the system...");
                            System.out.println("THANK YOU FOR USING SYSTEM");
                            flag = false;
                            return;
                        }
                        default -> System.out.println("Invalid choice! Please choose either Admin or User.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("An error occurred: invalid Input. Please enter a number.");
                    scanner.next(); // Consume the invalid input to avoid an infinite loop
                }
            }
        } else {
            System.out.println("Not connected to the database.");
        }
    }

    private static void welcomeMsgPrint() {
        System.out.println("*********************************************************");
        System.out.println("*                                                       *");
        System.out.println("*   ╔═══════════════════════════════════════════════╗   *");
        System.out.println("*   ║                                               ║   *");
        System.out.println("*   ║      Welcome to the Book Store System!        ║   *");
        System.out.println("*   ║                                               ║   *");
        System.out.println("*   ╚═══════════════════════════════════════════════╝   *");
        System.out.println("*                                                       *");
        System.out.println("*********************************************************");
        System.out.println();
        System.out.println("Explore our vast collection of books and manage your Books with ease.");
        System.out.println("Please select an option from the menu to get started.");
        System.out.println();
    }

    private static void displayMainMenu() {

        System.out.println("""

                -----------------------------------------
                    Are you a user or an admin?
                    1 --> Admin
                    2 --> User
                    3 --> Exit
                -----------------------------------------
                        """);
        System.out.print("Enter your choice: ");
    }

}