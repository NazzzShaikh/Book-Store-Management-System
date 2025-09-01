package Functions;

import DataStructure.LinkedListForCart;
import Model.Book;
import Model.Cart;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserFunctions implements UserFunctionsInterface {
    private static Connection connection;
    private static Scanner scanner;
    LinkedListForCart cartLL;
    int userId;

    public UserFunctions(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);

    }

    // Method to add a book to the cart
    public void addToCart(String username) throws SQLException {
        displayAllBooks();

        // Retrieve user ID based on username
        String sql1 = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pstmt1 = connection.prepareStatement(sql1);
        pstmt1.setString(1, username);
        ResultSet rs1 = pstmt1.executeQuery();

        // Default value in case no user is found
        if (rs1.next()) {
            userId = rs1.getInt("user_id");
        }

        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        // Retrieve book details
        String sql2 = "SELECT * FROM books WHERE book_id = ?";
        PreparedStatement pstmt2 = connection.prepareStatement(sql2);
        pstmt2.setInt(1, bookId);
        ResultSet rs2 = pstmt2.executeQuery();

        double price = 0;
        String bookName = "";
        if (rs2.next()) {
            price = rs2.getDouble("price");
            bookName = rs2.getString("title");
        }

        // Insert into cart
        String query = "INSERT INTO cart (user_id, username, book_id, bookname, quantity, price) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt3 = connection.prepareStatement(query);
        pstmt3.setInt(1, userId);
        pstmt3.setString(2, username);
        pstmt3.setInt(3, bookId);
        pstmt3.setString(4, bookName);
        pstmt3.setInt(5, quantity);
        pstmt3.setDouble(6, (quantity * price));

        int result = pstmt3.executeUpdate();

        if (result > 0) {
            Cart userCart = new Cart(userId, username, bookId, bookName, quantity, price * quantity);
            cartLL.addBookToCart(userCart);
            System.out.println("Book added to cart successfully.");
        } else {
            System.out.println("Failed to add book to cart.");
        }
    }

    // @Override
    // public void addToCart(String username) throws SQLException {
    // displayAllBooks();
    // // System.out.print("Enter User ID: ");
    // // int userId = cartLL.cart.userId;
    // String sql1 = "SELECT * FROM cart WHERE username = ? ";
    // PreparedStatement pstmt = connection.prepareStatement(sql1);
    // pstmt.setString(1, username);
    // ResultSet rs1 = pstmt.executeQuery();
    // if (rs1 != null) {
    // rs1.next();
    // // int cartId = rs.getInt("cart_id");
    // userId = rs1.getInt("user_id");

    // }
    // System.out.print("Enter Book ID: ");
    // int bookId = scanner.nextInt();
    // System.out.print("Enter Quantity: ");
    // int quantity = scanner.nextInt();
    // // System.out.println("hey1");
    // String sql = "select * from books where book_id = ?";
    // PreparedStatement pst = connection.prepareStatement(sql);
    // pst.setInt(1, bookId);
    // ResultSet rs = pst.executeQuery();
    // rs.next();
    // double price = rs.getDouble("price");
    // String bookName = rs.getString("title");
    // // insert into cart
    // // System.out.println("hey2");
    // String query = "INSERT INTO cart (user_id,username, book_id,bookname,quantity
    // , price ) VALUES (?,?,?, ?,?,?)";
    // PreparedStatement pstmt2 = connection.prepareStatement(query);
    // pstmt2.setInt(1, userId);
    // pstmt2.setString(2, username);
    // pstmt2.setInt(3, bookId);
    // pstmt2.setString(4, bookName);
    // pstmt2.setInt(5, quantity);
    // pstmt2.setDouble(6, (quantity * price));
    // int r = pstmt.executeUpdate();
    // if (r > 0) {
    // Cart userCart = new Cart(userId, username, bookId, bookName, quantity, price
    // * quantity);
    // cartLL.addBookToCart(userCart);
    // System.out.println("Book added to cart successfully.");
    // } else {
    // System.out.println("Failed to add book to cart.");
    // }

    // }
    public void removeBookFromCart(String username) throws SQLException, InputMismatchException {
        // Display cart details and check if there are items in the cart
        boolean hasItems = displayCartDetails(username);

        // Check if the linked list representing the cart is empty
        if (cartLL.head == null) {
            System.out.println("No items found in the cart. Please add items to the cart first.");
            return; // Exit the method as there are no items to remove
        }

        System.out.print("Do you want to remove a book from the cart (yes/no): ");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("yes")) {
            while (true) {
                try {
                    System.out.print("Enter the cart ID from the above list that you want to remove: ");
                    int removeCartID = scanner.nextInt();

                    // Remove the book from the linked list (or other data structure)
                    boolean removedFromList = cartLL.removeBookFromCart(removeCartID);
                    if (!removedFromList) {
                        System.out.println("Cart ID not found in the list. Please enter a valid cart ID.");
                        continue; // Prompt user again
                    }

                    // Prepare and execute the SQL query to remove the book from the cart in the
                    // database
                    String sql = "DELETE FROM cart WHERE username = ? AND cart_id = ?";
                    try (PreparedStatement pst = connection.prepareStatement(sql)) {
                        pst.setString(1, username);
                        pst.setInt(2, removeCartID);

                        int rowsAffected = pst.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Book removed from cart successfully.");
                            break; // Exit the loop if the removal was successful
                        } else {
                            System.out.println("Failed to remove book from cart. Please try again.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("An error occurred while removing the book. Please try again.");
                    e.printStackTrace();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid cart ID.");
                    scanner.next(); // Clear the invalid input
                } catch (NullPointerException e) {
                    System.out.println("A null pointer exception occurred. Please check the implementation.");
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("You have chosen not to remove any book from the cart.");
        }
    }

    // public void removeBookFromCart(String username) throws SQLException {
    // boolean flag = displayCartDetails(username);

    // if (flag) {
    // System.out.print("Do You Want to remove Book From cart (yes/no) :");
    // String choice = scanner.next();
    // if(choice.equalsIgnoreCase("yes")){
    // while (true) {
    // try {
    // System.out.println("Enter cartid from above cart list which you want to
    // remove from cart :- ");
    // int removeCartID = scanner.nextInt();
    // cartLL.removeBookFromCart(removeCartID);
    // String sql = "Delete From cart where username=? and cart_id=?";
    // PreparedStatement pst = connection.prepareStatement(sql);
    // pst.setString(1, username);
    // pst.setInt(2, removeCartID);
    // int r = pst.executeUpdate();
    // if (r > 0) {
    // System.out.println("Book removed from cart successfully");
    // break;
    // } else {
    // System.out.println("Failed to remove book from cart");
    // }
    // } catch (Exception e) {
    // System.out.println("Please enter valid Cart id.");
    // }
    // }
    // }else{
    // System.out.println("You have not added any book to cart");
    // }else {
    // System.out.println("First add products in cart.....");
    // }
    // }

    // create bill ,bill banse
    static String createbill_for_billing(String username, LinkedListForCart cartLL) throws SQLException, IOException {

        Date d = new Date();

        Date currentDate = d;

        double totalAmount = 0;
        if (cartLL.head == null) {
            System.out.println("NO ITEMS IN CART!");
            return "";

        } else {
            FileWriter fw = new FileWriter(username + "ShopingBill.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            LinkedListForCart.Node temp = cartLL.head;

            Date date = new Date();

            bw.write("-------------------ONLINE DIGITAL MALL BILL---------------------------");
            bw.newLine();
            bw.newLine();
            bw.write("----------------------------" + username + "----------------------------------");
            bw.newLine();
            bw.newLine();
            bw.write("                                                                  " + date);
            bw.newLine();
            bw.newLine();
            int userId = 0;
            while (temp != null) {
                int pid = temp.userCart.getBook_id();
                String pname = temp.userCart.getBookName();
                int quantity = temp.userCart.getQuantity();
                double price = temp.userCart.getPrice();
                userId = temp.userCart.getUser_id();

                System.out.println("Book Id:- " + pid);
                System.out.println("Book Name:-" + pname);
                System.out.println("Book Quantity:-" + quantity);
                System.out.println("Book Price:-" + price);

                bw.write("Book Id:- " + pid + "  ");
                bw.write("Book Name:-" + pname + "  ");
                bw.write("Book Quantity:-" + quantity + "  ");
                bw.write("Book Price:-" + price);
                bw.newLine();

                totalAmount = totalAmount + price;
                temp = temp.next;

            }

            System.out.println("-------------------------------------------------------------------");
            System.out.println("|                                                                  |");
            System.out.println("|TOTAL AMOUNT OF YOUR BILL IS :- " + totalAmount + "               |");
            System.out.println("|                                                                  |");
            System.out.println("--------------------------------------------------------------------");

            String paymentMethod = "";
            System.out.println("--------------------------------------------------------");
            System.out.println("|choose payment method:                                |");
            System.out.println("| 1. for Cash On Delivery:                             |");
            System.out.println("| 2. for UPI:                                          |");
            System.out.println("| 3. for Debit Card:                                   |");
            System.out.println("--------------------------------------------------------");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:

                    paymentMethod = "PAYMENT BY CASH ON DELIVERY.";
                    System.out.println("Order successfully done.. \n Payment by Cash On Delivery...");
                    LinkedListForCart.Node temp1 = cartLL.head;
                    while (temp1 != null) {
                        String pname = temp1.userCart.getBookName();
                        int quantity = temp1.userCart.getQuantity();
                        double price = temp1.userCart.getPrice();
                        int bookid = temp1.userCart.getBook_id();

                        String sql = "INSERT INTO `orders`(`user_id`, `total_amount`,`status`) values(?,?, 'paid')";

                        PreparedStatement pst = connection.prepareStatement(sql);
                        pst.setInt(1, userId);
                        pst.setDouble(2, price);
                        pst.execute();
                        String sql2 = "{call updateQuantity(?,?)}";
                        pst = connection.prepareCall(sql2);
                        pst.setInt(1, (quantity));
                        pst.setInt(2, bookid);
                        pst.executeUpdate();
                        temp1 = temp1.next;
                        String payinsert = "INSERT INTO `payments`( `user_id`, `username`, `amount`, `payment_method`, `status`) Values (?,?,?,?,?) ";
                        pst = connection.prepareStatement(payinsert);
                        pst.setInt(1, userId);
                        pst.setString(2, username);
                        pst.setDouble(3, price);
                        pst.setString(4, "Cash on Delivery");
                        pst.setString(5, "Paid");
                        pst.executeUpdate();
                        String emptyCart = "Delete From Cart where user_id=?";
                        pst = connection.prepareStatement(emptyCart);
                        pst.setInt(1, userId);
                        pst.executeUpdate();
                    }
                    break;

                case 2:{
                    //viewBills(username);
                    paymentMethod = "PAYMENT BY UPI.";
                    System.out.println("ENTER UPI ID:");
                    String upi = scanner.next();

                    boolean p = true;
                    while (p) {
                    
                        
                            try {
                                System.out.println("Payment is procesing.");
                                Thread.sleep(5000);
                                System.out.println("Payment Successfully completed.... Thank you!");
                            } catch (Exception e) {
                                System.out.println("Payment is failed!");
                            }

                            LinkedListForCart.Node temp2 = cartLL.head;
                            while (temp2 != null) {
                                String pname = temp2.userCart.getBookName();
                                int quantity = temp2.userCart.getQuantity();
                                double price = temp2.userCart.getPrice();
                                int bookid = temp2.userCart.getBook_id();

                                String sql = "INSERT INTO `orders`(`user_id`, `total_amount`,`status`) values(?,?, 'paid')";

                                PreparedStatement pst = connection.prepareStatement(sql);
                                pst.setInt(1, userId);
                                pst.setDouble(2, price);
                                pst.execute();

                                String sql2 = "{call updateQuantity(?,?)}";
                                pst = connection.prepareCall(sql2);
                                pst.setInt(1, (quantity));
                                pst.setInt(2, bookid);
                                pst.executeUpdate();
                                temp2 = temp2.next;
                                String payinsert = "INSERT INTO `payments`( `user_id`, `username`, `amount`, `payment_method`, `status`) Values (?,?,?,?,?) ";
                                pst = connection.prepareStatement(payinsert);
                                pst.setInt(1, userId);
                                pst.setString(2, username);
                                pst.setDouble(3, price);
                                pst.setString(4, "By UPI");
                                pst.setString(5, "Paid");
                                pst.executeUpdate();
                                String emptyCart = "Delete From Cart where user_id=?";
                                pst = connection.prepareStatement(emptyCart);
                                pst.setInt(1, userId);
                                pst.executeUpdate();

                            }
                            p = false;
                        } 
                    }
                    break;

                case 3:

                    paymentMethod = "PAYMENT BY DEBIT CARD.";
                    boolean flag = true;
                    while (flag) {
                        int flag1 = 0;
                        System.out.println("ENTER DEBITCARD NUMBER:");
                        String accountNumber = scanner.nextLine();
                        if (String.valueOf(accountNumber).length() == 16) {
                            for (int i = 0; i < 16; i++) {
                                if ((String.valueOf(accountNumber).charAt(i) >= '0')
                                        && (String.valueOf(accountNumber).charAt(i) <= '9')) {
                                    flag1++;
                                }
                            }

                            if (flag1 == 16) {
                                flag = false;

                                boolean p1 = true;
                                while (p1) {
                                    System.out.println("ENTER AMOUNT:");
                                    double amount = scanner.nextDouble();

                                    if (amount == totalAmount) {
                                        try {
                                            System.out.println("Payment is procesing.");
                                            Thread.sleep(2000);
                                            System.out.println("Payment Successfully completed.... Thank you!");
                                        } catch (Exception e) {
                                            System.out.println("Payment is failed!");
                                        }

                                        LinkedListForCart.Node temp3 = cartLL.head;
                                        while (temp3 != null) {
                                            String pname = temp3.userCart.getBookName();
                                            int quantity = temp3.userCart.getQuantity();
                                            double price = temp3.userCart.getPrice();
                                            int bookid = temp3.userCart.getBook_id();

                                            String sql = "INSERT INTO `orders`(`user_id`, `total_amount`,`status`) values(?,?, 'paid')";

                                            PreparedStatement pst = connection.prepareStatement(sql);
                                            pst.setInt(1, userId);
                                            pst.setDouble(2, price);
                                            pst.execute();

                                            String sql2 = "{call updateQuantity(?,?)}";
                                            pst = connection.prepareCall(sql2);
                                            pst.setInt(1, (quantity));
                                            pst.setInt(2, bookid);
                                            pst.executeUpdate();
                                            temp3 = temp3.next;
                                            String payinsert = "INSERT INTO `payments`( `user_id`, `username`, `amount`, `payment_method`, `status`) Values (?,?,?,?,?) ";
                                            pst = connection.prepareStatement(payinsert);
                                            pst.setInt(1, userId);
                                            pst.setString(2, username);
                                            pst.setDouble(3, price);
                                            pst.setString(4, "Debit Card");
                                            pst.setString(5, "Paid");
                                            pst.executeUpdate();
                                            String emptyCart = "Delete From Cart where user_id=?";
                                            pst = connection.prepareStatement(emptyCart);
                                            pst.setInt(1, userId);
                                            pst.executeUpdate();

                                        }
                                        p1 = false;
                                    } else {
                                        System.out.println(
                                                "THE PAYMENT OF AN INVOICE THAT IS LESS THAN FULL AMOUNT DUE...");
                                    }
                                }
                            } else {
                                System.out.println("debitcard number should be only digits!");
                            }
                        } else {
                            System.out.println("###---debitcard number should be 16 digits!---###");
                        }
                    }

                    break;

                default:
                    System.out.println("Select invalid payment method");
            }

            bw.write("--------------------------------------------------------");
            bw.newLine();
            bw.write("|                                                      |");
            bw.newLine();
            bw.write("|TOTAL AMOUNT OF YOUR BILL IS :- " + totalAmount + "                |");
            bw.newLine();
            bw.write("|                                                      |");
            bw.newLine();
            bw.write("| Payment method : " + paymentMethod + "                                    |");
            bw.newLine();
            bw.write("--------------------------------------------------------");
            bw.newLine();

            bw.close();
            fw.close();

            System.out.println("You want to continue your shoping (yes/no) :");
            String ans = scanner.next();
            cartLL.head = null;
            if (ans.equalsIgnoreCase("no")) {
                return "no";
            } else {
                return "yes";
            }
        }

    }

    // Method to make a payment
    @Override
    public void makePayment(String username) {
        // System.out.print("Enter User ID: ");
        // int userId = scanner.nextInt();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter Payment Method: ");
        scanner.nextLine(); // Consume newline
        String paymentMethod = scanner.nextLine();
        String query = "INSERT INTO payments (user_id, amount, payment_method, status) VALUES (?, ?, ?, 'Completed')";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, paymentMethod);
            pstmt.executeUpdate();
            System.out.println("Payment made successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view user's bills
    // public static void viewBills(String username) {
    //     String query = "SELECT payment_id, user_id, amount, payment_date, payment_method, username FROM payments WHERE username = ?";
    //     try (PreparedStatement pstmt = connection.prepareStatement(query)) {
    //         pstmt.setString(1, username);
    //         ResultSet rs = pstmt.executeQuery();

    //         // Print table header, including the "Username" column
    //         System.out.printf("%-10s %-10s %-10s %-15s %-15s %-20s%n", "Payment ID", "User ID", "Username", "Amount",
    //                 "Payment Date", "Payment Method");
    //         System.out.println("--------------------------------------------------------------------------");

    //         while (rs.next()) {
    //             int paymentId = rs.getInt("payment_id");
    //             int userId = rs.getInt("user_id");
    //             String retrievedUsername = rs.getString("username");
    //             double amount = rs.getDouble("amount");
    //             Timestamp paymentDate = rs.getTimestamp("payment_date");
    //             String paymentMethod = rs.getString("payment_method");

    //             System.out.printf("%-10d %-10d %-10s Rs.%-14.2f %-15s %-20s%n", paymentId, userId, retrievedUsername,
    //                     amount, paymentDate.toLocalDateTime(), paymentMethod);
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    // Method to view the user's cart

    @Override // 123
    public void viewCart(String username) {
        String query = "SELECT b.isbn, b.title, b.author, b.price, c.quantity " +
                "FROM cart c JOIN books b ON c.book_id = b.book_id " +
                "WHERE c.username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // Print table header
            System.out.printf("%-15s %-30s %-20s %-10s %-10s%n", "ISBN", "Title", "Author", "Price", "Quantity");
            System.out.println("--------------------------------------------------------------");

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                System.out.printf("%-15s %-30s %-20s Rs.%-9.2f %-10d%n", isbn, title, author, price, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // @Override
    // public void viewCart(String username){
    // cartLL.displayCart();
    // }
    public List<Cart> fetchCartDetailsByUsername(String username) throws SQLException {
        List<Cart> cartList = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int cartId = rs.getInt("cart_id");
                    int userId = rs.getInt("user_id");
                    int bookId = rs.getInt("book_id");
                    String bookName = rs.getString("bookname");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price"); // Adjust based on your schema
                    java.sql.Timestamp addedAt = rs.getTimestamp("added_at");
                    // Create a new Cart object and add it to the list
                    Cart cart = new Cart(cartId, userId, username, bookId, bookName, quantity, price, addedAt);
                    cartList.add(cart);
                }
            }
        }
        return cartList;
    }

    public boolean displayCartDetails(String username) throws SQLException {
        String sql = "SELECT * FROM cart WHERE username = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-10s %-15s %-10s %-10s %-10s %-20s%n",
                "Cart ID", "User ID", "Username", "Book ID", "Quantity", "Price", "Added At");
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------");
        if (rs != null) {
            while (rs.next()) {
                int cartId = rs.getInt("cart_id");
                int userId = rs.getInt("user_id");
                int bookId = rs.getInt("book_id");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price"); // Adjust based on your schema
                Timestamp addedAt = rs.getTimestamp("added_at");
                // Create a new Cart object and add it to the list
                // Cart cart = new Cart(cartId, userId, username, bookId, quantity,price,
                // addedAt);
                // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                System.out.printf("%-10d %-10d %-15s %-10d %-10d Rs.%-9.2f %-20s%n",
                        cartId, userId, username, bookId, quantity,
                        price, addedAt);
            }
            return true;
        } else {
            System.out.println("No cart found for the user");
            return false;
        }
    }

    // Method to search for a book by ISBN
    @Override
    public void searchBookByIsbn(String username) {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.next();

        String query = "SELECT * FROM books WHERE isbn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            // Print table header
            System.out.printf("%-15s %-30s %-20s %-15s %-10s %-6s%n", "ISBN", "Title", "Author", "Genre", "Price",
                    "stock");
            System.out.println("------------------------------------------------------------");

            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock_quantity");

                System.out.printf("%-15s %-30s %-20s %-15s Rs.%-9.2f %d%n", isbn, title, author, genre, price, stock);
            } else {
                System.out.println("No book found with ISBN: " + isbn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view books by genre
    @Override
    public void viewBooksByGenre(String username) {
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

                System.out.printf("%-15s %-30s %-20s %-15s Rs.%-9.2f %d%n", isbn, title, author, bookGenre, price,
                        stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // public void viewBooksByGenre(String username) {
    // System.out.print("Enter Genre: ");
    // String genre = scanner.next();

    // String query = "SELECT * FROM books WHERE genre = ?";
    // try (PreparedStatement pstmt = connection.prepareStatement(query)) {
    // pstmt.setString(1, genre);
    // ResultSet rs = pstmt.executeQuery();

    // // Print table header
    // System.out.printf("%-15s %-30s %-20s %-15s %-10s %-6s%n", "ISBN", "Title",
    // "Author", "Genre", "Price",
    // "stock");
    // System.out.println("------------------------------------------------------------");

    // while (rs.next()) {
    // String isbn = rs.getString("isbn");
    // String title = rs.getString("title");
    // String author = rs.getString("author");
    // String bookGenre = rs.getString("genre");
    // double price = rs.getDouble("price");
    // int stock = rs.getInt("stock_quantity");

    // System.out.printf("%-15s %-30s %-20s %-15s $%-9.2f %d%n", isbn, title,
    // author, bookGenre, price, stock);
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    // Method to give a rating to a book
    @Override
    public void giveRatingToBook() {
        System.out.print("Enter Book ISBN: ");
        String isbn = scanner.next();
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Rating (1 to 5): ");
        int rating = scanner.nextInt();

        String query = "INSERT INTO book_ratings (isbn, user_id, rating) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE rating = VALUES(rating)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, isbn);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, rating);
            pstmt.executeUpdate();
            System.out.println("Rating submitted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to handle the user menu
    @Override
    public void userMenu(String username) throws SQLException, IOException {
        cartLL = new LinkedListForCart(connection, username);
        cartLL.addPreviouCart();
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("-----------------------------------------");
            System.out.println("1 --> Add to Cart");
            System.out.println("2 --> Make Payment");
            System.out.println("3 --> View Bills");
            System.out.println("4 --> View Cart");
            System.out.println("5 --> remove book from cart");
            System.out.println("6 --> Search Book by ISBN");
            System.out.println("7 --> View Books by Rating");
            System.out.println("8 --> Display Books Menu ");
            System.out.println("9 --> Display All Books");
            System.out.println("10 --> Exit");
            System.out.println("-----------------------------------------");
            System.out.println();

            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addToCart(username);
                    break;
                case 2:
                    createbill_for_billing(username, cartLL);

                    // makePayment(username);
                    break;
                case 3:
                    //viewBills(username);
                    break;
                case 4:
                    // viewCart(username);
                    // List<Cart> cartList=fetchCartDetailsByUsername(username);
                    displayCartDetails(username);
                    break;
                case 5:
                    removeBookFromCart(username);
                    break;
                case 6:
                    searchBookByIsbn(username);
                    break;
                case 7:
                    fetchAllBooks();
                    break;
                case 8:
                    displayBooksMenu(username);
                    break;

                case 9:
                    displayAllBooks();
                    break;
                case 10:
                    System.out.println("Exiting...");
                    return;
                case 11:
                    viewBooksByGenre(username);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void displayAllBooks() {
        String query = "SELECT * FROM books";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            // Print table header
            System.out.printf("%-5s %-15s %-30s %-20s %-15s %-10s %-6s%n", "bookId", "ISBN", "Title", "Author", "Genre",
                    "Price",
                    "stock");
            System.out.println("------------------------------------------------------------");

            // Print each book's details
            while (rs.next()) {
                int bookid = rs.getInt("book_id");
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock_quantity");

                System.out.printf("%-5s %-15s %-30s %-20s %-15s Rs.%-9.2f %d%n", bookid, isbn, title, author, genre,
                        price, stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayBooksMenu(String username) {
        boolean flag = true;
        while (flag) {
            System.out.println("""
                        How would you like to display books?
                    -----------------------------------------
                            1 --> By Title
                            2 --> By Author
                            3 --> By Genre
                            4 --> Back to User Menu
                    -----------------------------------------

                                                """);
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String query = "";
            switch (choice) {
                case 1:
                    displayAllBooks();
                    System.out.print("Enter title keyword: ");
                    String titleKeyword = scanner.nextLine();
                    query = "SELECT * FROM books WHERE title LIKE ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                        pstmt.setString(1, "%" + titleKeyword + "%");
                        ResultSet rs = pstmt.executeQuery();
                        printBookResults(rs);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    displayAllBooks();
                    System.out.print("Enter author keyword: ");
                    String authorKeyword = scanner.nextLine();
                    query = "SELECT * FROM books WHERE author LIKE ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                        pstmt.setString(1, "%" + authorKeyword + "%");
                        ResultSet rs = pstmt.executeQuery();
                        printBookResults(rs);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    viewBooksByGenre(username);
                    break;
                case 4:
                    flag = false;
                    System.out.println("Back to Main menu");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Helper method to print book results
    private void printBookResults(ResultSet rs) throws SQLException {
        // Print table header with aligned columns
        System.out.printf("%-15s %-30s %-20s %-15s %-10s %-10s %-10s%n",
                "ISBN", "Title", "Author", "Genre", "Price", "Stock", "Rating");
        System.out.println("-------------------------------------------------------------------------");

        // Iterate through the result set and print each record
        while (rs.next()) {
            String isbn = rs.getString("isbn");
            String title = rs.getString("title");
            String author = rs.getString("author");
            String genre = rs.getString("genre");
            double price = rs.getDouble("price");
            int stock = rs.getInt("stock_quantity");
            double rating = rs.getDouble("rating");

            // Print each record with proper alignment
            System.out.printf("%-15s %-30s %-20s %-15s Rs.%-9.2f %-10d %-10.2f%n",
                    isbn, title, author, genre, price, stock, rating);
        }
    }

    public List<Book> fetchAllBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int bookId = rs.getInt("book_id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String genre = rs.getString("genre");
                    String isbn = rs.getString("isbn");
                    double price = rs.getDouble("price");
                    int stockQuantity = rs.getInt("stock_quantity");
                    java.sql.Date publishedDate = rs.getDate("published_date");
                    String publisher = rs.getString("publisher");
                    double rating = rs.getDouble("rating");

                    // Create a new Book object and add it to the list
                    Book book = new Book(bookId, title, author, genre, isbn, price, stockQuantity, publishedDate,
                            publisher, rating);
                    bookList.add(book);
                }
            }
        }
        sortBooksByRatingDescending(bookList);
        return bookList;
    }

    public void sortBooksByRatingDescending(List<Book> bookList) {
        Collections.sort(bookList, Comparator.comparing(Book::getRating));
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-4s %-30s %-20s %-15s %-10s %-6s %-6s%n",
                "ID", "Title", "Author", "Genre", "Price", "Qty", "Rating");
        System.out.println("----------------------------------------------------------------------------------------");

        // Print each book's details
        for (Book book : bookList) {
            System.out.printf("%-4d %-30s %-20s %-15s Rs.%-9.2f %-6d %-6.1f%n",
                    book.getBook_id(), book.getTitle(), book.getAuthor(),
                    book.getGenre(), book.getPrice(),
                    book.getStock_quantity(), book.getRating());
        }

        System.out.println("----------------------------------------------------------------------------------------");
    }

    @Override
    public void viewBills(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewBills'");
    }
}
