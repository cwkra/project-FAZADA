package ku.cs.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserList {

    private ArrayList<User> users;
    public UserList() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public int count() {
        return users.size();
    }

    public void sort(Comparator<User> userComparator) {
        Collections.sort(this.users, userComparator);
    }

    public boolean checkUsername(String username) {
        for (User user: this.users) {
            if (user.isUsername(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String password) {
        for (User user: this.users) {
            if (user.isPassword(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkShopName(String shopName) {
        for (User user: this.users) {
            if (user.isShopName(shopName)) {
                return true;
            }
        }
        return false;
    }

    public User searchUsername(String username) {
        for (User user: this.users) {
            if (user.isUsername(username)) {
                return user;
            }
        }
        return null;
    }

    public User searchShopName(String shopName) {
        for (User user: this.users) {
            if (user.isShopName(shopName)) {
                return user;
            }
        }
        return null;
    }

    public void editProfile(User user) {
        User editUser = searchUsername(user.getUsername());
        editUser.setEmail(user.getEmail());
        editUser.setTelephoneNumber(user.getTelephoneNumber());
    }

    public void changePassword(User user) {
        User editUser = searchUsername(user.getUsername());
        editUser.setPassword(user.getPassword());
    }

    public void setShopName(User user) {
        User editUser = searchUsername(user.getUsername());
        editUser.setShopName(user.getShopName());
    }

    public void setImagePath(User user) {
        User editUser = searchUsername(user.getUsername());
        editUser.setImagePath(user.getImagePath());
    }

    public String toCSV() {
        String result = "";
        for (User user: this.users) {
            result += user.toCSV() + "\n";
        }
        return result;
    }

    public void sortUser() {
        Comparator<User> comparator = (c1, c2) -> {
            return c2.getSignInTime().compareTo(c1.getSignInTime());
        };
        Collections.sort(users, comparator);
    }
}
