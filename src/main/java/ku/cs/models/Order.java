package ku.cs.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private String id = UUID.randomUUID().toString();
    private String userId;
    private String productId;
    private Address address;
    private String status;
    private String trackingCode;
    private int productQuantity;
    private double cumulativePurchase;
    private LocalDateTime time;
}
