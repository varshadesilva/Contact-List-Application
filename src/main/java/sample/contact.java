package sample;

import java.sql.SQLException;

public class contact {
    String firstname;
    String middlename;
    String lastname;
    int contactid;

    public contact(){

    }
    public contact(String firstname, String middlename, String lastname) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
    }
    public contact(int contactid,String firstname, String lastname){
        this.contactid = contactid;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void insert() throws SQLException {
        Datasource datasource = new Datasource();
        contactid = datasource.insertContact(firstname,middlename,lastname);
    }

    public int getContactid() {
        return contactid;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setContactid(int contactid) {
        this.contactid = contactid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }
}
