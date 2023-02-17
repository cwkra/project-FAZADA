package ku.cs.services;

import ku.cs.models.Address;
import ku.cs.models.AddressList;
import ku.cs.models.Product;
import ku.cs.models.ProductList;

import java.io.*;

public class ProductFileDataSource implements DataSource<ProductList> {
    private String directoryName;
    private String fileName;
    public ProductFileDataSource() {
        this("csv-data", "product.csv");
    }
    public ProductFileDataSource(String directoryName, String fileName) {
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
    public void writeData(ProductList productList) {
        String path = directoryName + File.separator + fileName;
        File file = new File(path);
        //การเขียนใช้ FileWriter ให้สร้างขึ้นมา
        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file); //FileWriter must Try/Catch
            buffer = new BufferedWriter(writer);

            buffer.write(productList.toCSV());

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
    public ProductList readData() {
        ProductList productList = new ProductList();
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
                productList.addProduct(new Product(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        Double.parseDouble(data[3].trim()),
                        Integer.parseInt(data[4].trim()),
                        Integer.parseInt(data[5].trim()),
                        data[6].trim(),
                        data[7].trim(),
                        data[8].trim(),
                        data[9].trim()
                ));
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
