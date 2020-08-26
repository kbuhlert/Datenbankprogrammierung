package Uebung2;

import java.sql.*;

public class DBHelper2 {
    private Connection con = null;

    public void openProductDB (){
        String connection = "jdbc:sqlite:dbProdukteUe2";

        try {
            con = DriverManager.getConnection(connection);
            System.out.println("mit Datenbank dbProdukteUe2 verbunden");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void CreateTableProdukte(){
        String SQLCreateTable = "CREATE TABLE Produkte (Bezeichnung VARCHAR(50), Nettopreis DECIMAL(5,2));";

        try {
            Statement stm = con.createStatement();

            stm.executeUpdate("DROP TABLE Produkte");
            stm.executeUpdate(SQLCreateTable);
            System.out.println("Tabelle wurde erstellt");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean tableExists (String tablename){
        boolean tableExists = false;

        //SQL-Abfrage, die Anzahl (=count(*) as Anzahl) der enthaltenen Objekte (Tabellen) in der Datenbank ausgibt
        // hier wird nach Tabellennamen eingeschränkt also Ausgabe, ob bestimmte Tabelle enthalten ist
        String SQLTabellenzahl = " SELECT count(*) as Anzahl FROM sqlite_master WHERE type='table' and name='" + tablename + "'";

        //Um die Anzahl zu erhalten muss von der Verbindung zur Datenbank (con) ein Statement erzeugt werden,
        // dass dann die SQL-Anfrage ausführt
        // und das Ergebnis der Anfrage in einem Resultset speichert

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(SQLTabellenzahl);

            //das Ergebnis (Resultset) enthält die Anzahl der Tabellen mit dem gesuchten Tabellenamen (als Parameter übergeben) in der Datenbank,
            // wenn die Tabelle nicht existiert ist das Ergebnis = 0
            // um diesen Wert aus dem ResultSet auslesen zu können, muss in die erste Zeile des Resultsets (mit rs.next) gegangen werden

            rs.next();
            //jetzt wird geschaut, ob eine Tabelle mit dem Namen mit dem Query-Statement gefunden wurde
            // hierfür wird der int-Wert aus der Spalte Anzahl verglichen (keine Tabelle: Anzahl == 0)
            if (rs.getInt("Anzahl")==0)
                tableExists=false;
            else
                tableExists=true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tableExists;
    }

    //Tabelle wir mit Namen als Parameter und Spaltenbezeichnungen als Parameter (=SpaltenDDL) erstellt,
    // auf diesem Weg kann auch gestestet werden, ob Tabelle bereits existiert, dann wird Vorgängertabelle gelöscht
    public void createTableMitNamen(String tabellenname, String SpaltenDDL){

        String SQLCreateTable = "CREATE TABLE " + tabellenname + SpaltenDDL;

        if(tableExists(tabellenname)==false){
            try {
                Statement stm = con.createStatement();
                stm.executeUpdate(SQLCreateTable);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void AddProducts (String bezeichnung, Double preis){
        String SQLInsert = " INSERT INTO Produkte VALUES('" + bezeichnung + "', " + preis + ")";

        try {
            Statement stm = con.createStatement();
            stm.executeUpdate(SQLInsert);
            System.out.println("Produkt " + bezeichnung + " wurde zur Tabelle zugefügt.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void ReadAllProducts(){
        System.out.println();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println();

        String query = "SELECT * FROM Produkte";
        String queryAvgPreis = "SELECT AVG(Nettopreis) FROM Produkte";

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            //Ausgabe der Spaltenbezeichnungen:
            int colums = rs.getMetaData().getColumnCount();
            for (int i=1; i<=colums; i++){
                System.out.print(String.format("%-30s", rs.getMetaData().getColumnLabel(i)));
            }
            System.out.println();
            System.out.println("-------------------------------------------------");

            //Ausgabe des resultsets (=die in der Tabelle enthaltenen Daten)
            Double gesamtpreis = 0.0;
            int counter = 0;
            while (rs.next()){
                double preis = rs.getDouble("Nettopreis");
                gesamtpreis += preis;
                counter++;

                //Ausgabe der Spalteninhalte (Werte in der Tabelle)
                for (int i=1; i<=colums; i++){
                    System.out.print(String.format("%-30s", rs.getString(i)));
                }
                System.out.println();
            }

            //Ausgabe des Durschnittspreises mit Rechnung
            Double averagePreis = gesamtpreis/counter;
            System.out.println();
            System.out.println("Durchschnittspreis der Produkte: " + averagePreis + "€");

            //Ausgabe Durschnittspreis mit Query
            Statement stm2 = con.createStatement();
            ResultSet rs2 = stm2.executeQuery(queryAvgPreis);
            while (rs2.next()){
                double avgPreis = rs2.getDouble(1);
                System.out.println("Durchschnittspreis der Produkte: " + avgPreis + "€");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    //Methoden zum Ausprobieren, ob Tabelle mit variablen Namen erstellt werden konnte

    public void AddProductsToUebung2 (String spalte1){
        String SQLInsert = " INSERT INTO Uebung3 VALUES('" + spalte1 + "')";

        try {
            Statement stm = con.createStatement();
            stm.executeUpdate(SQLInsert);
            System.out.println("Produkt " + spalte1 + " wurde zur Tabelle zugefügt.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void readTabelleUebung2(){
        String select="";
        select += "SELECT rowid, Spalte1 ";
        select += " FROM Uebung3";
        try {
            Statement stmt= con.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            while (rs.next()){
                System.out.println(rs.getInt("rowid")
                        + " " + rs.getString("Spalte1"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addProduktPreparedStatement(String bezeichnung, double preis)
    {
        String insertSQL = "INSERT INTO Produkte VALUES(?,?)";

        //INSERT INTO Produkte VALUES('Handy',20)

        try {
            PreparedStatement stmt= con.prepareStatement(insertSQL);
            stmt.setString(1,bezeichnung);
            stmt.setDouble(2,preis);
            stmt.executeUpdate();
            System.out.println(bezeichnung + " wurde hinzugefügt");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



}
