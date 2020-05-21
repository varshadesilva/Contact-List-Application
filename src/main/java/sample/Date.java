package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;


public class Date {
    private final SimpleStringProperty dateType = new SimpleStringProperty("");
    private java.sql.Date date;
    //private final SimpleStringProperty date = new SimpleStringProperty("");



   // public Date() {
    //    this("", "");
   // }

    public Date(String dateType, java.sql.Date dates) {
        setDateType(dateType);
        setDate(dates);
    }

    public String getDateType() {
        return dateType.get();
    }

    public void setDateType(String typedate) {
        dateType.set(typedate);
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date dates) {
        date = dates;
    }

}

