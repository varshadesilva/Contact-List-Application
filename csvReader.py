import csv

with open('Contacts.csv') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    f = open("sqlFile.txt", "a")

    for row in csv_reader:
        if line_count != 0:
            f.write(f'INSERT INTO contact (Contact_id, Fname, Mname, Lname) VALUES ({row[0]},"{row[1]}","{row[2]}","{row[3]}");\n')
            if row[4] != "":
                phone_type = "Home"
                area_code = row[4][0:3]
                phone_number = row[4][4:]
                f.write(f'INSERT INTO phone (Contact_id, Phone_type, Area_code, Phone_number) VALUES ({row[0]},"{phone_type}","{area_code}","{phone_number}");\n')
            if row[5] != "":
                phone_type = "Cell"
                area_code = row[5][0:3]
                phone_number = row[5][4:]
                f.write(f'INSERT INTO phone (Contact_id, Phone_type, Area_code, Phone_number) VALUES ({row[0]},"{phone_type}","{area_code}","{phone_number}");\n')
            if row[10] != "":
                phone_type = "Work"
                area_code = row[10][0:3]
                phone_number = row[10][4:]
                f.write(f'INSERT INTO phone (Contact_id, Phone_type, Area_code, Phone_number) VALUES ({row[0]},"{phone_type}","{area_code}","{phone_number}");\n')
            if row[6] != "" or row[7] != "" or row[8] != "" or row[9] != "":
                address_type = "Home"
                home_address = row[6]
                home_city = row[7]
                home_state = row[8]
                home_zip = row[9]
                f.write(f'INSERT INTO address (Contact_id, Address_type, Address, City, State, Zip) VALUES ({row[0]},"{address_type}","{home_address}","{home_city}","{home_state}","{home_zip}");\n')
            if row[11] != "" or row[12] != "" or row[13] != "" or row[14] != "":
                address_type = "Work"
                work_address = row[11]
                work_city = row[12]
                work_state = row[13]
                work_zip = row[14]
                f.write(f'INSERT INTO address (Contact_id, Address_type, Address, City, State, Zip) VALUES ({row[0]},"{address_type}","{work_address}","{work_city}","{work_state}","{work_zip}");\n')
            if row[15] != "":
                date_type = "Birthday"
                date = row[15].rsplit('/')
                sql_date = date[0]
                #day = date[1]
                #year = date[2]
                #sql_date = month+day+year
                f.write(f'INSERT INTO dates (Contact_id, Date_type, calendar_date) VALUES ({row[0]},"{date_type}","{sql_date}");\n')
        line_count += 1









