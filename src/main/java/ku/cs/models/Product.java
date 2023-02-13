package ku.cs.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Product {
    private String id = UUID.randomUUID().toString();
    private String name;
    private double price;
    private int amount;
    private String category;
    private String detail;
    private String image;
    private LocalDateTime timeAddProduct;
}
