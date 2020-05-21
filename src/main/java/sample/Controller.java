package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<ContactList> tableView;
    @FXML
    private TableView<Date>datetableView;
    @FXML
    private TextField dateTypeField;
    @FXML
    private DatePicker datepicker;
    @FXML
    private TableView<Address> addresstableView;
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
    private TableView<Phone> phonetableView;
    @FXML
    private TextField phoneTypeField;
    @FXML
    private TextField areaCodeField;
    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField middleNameField;
    @FXML
    private TextField lastNameField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<contact,String> firstColumn;
    @FXML
    private TableColumn<contact,String> middleColumn;
    @FXML
    private TableColumn<contact,String> lastColumn;

    @FXML
    private TextField fNameField;
    @FXML
    private TextField mNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TableView<Phone>showphonetableView;
    @FXML
    private TableView<Address>showaddresstableView;
    @FXML
    private TableView<Date>showdatetableView;

    @FXML
    private VBox mainVBox;

    private double xOffset = 0.0D;
    private double yOffset = 0.0D;

    ObservableList<Phone> phonedata = FXCollections.emptyObservableList();
    ObservableList<Address> addressdata = FXCollections.emptyObservableList();
    ObservableList<Date> datedata = FXCollections.emptyObservableList();
    ObservableList<ContactList>searchData = FXCollections.observableArrayList();
    ObservableList<Phone>displayPhone = FXCollections.observableArrayList();
    ObservableList<Address>displayAddress = FXCollections.observableArrayList();
    ObservableList<Date>displayDate = FXCollections.observableArrayList();
    Datasource datasource = new Datasource();

    ResultSet resultSetPhone;
    ResultSet resultSetAddress;
    ResultSet resultSetDate;

    contact editContactItem = new contact();




    public void showEditContact() throws SQLException {     //displays the edit dialog from the main dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/EditDialog.fxml"));
        EditContactController editController;

        try {
            Parent dialogContent = fxmlLoader.load();
            editController = fxmlLoader.<EditContactController>getController();
            editController.setContact(editContactItem);  //passes object (contains info of the contact that user has selected from search hits) to editContactController so that this info is displayed in edit window
            editController.setPhone(displayPhone);
            editController.setAddress(displayAddress);
            editController.setDate(displayDate);

            dialog.getDialogPane().setContent(dialogContent);

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);

        if (button == ButtonType.APPLY ) {
            editController.EditSave();
        }
    }


    public void deleteContact() throws SQLException{
        ContactList contact = tableView.getSelectionModel().getSelectedItem();  //selects the contact that user has selected from search hits and deletes
        if(contact!= null){
            datasource.delete(contact.getContactid());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Contact is deleted");
            alert.setContentText("Contact is deleted successfully");
            alert.showAndWait();
        }
    }

    public void showContactDetails() throws SQLException{       //displays each contact's full details on the right side of main dialog
        ContactList contact = tableView.getSelectionModel().getSelectedItem();//selects the contact that user has selected from search hits
       ResultSet resultSetContact = datasource.contactSearch(contact.getContactid());  //searches for the details of the contact that has been selected in line above
        resultSetPhone = datasource.phoneSearch(contact.getContactid());           //retrieves data from database (datasource class methods returns a resultset)
        resultSetAddress = datasource.addressSearch(contact.getContactid());
        resultSetDate = datasource.dateSearch(contact.getContactid());
        displayPhone.clear();
        displayAddress.clear();
        displayDate.clear();

        while (resultSetContact.next()){

            String firstname = resultSetContact.getString("Fname"); //retrieves data from the resultset
            String middlename = resultSetContact.getString("Mname");
            String lastname = resultSetContact.getString("Lname");
            int contactid = resultSetContact.getInt("Contact_id");
            editContactItem.setFirstname(firstname);        //passes the retrieved data to a new object (editContactItem) created above. This object will be passed to the editContactController via the showEditContact method above so that data will be displayed in the edit window
            editContactItem.setMiddlename(middlename);
            editContactItem.setLastname(lastname);
            editContactItem.setContactid(contactid);

            fNameField.setText(firstname);
            mNameField.setText(middlename);
            lNameField.setText(lastname);
        }

        while(resultSetPhone.next()){       //resultSetPhone has the data retrieved from database. New objects are created with this data and the objects are added to observable lists defined above
            displayPhone.add(new Phone(resultSetPhone.getString("Phone_type"),resultSetPhone.getString("Area_code"),resultSetPhone.getString("Phone_number")));
        }
        showphonetableView.setItems(displayPhone);  //observable list set to the table view so that info displays on right side of main dialog

        while(resultSetAddress.next()){
            displayAddress.add(new Address(resultSetAddress.getString("Address_type"),resultSetAddress.getString("Address"),resultSetAddress.getString("City"),resultSetAddress.getString("State"),resultSetAddress.getString("Zip")));
        }
        showaddresstableView.setItems(displayAddress);

        while(resultSetDate.next()){
            displayDate.add(new Date(resultSetDate.getString("Date_type"),resultSetDate.getDate("calendar_date")));
        }
        showdatetableView.setItems(displayDate);
    }



    public void showSearchResults() throws SQLException {      //displays search results in the table view below the search box
        searchData.clear();
        ResultSet resultSet = datasource.searchData(searchField.getText()); //passes string entered in search box for database search
        while (resultSet.next()) {  //results are used to create new ContactList objects and added to searchData observable list
            searchData.add(new ContactList(resultSet.getString("Fname"),resultSet.getString("Mname"),resultSet.getString("Lname"),resultSet.getInt("contact_id")));
        }
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("firstname")); //tableView columns are joined with the ContactList class instance variables
        middleColumn.setCellValueFactory(new PropertyValueFactory<>("middlename"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        tableView.setItems(searchData);

    }

    @FXML
    public void handleDateAdd(){ //handles adding new dates via the add new dialog
        datedata = datetableView.getItems();
        java.sql.Date date1 = java.sql.Date.valueOf(datepicker.getValue());
        datedata.add(new Date(dateTypeField.getText(),
                date1
        ));

        dateTypeField.setText("");
    }

    @FXML
    public void handleAddressAdd(){ //handles adding new addresses via the add new dialog
        addressdata = addresstableView.getItems();
        addressdata.add(new Address(addressTypeField.getText(),
                streetAddressField.getText(),
                cityField.getText(),
                stateField.getText(),
                zipField.getText()
        ));

        addressTypeField.setText("");
        streetAddressField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipField.setText("");
    }

    @FXML
    public void handlePhoneAdd(){  //handles adding new phone numbers from the add new dialog
            phonedata = phonetableView.getItems();
            phonedata.add(new Phone(phoneTypeField.getText(),
                    areaCodeField.getText(),
                    phoneNumberField.getText()
            ));

            phoneTypeField.setText("");
            areaCodeField.setText("");
            phoneNumberField.setText("");
        }

        @FXML
    public void handleSave() throws SQLException {  //saves data user enters into the add new contact dialog
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
        String firstName = firstNameField.getText().trim();
        String middleName = middleNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        if(!firstName.isEmpty()) {
            contact contact = new contact(firstName, middleName, lastName);
            contact.insert();

            int id = contact.getContactid();

            for (Date each : datedata) {
                datetype = each.getDateType();
                date = each.getDate();
                datasource.insertDate(id, datetype, date);
            }
            for (Phone each : phonedata) {
                phonetype = each.getPhoneType();
                areacode = each.getAreaCode();
                phonenum = each.getPhoneNum();
                datasource.insertPhone(id, phonetype, areacode, phonenum);
            }
            for (Address each : addressdata) {
                addresstype = each.getAddresstype();
                address = each.getStreetaddress();
                city = each.getCity();
                state = each.getState();
                zip = each.getZip();
                datasource.insertAddress(id, addresstype, address, city, state, zip);
            }
        }
        ((Stage)this.saveButton.getScene().getWindow()).close();
            if(firstNameField.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter at least a first name");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Contact is added");
                alert.setContentText("Contact is added successfully");
                alert.showAndWait();
            }

    }

    @FXML
    public void handleNew() throws Exception {
        this.tableView.getSelectionModel().clearSelection();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/NewDialog.fxml"));
        Stage stage = new Stage();
        root.setOnMousePressed((e) -> {
            this.xOffset = e.getSceneX();
            this.yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((e) -> {
            stage.setX(e.getScreenX() - this.xOffset);
            stage.setY(e.getScreenY() - this.yOffset);
        });
        Scene scene = new Scene(root);
        stage.setTitle("Add New");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleExit(ActionEvent event) {
        ((Node)((Node)event.getSource())).getScene().getWindow().hide();
    }

}
