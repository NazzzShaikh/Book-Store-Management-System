package Model;

public class User {
    int user_id, vip_pass;
    String username, password, userFullname, email, phone_number, address;
    public User(int vip_pass,String username, String password, String userFullname, String email, String phone_number,
            String address) {
        this.username = username;
        this.password = password;
        this.userFullname = userFullname;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.vip_pass=vip_pass;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getVip_pass() {
        return vip_pass;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getUserFullname() {
        return userFullname;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public String getAddress() {
        return address;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setVip_pass(int vip_pass) {
        this.vip_pass = vip_pass;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", vip_pass=" + vip_pass + ", username=" + username + ", password="
                + password + ", userFullname=" + userFullname + ", email=" + email + ", phone_number=" + phone_number
                + ", address=" + address + "]";
    }

}
