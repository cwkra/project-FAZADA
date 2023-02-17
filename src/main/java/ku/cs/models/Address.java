package ku.cs.models;

import java.util.UUID;

public class Address {
    private String id;
    private String username;
    private String name;
    private String telephoneNumber;
    private String district;
    private String province;
    private String zipCode;
    private String addressDetails;

    public Address(String id, String username, String name, String telephoneNumber, String district, String province, String zipCode, String addressDetails) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.district = district;
        this.province = province;
        this.zipCode = zipCode;
        this.addressDetails = addressDetails;
    }

    public Address(String username, String name, String telephoneNumber, String district, String province, String zipCode, String addressDetails) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.district = district;
        this.province = province;
        this.zipCode = zipCode;
        this.addressDetails = addressDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    public boolean checkId(String id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        return " " + name + " (" + telephoneNumber + ")";
    }
    public String toCSV() {
        return id + "," + username + "," + name + "," + telephoneNumber + "," + district + "," + province + "," + zipCode + "," + addressDetails;
    }
}
