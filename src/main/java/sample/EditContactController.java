package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class EditContactController {
    @FXML 
    private TextField editfirstNameField = new TextField();
    @FXML
    private TextField editmiddleNameField = new TextField();
    @FXML
    private TextField editlastNameField = new TextField();
    @FXML
    private TableView<Phone> phonetableView;
    @FXML
    private TableView<Address> addresstableView;
    @FXML
    private TableView<Date> datetableView;
    @FXML
    private TextField phoneTypeField;
    @FXML
    private TextField areaCodeField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField addressTypeField;
    @FXML
    private TextField streetAddressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField zipField;
    @FXML
    private TextField dateTypeField;
    @FXML
    private DatePicker datepicker;


    ObservableList<Phone> editphonedata = FXCollections.emptyObservableList();
    ObservableList<Address> editaddressdata = FXCollections.emptyObservableList();
    ObservableList<Date> editdatedata = FXCollections.emptyObservableList();

    ArrayList<Address> newaddressarray = new ArrayList<Address>();  //new arraylists save only new items added via edit window to the database
    ArrayList <Phone> newphonearray = new ArrayList<Phone>();
    ArrayList<Date> newdatearray = new ArrayList<Date>();


    Datasource datasource = new Datasource();
    private int contactid;

    private String firstname;
    private String middlename;
    private String lastname;

    @FXML
    public void EditSave() throws SQLException {
        String phonetype = "";
        String areacode = "";
        String phonenum = "";
        String addresstype = "";
        String address = "";
        String city = "";
        String state = "";
        String zip = "";
        String datetype = "";
        java.sql.Date date;

        String newfirstName = editfirstNameField.getText().trim();
        String newmiddleName = editmiddleNameField.getText().trim();
        String newlastName = editlastNameField.getText().trim();

        if(!firstname.equals(newfirstName)){
            datasource.editFirstname(newfirstName, contactid);
        }
        if(!middlename.equals(newmiddleName)){
            datasource.editMiddlename(newmiddleName,contactid);
        }
        if(!lastname.equals(newlastName)){
            datasource.editLastname(newlastName,contactid);
        }


        for (Date each : newdatearray) {
            datetype = each.getDateType();
            date = each.getDate();
            datasource.insertDate(contactid, datetype, date);
        }
        for (Phone each : newphonearray) {
            phonetype = each.getPhoneType();
            areacode = each.getAreaCode();
            phonenum = each.getPhoneNum();
            datasource.insertPhone(contactid, phonetype, areacode, phonenum);
        }
        for (Address each : newaddressarray) {
            addresstype = each.getAddresstype();
            address = each.getStreetaddress();
            city = each.getCity();
            state = each.getState();
            zip = each.getZip();
            datasource.insertAddress(contactid, addresstype, address, city, state, zip);
        }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Contact Updated");
            alert.setContentText("Contact is updated successfully");
            alert.showAndWait();

    }



        public void setContact(contact item) {
            firstname = item.getFirstname();
            middlename = item.getMiddlename();
            lastname = item.getLastname();
            editfirstNameField.setText(firstname);
            editmiddleNameField.setText(middlename);
            editlastNameField.setText(lastname);
            contactid = item.getContactid();
    }

    public void setPhone(ObservableList list){      //this method called from Controller.java. A list containing display data is passed to this method. This list is set to the phonetableview so that current data is displayed in edit window
        phonetableView.setItems(list);
    }

    public void setAddress(ObservableList list){
        addresstableView.setItems(list);
    }

    public void setDate(ObservableList list){
        datetableView.setItems(list);
    }


    @FXML
    public void initialize(){

    }

    @FXML
    public void editDateAdd(){
        editdatedata = datetableView.getItems();
        java.sql.Date date1 = java.sql.Date.valueOf(datepicker.getValue());
        Date newdate = new Date(dateTypeField.getText(),date1);

        editdatedata.add(newdate);
        newdatearray.add(newdate);

        dateTypeField.setText("");
    }

    @FXML
    public void editAddressAdd(){
        editaddressdata = addresstableView.getItems();
        Address newaddress = new Address(addressTypeField.getText(),streetAddressField.getText(),cityField.getText(),stateField.getText(),zipField.getText());

        editaddressdata.add(newaddress);
        newaddressarray.add(newaddress);

        addressTypeField.setText("");
        streetAddressField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipField.setText("");
    }

    @FXML
    public void editPhoneAdd(){
        editphonedata = phonetableView.getItems();  //adding elements already displayed in edit window to a new observable list
        String newphonetype = phoneTypeField.getText();   //collecting the new info that the user enters into the edit window
        String newareacode = areaCodeField.getText();
        String newphonenumber = phoneNumberField.getText();

        editphonedata.add(new Phone(newphonetype,  //creating phone object and adding it to observalist list so that old info and new info (added via edit window) is displayed via phonetableview
               newareacode,
               newphonenumber
        ));

        newphonearray.add(new Phone(newphonetype,newareacode,newphonenumber));   //creating new phone object and adding it to an arraylist. This arraylist will be looped to add new data (entered via edit window) to database in EditSave method above

        phoneTypeField.setText("");
        areaCodeField.setText("");
        phoneNumberField.setText("");
    }

    @FXML
    public void handleExit(ActionEvent event) {
        ((Node)((Node)event.getSource())).getScene().getWindow().hide();
    }


}
