package Model;

import java.sql.Timestamp;

public class Cart {
    private int cart_id;
    private int user_id;
    private String username;
    private int book_id;
    private String bookName;
    private int quantity;
    private double price;
    private Timestamp added_at;

    // public Cart(int cartId, int userId, String username1, int bookId, int
    // quantity1, Timestamp addedAt){}
    public Cart(int user_id, String username, int book_id, int quantity, double price) {
        // this.cart_id=cart_id;
        this.user_id = user_id;
        this.username = username;
        this.book_id = book_id;
        this.quantity = quantity;
        this.price = price;
        // this.added_at = added_at;
    }

    public Cart(int user_id, String username, int book_id,String bookName, int quantity, double price) {
        //this.cart_id = cartId;
        this.user_id = user_id;
        this.username = username;
        this.book_id = book_id;
        this.quantity = quantity;
        this.price = price;
       // this.added_at = added_at;//
        this.bookName=bookName;

    }
    // public Cart(int cart_id,int user_id, String username, int book_id,String bookName, int quantity, double price ,Timestamp added_at) {
    //     this.cart_id = cart_id;
    //     this.user_id = user_id;
    //     this.username = username;
    //     this.book_id = book_id;
    //     this.quantity = quantity;
    //     this.price = price;
    //    this.added_at = added_at;
    //     this.bookName=bookName;

    // }
    public Cart(int cart_id,int user_id, String username, int book_id,String bookName, int quantity, double price, Timestamp added_at) {
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.username = username;
        this.book_id = book_id;
        this.quantity = quantity;
        this.price = price;
        this.added_at = added_at;
        this.bookName=bookName;

    }
    // @Override
    // public String toString() {
    // return "Cart [ user_id=" + user_id + ", username=" + username + ", book_id="
    // + book_id
    // + ", quantity=" + quantity + "]";
    // }

    // Getter and Setter for cart_id
    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public String getBookName() {
        return bookName;
    }

    @Override
    public String toString() {
        return "Cart [cart_id=" + cart_id + ", user_id=" + user_id + ", username=" + username + ", book_id=" + book_id
                + ", bookName=" + bookName + ", quantity=" + quantity + ", price=" + price + ", added_at=" + added_at
                + "]";
    }

    // Getter and Setter for user_id
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for book_id
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    // Getter and Setter for added_at
    public Timestamp getAdded_at() {
    return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
    this.added_at = added_at;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
