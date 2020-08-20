package Uebung2;

import java.sql.*;

public class DBMethoden {
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


}
