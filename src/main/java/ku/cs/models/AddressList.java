package ku.cs.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AddressList {

    private ArrayList<Address> addresses;
    public AddressList() {
        addresses = new ArrayList<>();
    }
    public void addAddress(Address address) {
        addresses.add(address);
    }
    public void removeAddress(Address address) {
        Address removeAddress = searchAddressById(address.getId());
        addresses.remove(removeAddress);
    }
    public void editAddress(Address address) {
        Address editAddress = searchAddressById(address.getId());
        editAddress.setName(address.getName());
        editAddress.setTelephoneNumber(address.getTelephoneNumber());
        editAddress.setDistrict(address.getDistrict());
        editAddress.setProvince(address.getProvince());
        editAddress.setZipCode(address.getZipCode());
        editAddress.setAddressDetails(address.getAddressDetails());
    }
    public ArrayList<Address> getAllAddresses() {
        return addresses;
    }
    public int count() {
        return addresses.size();
    }

    public ArrayList<Address> getMyAddresses(String username) {
        ArrayList<Address> myAddresses = new ArrayList<>();
        for (Address address: addresses) {
            if (address.isUsername(username)) {
                myAddresses.add(address);
            }
        }
        return myAddresses;
    }

    public Address searchAddressById(String id) {
        for (Address address: addresses)
            if (address.checkId(id)) {
                return address;
            }
        return null;
    }

    public String toCSV() {
        String result = "";
        for (Address address: this.addresses) {
            result += address.toCSV() + "\n";
        }
        return result;
    }
}
