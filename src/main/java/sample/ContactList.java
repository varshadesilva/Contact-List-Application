package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class ContactList {   //class is bound to the table view that displays search hits
    private final SimpleStringProperty firstname = new SimpleStringProperty("");
    private final SimpleStringProperty middlename = new SimpleStringProperty("");
    private final SimpleStringProperty lastname = new SimpleStringProperty("");
    private final SimpleIntegerProperty contactid = new SimpleIntegerProperty();

    public ContactList() {
        this("", "","", 0);
    }

    public ContactList(String fname, String mname, String lname, int idcontact) {
        setFirstname(fname);
        setMiddlename(mname);
        setLastname(lname);
        setContactid(idcontact);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String fname) {
        firstname.set(fname);
    }

    public String getMiddlename() {
        return middlename.get();
    }

    public void setMiddlename(String mname) {
        middlename.set(mname);
    }

    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String lname) {
        lastname.set(lname);
    }

    public int getContactid() {
        return contactid.get();
    }

    public void setContactid(int idcontact) {
        contactid.set(idcontact);
    }
}

