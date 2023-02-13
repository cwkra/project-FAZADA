package ku.cs.services;

import ku.cs.models.Address;
import ku.cs.models.AddressList;
import ku.cs.models.User;
import ku.cs.models.UserList;
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

}
