package sample;

import javafx.beans.property.SimpleStringProperty;


public class Phone {
    private final SimpleStringProperty phoneType = new SimpleStringProperty("");
    private final SimpleStringProperty areaCode = new SimpleStringProperty("");
    private final SimpleStringProperty phoneNum = new SimpleStringProperty("");

    public Phone() {
        this("", "", "");
    }

    public Phone(String phoneType, String areaCode, String phoneNum) {
        setPhoneType(phoneType);
        setAreaCode(areaCode);
        setPhoneNum(phoneNum);
    }

    public String getPhoneType() {
        return phoneType.get();
    }

    public void setPhoneType(String phonetype) {
        phoneType.set(phonetype);
    }

    public String getAreaCode() {
        return areaCode.get();
    }

    public void setAreaCode(String areacode) {
        areaCode.set(areacode);
    }

    public String getPhoneNum() {
        return phoneNum.get();
    }

    public void setPhoneNum(String phonenum) {
        phoneNum.set(phonenum);
    }
}
