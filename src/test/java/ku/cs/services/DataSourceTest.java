package ku.cs.services;

import ku.cs.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataSourceTest {

    @Test
    @DisplayName("ทดสอบการเขียนไฟล์ผู้ใช้")
    void testWriteUserData() {
        DataSource <UserList> dataSource = new UserFileDataSource("unit-test",  "user.csv");
        UserList userList = dataSource.readData();
        userList.addUser(new User("cwkra", "123", "Chawalkorn", "Rasita"));
        dataSource.writeData(userList);
        File file = new File("unit-test" + File.separator + "user.csv");
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("ทดสอบการอ่านไฟล์ผู้ใช้")
    void testReadUserData() {
        DataSource <UserList> dataSource = new UserFileDataSource("unit-test",  "user.csv");
        UserList userList = dataSource.readData();
        dataSource.writeData(userList);

        UserList readList = dataSource.readData();
        assertEquals(1, readList.count());
        assertEquals(userList.toCSV(), readList.toCSV());
    }

    @Test
    @DisplayName("ทดสอบการเขียนไฟล์ที่อยู่")
    void testWriteAddressData() {
        DataSource <AddressList> dataSource = new AddressFileDataSource("unit-test",  "address.csv");
        AddressList addressList = dataSource.readData();
        addressList.addAddress(new Address(UUID.randomUUID().toString(),"cwkra", "Chawalkorn Rasita", "0837726202", "จตุจักร", "กรุงเทพมหานคร", "10900", "เลขที่ 5 ซอยวิภาวดีรังสิต 34 ถนนวิภาวดีรังสิต แขวงจตุจักร"));
        dataSource.writeData(addressList);
        File file = new File("unit-test" + File.separator + "address.csv");
        assertTrue(file.exists());
    }


    @Test
    @DisplayName("ทดสอบการอ่านไฟล์ที่อยู่")
    void testReadAddressData() {
        DataSource <AddressList> dataSource = new AddressFileDataSource("unit-test",  "address.csv");
        AddressList addressList = dataSource.readData();
        dataSource.writeData(addressList);

        AddressList readList = dataSource.readData();
        assertEquals(1, readList.count());
        assertEquals(addressList.toCSV(), readList.toCSV());
    }

    @Test
    @DisplayName("ทดสอบการเขียนไฟล์คำสั่งซื้อ")
    void testWriteProductData() {
        DataSource <ProductList> dataSource = new ProductFileDataSource("unit-test",  "product.csv");
        ProductList productList = dataSource.readData();
        productList.addProduct(new Product("กล่องข้าว", "cwkra.shop", 250.00, 8, "อื่น ๆ ", "กล่องข้าวสีขาว" ,""));
        dataSource.writeData(productList);
        File file = new File("unit-test" + File.separator + "product.csv");
        assertTrue(file.exists());
    }


    @Test
    @DisplayName("ทดสอบการอ่านไฟล์คำสั่งซื้อ")
    void testReadProductData() {
        DataSource <ProductList> dataSource = new ProductFileDataSource("unit-test",  "product.csv");
        ProductList productList = dataSource.readData();
        dataSource.writeData(productList);

        ProductList readList = dataSource.readData();
        assertEquals(1, readList.count());
        assertEquals(productList.toCSV(), readList.toCSV());
    }

    @Test
    @DisplayName("ทดสอบการเขียนไฟล์คำสั่งซื้อ")
    void testWriteOrderData() {
        DataSource <OrderList> dataSource = new OrderFileDataSource("unit-test",  "order.csv");
        OrderList orderList = dataSource.readData();
        orderList.addOrder(new Order("81d659c1-32cc-45ca-bd2f-c7ca7c95afaf", "cwkra", "46e90fe6-845b-4ab6-b04a-0600c753e670", "cwkra.shop", 2, 500.00));
        dataSource.writeData(orderList);
        File file = new File("unit-test" + File.separator + "order.csv");
        assertTrue(file.exists());
    }


    @Test
    @DisplayName("ทดสอบการอ่านไฟล์คำสั่งซื้อ")
    void testReadOrderData() {
        DataSource <OrderList> dataSource = new OrderFileDataSource("unit-test",  "order.csv");
        OrderList orderList = dataSource.readData();
        dataSource.writeData(orderList);

        OrderList readList = dataSource.readData();
        assertEquals(1, readList.count());
        assertEquals(orderList.toCSV(), readList.toCSV());
    }

}
