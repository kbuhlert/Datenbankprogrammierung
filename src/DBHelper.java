import java.sql.*;

public class DBHelper {

    private Connection con = null;

    public void OpenDatabase(){

        //Erstellen der Datenbankverbindung, Zugriff auf Datenbank (hier: dbKerstin) --> keine physikalische DB, sondern eine inMemory DB im Hauptspeicher

        String connectionString = "jdbc:sqlite:dbKerstin";  //sqlite = Treiber, wird anhand des Connectionstrings geladen, Treiber wurde in Libary geladen
        // und ist spezifisch für Datenbank (Treiber = Übersetzer)
        try {
            con = DriverManager.getConnection(connectionString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void CreateKundenTable(){
        String createTable = "CREATE TABLE Kunden(Vorname varchar(20));";

        //Statement ist kapselung von SQL Anweisungen in JDBC

        try {
            //Statement erzeugen
            Statement stm = con.createStatement();

            //Tabelle wird gelöscht, falls sie existiert, damit kein fehler bei zweiter Ausführung ausgegeben wird, dabei würden aber enthaltene Daten verloren gehen
            stm.executeUpdate("DROP TABLE Kunden");

            //Statement an Datenbank abschicken
            stm.executeUpdate(createTable); //immer wenn etwas erzeugt wird (Create, Insert, Delete), wird .executeUpdate verwendet, bei SELECT .executeQuery
            stm.executeUpdate("INSERT INTO Kunden VALUES('Aiste')");
            stm.executeUpdate("INSERT INTO Kunden VALUES('Emina')");
            stm.executeUpdate("INSERT INTO Kunden VALUES('Kerstin')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void ReadTable(){
        try {
            Statement stm = con.createStatement();
            ResultSet rs =  stm.executeQuery("SELECT rowid, vorname FROM Kunden");

            //ResultSet durchlaufen mit while-Schleife

            while (rs.next()){
                int rowid = rs.getInt("rowid");
                String vorname = rs.getString("vorname");
                System.out.println("RowId " + rowid + " Vorname " + vorname);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
