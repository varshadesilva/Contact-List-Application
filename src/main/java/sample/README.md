**Contact List Application**

This project involves the creation of a database host application that interfaces with a backend database implementing 
a Contact List application. The host application contains a native graphical user interface (GUI). Users are able to 
search contacts, add new contacts, edit or delete contacts through the GUI. 

_Getting Started:_

These instructions will get a copy of the application up and running on a Windows local machine.

_Setup:_ 

In order to run the Contact List application, JDK version 11 or later must be installed. Access Java SE Downloads page and Accept
License Agreement. Under the Downloads menu, click the Download link that corresponds to the .exe for your version of Windows.
To run the JDK installer, double-click the installer's icon in the download location. Follow the instructions provided by the installation wizard.

Additionaly, JavaFX version 11 or later must be installed. Access gluonhq.com and navigate to the Products page. Choose JavaFX 
and download and save the latest version of JavaFX SDK to your machine. Make sure to note the location that the SDK is saved.

Create a database named "contacts" within MySQL. Run the sqlCreateTableStatements.sql file to create the necessary tables. Mock data in contacts.csv file can be inserted into the contacts database via csvReader.py. This python script extracts data and creates insert statements that can be run in the database. 

_Running the Application:_ 

Navigate to the target folder within the main ContactList folder. Open the run.bat file in a text editor. Change the module
path to the path where the JavaFX SDK is saved on your local machine. Save and close run.bat in the text editor. 
Double-click run.bat to start the Contact List application.

_Technical Dependencies:_

Windows OS

Java version 11 or later

JavaFX version 11 or later 


_Technologies used:_

Java 11.0.6

JavaFX 11.0.2

JDBC API

Maven

Python 3.8.2

MySQL 8.0.19

