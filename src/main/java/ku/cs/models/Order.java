package ku.cs.models;

import java.time.LocalDateTime;

public class Order {
    private String id;
    private String status;
    private String trackingCode;
    private int productQuantity;
    private double cumulativePurchase;
    private LocalDateTime createOrderTime;
    private User customer;
    private Product product;
    private Address customerAddress;


}
