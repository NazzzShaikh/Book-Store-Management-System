package Functions;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class LoginSignup {
    static Connection con;

    public LoginSignup(Connection con) {
        this.con = con;
    }

    public void adminLogin() throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Admin Username: ");
        String adminUsername = scanner.next();
        System.out.print("Enter Admin Password: ");
        String adminPassword = scanner.next();
        if (authenticate("admin", adminUsername, adminPassword)) {
            AdminFunctions admin = new AdminFunctions(con);
            admin.adminMenu();

        } else {
            System.out.println("Invalid Admin credentials. Please try again.");
        }
    }

    private static boolean authenticate(String role, String username, String password) throws SQLException {
        String table = role.equalsIgnoreCase("admin") ? "admin" : "users";
        String sql = "SELECT password FROM " + table + " WHERE username = ?";

        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String storedPassword = rs.getString("password");
            if (password.equals(storedPassword)) {
                return true; // Passwords match
            } else {
                System.out.println("Incorrect password. Please try again.");
                return false; // Passwords don't match
            }
        } else {
            System.out.println("Username not found.");
            return false; // Username not found
        }
    }

    public void userInteraction() throws SQLException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("""

                    -----------------------------------------
                    1 --> Login to existing account
                    2 --> Sign up for a new account
                    3 --> Back to Main Menu
                    -----------------------------------------
                        """);
            System.out.print("Enter your choice: ");
            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    userLogin();
                    break;

                case 2:
                    userSignUp();
                    break;
                case 3:
                    System.out.println("return to Main menu");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("An error occurred: invalid Input ");
        }

    }

    public static void userLogin() throws SQLException ,IOException{
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (authenticate("user", username, password)) {
            // System.out.println("USER MENU");
            UserFunctionsInterface user = new UserFunctions(con);
            user.userMenu(username);
        } else {
            System.out.println("Invalid user credentials. Please try again.");
        }
    }

    // Method to sign up the user
    private static void signUp(String username, String password, String name, String email, String phone_number,
            String address, int isVip_pass) throws SQLException {
        String sql = "INSERT INTO users (username, password,userFullname,email,phone_number,address,vip_pass) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, name);
        pstmt.setString(4, email);
        pstmt.setString(5, phone_number);
        pstmt.setString(6, address);
        pstmt.setInt(7, isVip_pass);
        int r = pstmt.executeUpdate();
        if (r > 0) {
            System.out.println("User account created successfully.");
        }
    }

    public static void userSignUp() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String newName;
        do {
            System.out.print("Enter Your Name (alphabets only): ");
            newName = scanner.nextLine();
        } while (!isValidName(newName));

        String newUsername;
        System.out.print("Enter a new username: ");
        newUsername = scanner.next();

        String newPassword;
        do {
            System.out.print("Enter a new password (at least 8 characters, including a special character): ");
            newPassword = scanner.next();
        } while (!isValidPassword(newPassword));

        scanner.nextLine(); // Consume newline

        String newEmail;
        do {
            System.out.print("Enter Your Email (e.g., name@gmail.com): ");
            newEmail = scanner.next();
        } while (!isValidEmail(newEmail));

        String newPhNo;
        do {
            System.out.print("Enter Your Phone Number (10 digits): ");
            newPhNo = scanner.next();
        } while (!isValidPhoneNumber(newPhNo));

        scanner.nextLine(); // Consume newline

        System.out.print("Enter Your Address: ");
        String newAddress = scanner.nextLine();

        System.out.println("Do you want to apply for Vip pass ? (yes/no)");
        String ipVip_pass = scanner.nextLine();
        int isVip_pass = 0;
        if (ipVip_pass.equalsIgnoreCase("yes")) {
            isVip_pass = 1;
        }
        signUp(newUsername, newPassword, newName, newEmail, newPhNo, newAddress, isVip_pass);
        System.out.println("Account created successfully. Please log in with your new credentials.");
    }

    // Validate Name: Should only contain alphabets and spaces
    private static boolean isValidName(String name) {
        String namePattern = "^[a-zA-Z\\s]+$";
        if (!name.matches(namePattern)) {
            System.out.println("Invalid name. It should only contain alphabets and spaces.");
            return false;
        }
        return true;
    }

    // Validate Password: At least 8 characters, 1 special character
    private static boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
        if (!password.matches(passwordPattern)) {
            System.out.println(
                    "Invalid password. It must be at least 8 characters long and contain at least one special character.");
            return false;
        }
        return true;
    }

    // Validate Email: Must contain @ and domain
    private static boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(emailPattern)) {
            System.out.println("Invalid email. It must be in the format name@domain.com.");
            return false;
        }
        return true;
    }

    // Validate Phone Number: Exactly 10 digits
    private static boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "^\\d{10}$";
        if (!phoneNumber.matches(phonePattern)) {
            System.out.println("Invalid phone number. It must be exactly 10 digits long.");
            return false;
        }
        return true;
    }
}
