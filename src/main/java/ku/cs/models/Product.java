package ku.cs.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Product {
    private String id;
    private String name;
    private String shopName;
    private double price;
    private int stock;
    private int sold;
    private String category;
    private String details;
    private String imagePath;
    private LocalDateTime timeAddProduct;

    public Product(String id, String name, String shopName, double price, int stock, int sold, String category, String details, String imagePath, String timeAddProduct) {
        this.id = id;
        this.name = name;
        this.shopName = shopName;
        this.price = price;
        this.stock = stock;
        this.sold = sold;
        this.category = category;
        this.details = details;
        this.imagePath = imagePath;
        this.timeAddProduct = LocalDateTime.parse(timeAddProduct);
    }

    public Product(String name, String shopName, double price, int stock, String category, String details, String imagePath) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.shopName = shopName;
        this.price = price;
        this.stock = stock;
        this.sold = 0;
        this.category = category;
        this.details = details;
        this.imagePath = imagePath;
        this.timeAddProduct = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
    public void addSold(int sold) {
        this.sold += sold;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTimeAddProduct() {
        return timeAddProduct.toString();
    }

    public void setTimeAddProduct(LocalDateTime timeAddProduct) {
        this.timeAddProduct = timeAddProduct;
    }

    public void addStock(int stock) {
        this.stock += stock;
    }
    public void reduceStock(int stock) {
        this.stock -= stock;
    }

    public boolean isId(String id) {
        return this.id.equals(id);
    }
    public boolean isShopName(String shopName) {
        return this.shopName.equals(shopName);
    }
    public String toCSV() {
        return id + "," +
                name + "," +
                shopName + "," +
                price + "," +
                stock + "," +
                sold + "," +
                category + "," +
                details + "," +
                imagePath + "," +
                getTimeAddProduct();
    }
    public String toStringAddProductTime() {
        return timeAddProduct.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
    }

}
