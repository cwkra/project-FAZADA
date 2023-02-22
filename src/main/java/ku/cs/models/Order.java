package ku.cs.models;

import ku.cs.services.AddressFileDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Order {
    private String id;
    private String productId;
    private String customerUsername;
    private String customerAddressId;
    private String shopName;
    private String status;
    private String trackingCode;
    private int productQuantity;
    private double cumulativePurchase;
    private LocalDateTime createOrderTime;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private DataSource<ProductList> productListDataSource = new ProductFileDataSource();
    private ProductList productList = productListDataSource.readData();
    private DataSource<AddressList> addressDataSource = new AddressFileDataSource();
    private AddressList addressList = addressDataSource.readData();

    public Order(String productId, String customerUsername, String customerAddressId, String shopName, int productQuantity, double cumulativePurchase) {
        this.id = UUID.randomUUID().toString();
        this.productId = productId;
        this.customerUsername = customerUsername;
        this.customerAddressId = customerAddressId;
        this.shopName = shopName;
        this.status = "รอการจัดส่ง";
        this.trackingCode = "";
        this.productQuantity = productQuantity;
        this.cumulativePurchase = cumulativePurchase;
        this.createOrderTime = LocalDateTime.now();
    }

    public Order(String id, String productId, String customerUsername, String customerAddressId, String shopName, String status, String trackingCode, int productQuantity, double cumulativePurchase, String createOrderTime) {
        this.id = id;
        this.productId = productId;
        this.customerUsername = customerUsername;
        this.customerAddressId = customerAddressId;
        this.shopName = shopName;
        this.status = status;
        this.trackingCode = trackingCode;
        this.productQuantity = productQuantity;
        this.cumulativePurchase = cumulativePurchase;
        this.createOrderTime = LocalDateTime.parse(createOrderTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getCumulativePurchase() {
        return cumulativePurchase;
    }

    public void setCumulativePurchase(double cumulativePurchase) {
        this.cumulativePurchase = cumulativePurchase;
    }

    public String getCreateOrderTime() {
        return createOrderTime.toString();
    }

    public void setCreateOrderTime(LocalDateTime createOrderTime) {
        this.createOrderTime = createOrderTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(String customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public boolean checkId(String id) {
        return this.id.equals(id);
    }
    public boolean checkShop(String shopName) {
        return this.shopName.equals(shopName);
    }
    public boolean checkUsername(String username) {
        return this.customerUsername.equals(username);
    }
    public boolean isPreparingToShip() {
        return this.status.equals("กำลังเตรียมจัดส่ง");
    }
    public boolean isInDelivery() {
        return this.status.equals("อยู่ในระหว่างการจัดส่ง");
    }
    public boolean isDelivered() {
        return this.status.equals("การจัดส่งสำเร็จ");
    }
    public boolean isCompleted() {
        return this.status.equals("สำเร็จ");
    }
    public static String localDateTimeToDateWithSlash(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(localDateTime);
    }
    public String toStringAddressDetails() {
        Address address = addressList.searchAddressById(customerAddressId);
        return  address.getName() + "\n"
                + address.getAddressDetails() + "\n"
                + address.getDistrict() + " "
                + address.getProvince() + " "
                + address.getZipCode();
    }
    public String toCSV() {
        return id + "," +
                productId + "," +
                customerUsername + "," +
                customerAddressId + "," +
                shopName + "," +
                status + "," +
                trackingCode + "," +
                productQuantity + "," +
                cumulativePurchase + "," +
                getCreateOrderTime();
    }
}
