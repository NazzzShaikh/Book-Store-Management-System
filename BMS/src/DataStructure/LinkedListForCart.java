package DataStructure;

import Model.Cart;
import java.sql.*;
import java.util.Scanner;
// import java.text.SimpleDateFormat;

public class LinkedListForCart {
    private Connection connection;
    private Scanner scanner;
    private String username;

    public class Node {

        public Node next;
        public Cart userCart;

        public Node(Cart userCart) {
            this.userCart = userCart;
            next = null;
        }
    }

    public LinkedListForCart(Connection connection, String username) throws SQLException {
        this.connection = connection;
        this.username = username;
    }

    public Node head = null;
    public Node temp01 = head;

    public void addBookToCart(Cart userCart) {
        Node n = new Node(userCart);
        if (head == null) {
            head = n;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = n;
        }
    }

    // public void removeBookFromCart(int book_id) {
    //     if (head == null) {
    //         System.out.println("Your cart is empty!");
    //     } else {
    //         int flag = 0;
    //         Node temp = head;
    //         if (head.userCart.getBook_id() == book_id) {
    //             Node del = head;
    //             head = head.next;
    //             del.next = null;
    //         } else {
    //             while (temp != null) {
    //                 if (temp.next.userCart.getBook_id() == book_id) {
    //                     flag = 1;
    //                     break;
    //                 } else {
    //                     temp = temp.next;
    //                 }
    //             }
    //             if (flag == 1) {
    //                 temp.next = temp.next.next;
    //             } else {
    //                 System.out.println("No prduct found in cart!");
    //             }
    //         }
    //     }
    // }
    public boolean removeBookFromCart(int book_id) {
        if (head == null) {
            System.out.println("Your cart is empty!");
            return false; // Cart is empty, cannot remove any item
        } else {
            Node temp = head;
            if (head.userCart.getBook_id() == book_id) {
                // Special case: the book to remove is at the head of the list
                Node del = head;
                head = head.next;
                del.next = null;
                return true; // Successfully removed the book
            } else {
                while (temp.next != null) {
                    if (temp.next.userCart.getBook_id() == book_id) {
                        // Book found and removed
                        Node del = temp.next;
                        temp.next = temp.next.next;
                        del.next = null;
                        return true; // Successfully removed the book
                    } else {
                        temp = temp.next;
                    }
                }
                // Book not found in the list
                System.out.println("No product found in the cart!");
                return false;
            }
        }
    }
    

    public void addPreviouCart() throws SQLException {
        String sql = "SELECT * FROM cart WHERE username = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        if (rs != null) {
            while (rs.next()) {
                int cartId = rs.getInt("cart_id");
                int userId = rs.getInt("user_id");
                int bookId = rs.getInt("book_id");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price"); // Adjust based on your schema
                Timestamp addedAt = rs.getTimestamp("added_at");
                String bookNamee=rs.getString("bookname");
                // Create a new Cart object and add it to the list
                Cart cart = new Cart(cartId, userId, username, bookId,bookNamee, quantity, price, addedAt);
                Node n = new Node(cart);
                if (head == null) {
                    head = n;
                } else {
                    Node temp = head;
                    while (temp.next != null) {
                        temp = temp.next;
                    }
                    temp.next = n;
                }
            }

        }
    }

    // boolean displayCart() {

    // if (head == null) {
    // System.out.println("No product in cart!");
    // return false;
    // } else {
    // Node temp = head;
    // while (temp != null) {
    // System.out.println("--------------------------------------------------------------------------");
    // System.out.println("Product Id :- " + temp.userCart.getpID());
    // System.out.println("Product Name :-" + temp.userCart.getpName());
    // System.out.println("Product Quantity:-" + temp.userCart.getpQuantity());
    // System.out.println("Product Price :-" + temp.userCart.getpPrice());
    // temp = temp.next;
    // }
    // return true;

    // }
    // }
    public boolean displayCart() {
        if (head == null) {
            System.out.println("No product in cart!");
            return false;
        } else {
            Node temp = head;
            System.out
                    .println("--------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-10s %-10s %-10s%n",
                    "Cart ID", "User ID", "Book ID", "Quantity", "Price");
            System.out
                    .println("--------------------------------------------------------------------------------------");
            while (temp != null) {
                System.out.printf("%-10d %-10s %-10d %-10d $%-9.2f%n",
                        temp.userCart.getUser_id(), temp.userCart.getUsername(), temp.userCart.getBook_id(),
                        temp.userCart.getQuantity(), temp.userCart.getPrice());
            }
            return true;
        }
    }
}