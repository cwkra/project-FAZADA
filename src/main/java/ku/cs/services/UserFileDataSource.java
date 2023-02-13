package ku.cs.services;

import ku.cs.models.User;
import ku.cs.models.UserList;

import java.io.*;

public class UserFileDataSource implements DataSource<UserList>{

    private String directoryName;
    private String fileName;

    //ใช้ตอน Production จริง
    public UserFileDataSource(){
        this("csv-data","user.csv");
    }

    //For test
    public UserFileDataSource(String directoryName,String fileName){
        this.directoryName = directoryName;
        this.fileName = fileName;
        initialFileIfNotExist();
    }

    //Method สร้าง File เมื่อไม่มีอยู่
    private void initialFileIfNotExist(){
        File file = new File(directoryName);
        //ถ้าไม่มี directory ให้สร้าง directory
        if (!file.exists()){
            file.mkdir();
        }
        //check file must creat path first
        String path = directoryName + File.separator + fileName;
        file = new File(path); //file.csv name

        //ถ้ายังไม่มี file ให้สร้าง file
        if (!file.exists()){
            try {
                file.createNewFile(); //CreateNewFile ทำให้เกิด exception แก้ด้วย Try/Catch
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeData(UserList userList) {
        String path = directoryName + File.separator + fileName;
        File file = new File(path);
        //การเขียนใช้ FileWriter ให้สร้างขึ้นมา
        FileWriter writer = null;
        BufferedWriter buffer = null;

        try {
            writer = new FileWriter(file); //FileWriter must Try/Catch
            buffer = new BufferedWriter(writer);

            buffer.write(userList.toCSV());

        } catch (IOException e) {
            e.printStackTrace();
        }finally { //Finally open FileWriter,BufferedWriter and close them
            try {
                buffer.close(); //.close() must Try/Catch
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public UserList readData() {
        UserList userList = new UserList();
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
                String role = data[0];
                if (role.equals("USER")) {
                    userList.addUser(new User(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim(),
                            data[5].trim(),
                            data[6].trim(),
                            data[7].trim(),
                            data[8].trim(),
                            data[9].trim(),
                            Integer.parseInt(data[10].trim()),
                            data[11].trim()
                    ));
                } else if (role.equals("SELLER")) {
                    userList.addUser(new User(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim(),
                            data[5].trim(),
                            data[6].trim(),
                            data[7].trim(),
                            data[8].trim(),
                            data[9].trim(),
                            Integer.parseInt(data[10].trim()),
                            data[11].trim()
                    ));
                } else if (role.equals("ADMIN")) {
                    userList.addUser(new User(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim(),
                            data[5].trim(),
                            data[6].trim(),
                            data[7].trim(),
                            data[8].trim(),
                            data[9].trim(),
                            Integer.parseInt(data[10].trim()),
                            data[11].trim()
                    ));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
