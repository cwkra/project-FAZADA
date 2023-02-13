package ku.cs.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class User {
    private String role;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String telephoneNumber;
    private String shopName;
    private LocalDateTime signInTime;
    private String banStatus;
    private int accessCount;
    private String imagePath;

    public User(String role, String username, String password, String firstName, String lastName, String email, String telephoneNumber, String shopName, String signInTime, String banStatus, int accessCount, String imagePath) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.shopName = shopName;
        this.signInTime = LocalDateTime.parse(signInTime);
        this.banStatus = banStatus;
        this.accessCount = accessCount;
        this.imagePath = imagePath;
    }

    public User(String username, String password, String firstName, String lastName) {
        this.role = "USER";
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = "";
        this.telephoneNumber = "";
        this.shopName = "";
        this.signInTime = LocalDateTime.MIN;
        this.banStatus = "NOT_BANNED";
        this.accessCount = 0;
        this.imagePath = "src/main/resources/user.png";
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSignInTime() {
        return signInTime.toString();
    }

    public void setSignInTime(LocalDateTime signInTime) {
        this.signInTime = signInTime;
    }

    public String getBanStatus() {
        return banStatus;
    }

    public void setBanStatus(String banStatus) {
        this.banStatus = banStatus;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    public boolean isFirstName(String firstName) {
        return this.firstName.equals(firstName);
    }

    public boolean isLastName(String lastName) {
        return this.lastName.equals(lastName);
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isShopName(String shopName) {
        return this.shopName.equals(shopName);
    }

    public boolean isAdmin() {
        return this.role.equals("ADMIN");
    }

    public boolean isUser() {
        return this.role.equals("USER");
    }

    public boolean isBanned() {
        return this.banStatus.equals("BANNED");
    }
    public String toCSV() {
        return role + "," + username + "," + password + "," +
                firstName + "," + lastName + "," + email + "," + telephoneNumber + "," +
                shopName + "," + getSignInTime() + "," + banStatus + "," + accessCount + "," +
                imagePath;
    }

    @Override
    public String toString() {
        return "["+ role + "] "+  username +" (" + toStringSignInTime() + ")";
    }

    public String toStringSignInTime() {
        return signInTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
    }
}
