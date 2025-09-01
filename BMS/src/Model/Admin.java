package Model;

public class Admin {
    int admin_id;
    String username , password,email , phone_number;
    public int getAdmin_id() {
        return admin_id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return "Admin [admin_id=" + admin_id + ", username=" + username + ", password=" + password + ", email=" + email
                + ", phone_number=" + phone_number + "]";
    }
    public String getEmail() {
        return email;
    }
    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public Admin(String username, String password, String email, String phone_number) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
    }
}
