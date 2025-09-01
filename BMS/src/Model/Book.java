package Model;

import java.sql.Date;

public class Book {
    int book_id, stock_quantity;
    double price, rating;
    String title, author, genre, isbn, publisher;
    Date published_date;
    public Book(int stock_quantity, double price, double rating, String title, String author, String genre, String isbn,
            Date published_date, String publisher) {
        this.stock_quantity = stock_quantity;
        this.price = price;
        this.rating = rating;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.published_date = published_date;
        this.publisher = publisher;
    }
    public Book(int bookId, String title, String author, String genre, String isbn, 
                double price, int stockQuantity, Date publishedDate, 
                String publisher, double rating) {
        this.book_id = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.price = price;
        this.stock_quantity = stockQuantity;
        this.published_date = publishedDate;
        this.publisher = publisher;
        this.rating = rating;
    }
    public int getBook_id() {
        return book_id;
    }
    public int getStock_quantity() {
        return stock_quantity;
    }
    public double getPrice() {
        return price;
    }
    public double getRating() {
        return rating;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getGenre() {
        return genre;
    }
    public String getIsbn() {
        return isbn;
    }
    public Date getPublished_date() {
        return published_date;
    }
    public String getPublisher() {
        return publisher;
    }
    @Override
    public String toString() {
        return "Book [book_id=" + book_id + ", stock_quantity=" + stock_quantity + ", price=" + price + ", rating="
                + rating + ", title=" + title + ", author=" + author + ", genre=" + genre + ", isbn=" + isbn
                + ", published_date=" + published_date + ", publisher=" + publisher + "]";
    }
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public void setPublished_date(Date published_date) {
        this.published_date = published_date;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
}
