package sample;

import javafx.beans.property.SimpleStringProperty;


public class Address {
    private final SimpleStringProperty addresstype = new SimpleStringProperty("");
    private final SimpleStringProperty streetaddress = new SimpleStringProperty("");
    private final SimpleStringProperty city = new SimpleStringProperty("");
    private final SimpleStringProperty state = new SimpleStringProperty("");
    private final SimpleStringProperty zip = new SimpleStringProperty("");

    public Address() {
        this("", "", "","","");
    }

    public Address(String addressType, String address, String city, String state, String zip) {
        setAddresstype(addressType);
        setStreetaddress(address);
        setCity(city);
        setState(state);
        setZip(zip);
    }

    public String getAddresstype() {
        return addresstype.get();
    }

    public void setAddresstype(String addresstype) {
        this.addresstype.set(addresstype);
    }

    public String getStreetaddress() {
        return streetaddress.get();
    }

    public void setStreetaddress(String addres) {
        streetaddress.set(addres);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String acity) {
        city.set(acity);
    }

    public String getState() {
        return state.get();
    }

    public void setState(String astate) {
        state.set(astate);
    }

    public String getZip() {
        return zip.get();
    }

    public void setZip(String azip) {
        zip.set(azip);
    }
}

