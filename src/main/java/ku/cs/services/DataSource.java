package ku.cs.services;

public interface DataSource <T>{

    void writeData(T t);
    T readData();
}
