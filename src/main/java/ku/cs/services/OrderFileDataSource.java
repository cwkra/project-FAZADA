package ku.cs.services;

import ku.cs.models.Order;
import ku.cs.models.OrderList;

import java.io.*;

public class OrderFileDataSource implements DataSource<OrderList>{
    private String directoryName;
    private String fileName;

    public OrderFileDataSource() {
        this("csv-data", "order.csv");
    }

    public OrderFileDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        initialFileIfNotExist();
    }

    private void initialFileIfNotExist() {
        File file = new File(directoryName);
        //ถ้าไม่มี directory ให้สร้าง directory
        if (!file.exists()) {
            file.mkdir();
        }
        //check file must creat path first
        String path = directoryName + File.separator + fileName;
        file = new File(path); //file.csv name

        //ถ้ายังไม่มี file ให้สร้าง file
        if (!file.exists()) {
            try {
                file.createNewFile(); //CreateNewFile ทำให้เกิด exception แก้ด้วย Try/Catch
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeData(OrderList orderList) {
        String path = directoryName + File.separator + fileName;
        File file = new File(path);
        //การเขียนใช้ FileWriter ให้สร้างขึ้นมา
        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file); //FileWriter must Try/Catch
            buffer = new BufferedWriter(writer);

            buffer.write(orderList.toCSV());

        } catch (IOException e) {
            e.printStackTrace();
        } finally { //Finally open FileWriter,BufferedWriter and close them
            try {
                buffer.close(); //.close() must Try/Catch
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public OrderList readData() {
        OrderList orderList = new OrderList();
        //Create path for where file read
        String path = directoryName + File.separator + fileName;
        File file = new File(path);
        FileReader reader = null;
        BufferedReader buffer = null;
        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            //Read file line by line and prepared variable เก็บข้อมูลทีละบรรทัด and name "line"
            String line = "";
            //อ่านข้อมูล
            while ((line = buffer.readLine()) != null) { //.readLine() has exception ต้อง Add 'catch' clause
                //รับค่ามาเสร็จ ก็จะมา split ตัว csv --> split มา จะได้ ArrayString ตั้งชื่อว่า data
                String[] data = line.split(",");
                String id = data[0].trim();
                String productId = data[1].trim();
                String customerUsername = data[2].trim();
                String customerAddressId = data[3].trim();
                String shopName = data[4].trim();
                String status = data[5].trim();
                String trackingCode = data[6].trim();
                int productQuantity = Integer.parseInt(data[7].trim());
                double cumulativePurchase = Double.parseDouble(data[8].trim());
                String createOrderTime = data[9].trim();
                orderList.addOrder(new Order(
                        id,
                        productId,
                        customerUsername,
                        customerAddressId,
                        shopName,
                        status,
                        trackingCode,
                        productQuantity,
                        cumulativePurchase,
                        createOrderTime
                ));
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
