package sample;
import java.sql.*;
import java.sql.Date;


public class Datasource {

    private static final String url = "jdbc:mysql://localhost:3306/contacts";
    private static final String user = "root";
    private static final String password = "varsha2030";
    private static Connection con;

    public static final String TABLE_CONTACT = "contact";
    public static final String COLUMN_CONTACT_ID = "Contact_id";
    public static final String COLUMN_CONTACT_FNAME = "Fname";
    public static final String COLUMN_CONTACT_MNAME = "Mname";
    public static final String COLUMN_CONTACT_LNAME = "Lname";

    public static final String TABLE_DATE = "dates";
    public static final String COLUMN_DATETYPE = "Date_type";
    public static final String COLUMN_CALENDARDATE = "calendar_date";

    public static final String TABLE_ADDRESS = "address";
    public static final String COLUMN_ADDRESS_ADDRESSTYPE = "Address_type";
    public static final String COLUMN_ADDRESS_ADDRESS = "Address";
    public static final String COLUMN_ADDRESS_CITY = "City";
    public static final String COLUMN_ADDRESS_STATE = "State";
    public static final String COLUMN_ADDRESS_ZIP = "Zip";

    public static final String TABLE_PHONE = "phone";
    public static final String COLUMN_PHONETYPE = "Phone_type";
    public static final String COLUMN_PHONE_AREACODE = "Area_code";
    public static final String COLUMN_PHONE_PHONENUMBER = "Phone_number";


    public static final String INSERT_CONTACT = "INSERT INTO " + TABLE_CONTACT +
            '(' + COLUMN_CONTACT_FNAME + ", " + COLUMN_CONTACT_MNAME + ", " + COLUMN_CONTACT_LNAME + ") VALUES(?, ?, ?)";

    public static final String INSERT_PHONE = "INSERT INTO " + TABLE_PHONE +
            '(' + COLUMN_CONTACT_ID + ", " + COLUMN_PHONETYPE + ", " + COLUMN_PHONE_AREACODE + ", " + COLUMN_PHONE_PHONENUMBER + ") VALUES(?, ?, ?, ?)";

    public static final String INSERT_ADDRESS = "INSERT INTO " + TABLE_ADDRESS +
            '(' + COLUMN_CONTACT_ID + ", " + COLUMN_ADDRESS_ADDRESSTYPE + ", " + COLUMN_ADDRESS_ADDRESS + ", " + COLUMN_ADDRESS_CITY + ", " + COLUMN_ADDRESS_STATE + ", " + COLUMN_ADDRESS_ZIP + ") VALUES(?, ?, ?, ?, ?, ?)";

    public static final String INSERT_DATE = "INSERT INTO " + TABLE_DATE +
            '(' + COLUMN_CONTACT_ID + ", " + COLUMN_DATETYPE + ", " + COLUMN_CALENDARDATE + ") VALUES(?, ?, ?)";

    public static final String SEARCH = "SELECT " + COLUMN_CONTACT_FNAME + ", " + COLUMN_CONTACT_MNAME + ", " + COLUMN_CONTACT_LNAME + ", " + COLUMN_CONTACT_ID + " FROM " +
            TABLE_CONTACT + " WHERE " + COLUMN_CONTACT_ID + " IN(SELECT " + COLUMN_CONTACT_ID + " FROM " + TABLE_PHONE + " WHERE " + COLUMN_PHONETYPE +
            " LIKE ? OR " + COLUMN_PHONE_AREACODE + " LIKE ? OR " + COLUMN_PHONE_PHONENUMBER + " LIKE ? UNION ALL SELECT " + COLUMN_CONTACT_ID +
            " FROM " + TABLE_ADDRESS + " WHERE " + COLUMN_ADDRESS_ADDRESSTYPE + " LIKE ? OR " + COLUMN_ADDRESS_ADDRESS + " LIKE ? OR " +
            COLUMN_ADDRESS_CITY + " LIKE ? OR " + COLUMN_ADDRESS_STATE + " LIKE ? OR " + COLUMN_ADDRESS_ZIP + " LIKE ? UNION ALL SELECT " +
            COLUMN_CONTACT_ID + " FROM " + TABLE_CONTACT + " WHERE " + COLUMN_CONTACT_FNAME + " LIKE ? OR " + COLUMN_CONTACT_MNAME + " LIKE ? OR " +
            COLUMN_CONTACT_LNAME + " LIKE ?)";

    public static final String QUERY_CONTACT = "SELECT " + COLUMN_CONTACT_ID + ", "+ COLUMN_CONTACT_FNAME + ", " + COLUMN_CONTACT_MNAME + ", " +
            COLUMN_CONTACT_LNAME + " FROM " + TABLE_CONTACT +
            " WHERE " + COLUMN_CONTACT_ID + " = ?";

    public static final String QUERY_PHONE = "SELECT " + COLUMN_PHONETYPE + ", " + COLUMN_PHONE_AREACODE + ", " + COLUMN_PHONE_PHONENUMBER +
            " FROM " + TABLE_PHONE + " WHERE " + TABLE_PHONE + "." + COLUMN_CONTACT_ID + " = ?";

    public static final String QUERY_ADDRESS = "SELECT " + COLUMN_ADDRESS_ADDRESSTYPE + ", " + COLUMN_ADDRESS_ADDRESS + ", " +
            COLUMN_ADDRESS_CITY + ", " + COLUMN_ADDRESS_STATE + ", " + COLUMN_ADDRESS_ZIP +
            " FROM " + TABLE_ADDRESS + " WHERE " + TABLE_ADDRESS + "." + COLUMN_CONTACT_ID + " = ?";

    public static final String QUERY_DATES = "SELECT " + COLUMN_DATETYPE + ", " + COLUMN_CALENDARDATE + " FROM " + TABLE_DATE + " WHERE " +
            TABLE_DATE + "." + COLUMN_CONTACT_ID + " = ?";

    public static final String DELETE_ADDRESS = "DELETE FROM " + TABLE_ADDRESS + " WHERE " + TABLE_ADDRESS + "." + COLUMN_CONTACT_ID + " = ?";
    public static final String DELETE_PHONE = "DELETE FROM " + TABLE_PHONE + " WHERE " + TABLE_PHONE + "." + COLUMN_CONTACT_ID + " = ?";
    public static final String DELETE_DATES = "DELETE FROM " + TABLE_DATE + " WHERE " + TABLE_DATE + "." + COLUMN_CONTACT_ID + " = ?";
    public static final String DELETE_CONTACT = "DELETE FROM " + TABLE_CONTACT + " WHERE " + TABLE_CONTACT + "." + COLUMN_CONTACT_ID + " = ?";

    public static final String UPDATE_FIRSTNAME = "UPDATE " + TABLE_CONTACT + " SET " + COLUMN_CONTACT_FNAME + " = ? WHERE " +
            COLUMN_CONTACT_ID + " = ?";

    public static final String UPDATE_MIDDLENAME = "UPDATE " + TABLE_CONTACT + " SET " + COLUMN_CONTACT_MNAME + " = ? WHERE " +
            COLUMN_CONTACT_ID + " = ?";

    public static final String UPDATE_LASTNAME = "UPDATE " + TABLE_CONTACT + " SET " + COLUMN_CONTACT_LNAME + " = ? WHERE " +
            COLUMN_CONTACT_ID + " = ?";

    private PreparedStatement insertIntoContact;
    private PreparedStatement insertIntoPhone;
    private PreparedStatement insertIntoAddress;
    private PreparedStatement insertIntoDate;
    private PreparedStatement searchQuery;
    private PreparedStatement searchContact;
    private PreparedStatement searchPhone;
    private PreparedStatement searchAddress;
    private PreparedStatement searchDates;
    private PreparedStatement deleteAddress;
    private PreparedStatement deletePhone;
    private PreparedStatement deleteDates;
    private PreparedStatement deleteContact;
    private PreparedStatement updateFirstname;
    private PreparedStatement updateMiddlename;
    private PreparedStatement updateLastname;

    private static Datasource instance = new Datasource();

    public Datasource() {

    }

    public static Datasource getInstance() {
        return instance;
    }

    public boolean open() {
        try {

            con = DriverManager.getConnection(url,user,password);

            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if(insertIntoContact != null) {
                insertIntoContact.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public void editFirstname(String firstname, int contactid) throws SQLException{
        updateFirstname = con.prepareStatement(UPDATE_FIRSTNAME,Statement.RETURN_GENERATED_KEYS);
        updateFirstname.setString(1,firstname);
        updateFirstname.setInt(2,contactid);
        updateFirstname.execute();
    }
    public void editMiddlename(String middlename, int contactid) throws SQLException{
        updateMiddlename = con.prepareStatement(UPDATE_MIDDLENAME,Statement.RETURN_GENERATED_KEYS);
        updateMiddlename.setString(1,middlename);
        updateMiddlename.setInt(2,contactid);
        updateMiddlename.execute();
    }
    public void editLastname(String lastname, int contactid) throws SQLException{
        updateLastname = con.prepareStatement(UPDATE_LASTNAME,Statement.RETURN_GENERATED_KEYS);
        updateLastname.setString(1,lastname);
        updateLastname.setInt(2,contactid);
        updateLastname.execute();
    }

    public void delete(int contactid) throws SQLException{
        deletePhone = con.prepareStatement(DELETE_PHONE,Statement.RETURN_GENERATED_KEYS);
        deletePhone.setInt(1,contactid);
        deletePhone.execute();

        deleteAddress = con.prepareStatement(DELETE_ADDRESS,Statement.RETURN_GENERATED_KEYS);
        deleteAddress.setInt(1,contactid);
        deleteAddress.execute();

        deleteDates = con.prepareStatement(DELETE_DATES,Statement.RETURN_GENERATED_KEYS);
        deleteDates.setInt(1,contactid);
        deleteDates.execute();

        deleteContact = con.prepareStatement(DELETE_CONTACT,Statement.RETURN_GENERATED_KEYS);
        deleteContact.setInt(1,contactid);
        deleteContact.execute();
    }

    public ResultSet dateSearch(int contactid) throws SQLException{
        searchDates = con.prepareStatement(QUERY_DATES,Statement.RETURN_GENERATED_KEYS);
        searchDates.setInt(1,contactid);
        ResultSet resultSet = searchDates.executeQuery();
        return resultSet;
    }
    public ResultSet addressSearch(int contactid) throws SQLException{
        searchAddress = con.prepareStatement(QUERY_ADDRESS,Statement.RETURN_GENERATED_KEYS);
        searchAddress.setInt(1,contactid);
        ResultSet resultSet = searchAddress.executeQuery();
        return resultSet;
    }

    public ResultSet phoneSearch(int contactid) throws SQLException{
        searchPhone = con.prepareStatement(QUERY_PHONE,Statement.RETURN_GENERATED_KEYS);
        searchPhone.setInt(1,contactid);
        ResultSet resultSet = searchPhone.executeQuery();
        return resultSet;
    }

    public ResultSet contactSearch(int contactid) throws SQLException{
        searchContact = con.prepareStatement(QUERY_CONTACT, Statement.RETURN_GENERATED_KEYS);
        searchContact.setInt(1,contactid);
        ResultSet resultSet = searchContact.executeQuery();
        return resultSet;
    }

    public ResultSet searchData(String searchString) throws  SQLException{
        searchQuery = con.prepareStatement(SEARCH, Statement.RETURN_GENERATED_KEYS);
        searchQuery.setString(1, "%" + searchString + "%");
        searchQuery.setString(2, "%" + searchString + "%");
        searchQuery.setString(3, "%" + searchString + "%");
        searchQuery.setString(4, "%" + searchString + "%");
        searchQuery.setString(5, "%" + searchString + "%");
        searchQuery.setString(6, "%" + searchString + "%");
        searchQuery.setString(7, "%" + searchString + "%");
        searchQuery.setString(8, "%" + searchString + "%");
        searchQuery.setString(9, "%" + searchString + "%");
        searchQuery.setString(10, "%" + searchString + "%");
        searchQuery.setString(11, "%" + searchString + "%");

        ResultSet resultSet = searchQuery.executeQuery();
        return resultSet;
    }

    public void insertDate(int contactid, String datetype, Date calendardate) throws SQLException {

        insertIntoDate = con.prepareStatement(INSERT_DATE, Statement.RETURN_GENERATED_KEYS);

        insertIntoDate.setInt(1, contactid);
        insertIntoDate.setString(2, datetype);
        insertIntoDate.setDate(3, calendardate);

        int affectedRows = insertIntoDate.executeUpdate();

        if(affectedRows != 1) {
            throw new SQLException("Couldn't insert date");
        }
    }


    public void insertAddress(int contactid, String addresstype, String address, String city, String state, String zip) throws SQLException {
        insertIntoAddress = con.prepareStatement(INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS);

        insertIntoAddress.setInt(1, contactid);
        insertIntoAddress.setString(2, addresstype);
        insertIntoAddress.setString(3, address);
        insertIntoAddress.setString(4, city);
        insertIntoAddress.setString(5, state);
        insertIntoAddress.setString(6, zip);

        int affectedRows = insertIntoAddress.executeUpdate();

        if(affectedRows != 1) {
            throw new SQLException("Couldn't insert address");
        }
    }

    public void insertPhone(int contactid, String phonetype, String areacode, String phonenum) throws SQLException {

        insertIntoPhone = con.prepareStatement(INSERT_PHONE, Statement.RETURN_GENERATED_KEYS);

        insertIntoPhone.setInt(1, contactid);
        insertIntoPhone.setString(2, phonetype);
        insertIntoPhone.setString(3, areacode);
        insertIntoPhone.setString(4, phonenum);

        int affectedRows = insertIntoPhone.executeUpdate();

        if(affectedRows != 1) {
            throw new SQLException("Couldn't insert phone");
        }
    }

    public int insertContact(String fname, String mname, String lname) throws SQLException {

            insertIntoContact = con.prepareStatement(INSERT_CONTACT, Statement.RETURN_GENERATED_KEYS);

            insertIntoContact.setString(1, fname);
            insertIntoContact.setString(2, mname);
            insertIntoContact.setString(3, lname);

            int affectedRows = insertIntoContact.executeUpdate();

            if(affectedRows != 1) {
                throw new SQLException("Couldn't insert contact");
            }

            ResultSet generatedKeys = insertIntoContact.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for contact");
            }
    }


}
