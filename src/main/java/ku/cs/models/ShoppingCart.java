package ku.cs.models;

public class ShoppingCart {
    private User user;
    private Product product;
    private int quantity;

    public ShoppingCart(User user, Product product) {
        this.user = user;
        this.product = product;
        this.quantity = 0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
