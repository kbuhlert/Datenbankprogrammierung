package Uebung3;

import java.sql.*;
import java.util.ArrayList;

public class DBHelper3 {

    private Connection con = null;

    public void openProductDB() {
        String connection = "jdbc:sqlite:dbProdukteUe3";

        try {
            con = DriverManager.getConnection(connection);
            System.out.println("mit Datenbank dbProdukteUe3 verbunden");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean tabelleVorhanden(String tablename) {
        boolean tableExists = false;

        String SQLTabellenzahl = " SELECT count(*) as Anzahl FROM sqlite_master WHERE type='table' and name='" + tablename + "'";

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(SQLTabellenzahl);

            rs.next();
            if (rs.getInt("Anzahl") != 0)
                tableExists = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tableExists;
    }

    public void tabelleErstellen(String tabellenname, String SpaltenSQL) {

        String SQLCreateTable = "CREATE TABLE " + tabellenname + SpaltenSQL;

        if (!tabelleVorhanden(tabellenname)) {
            try {
                Statement stm = con.createStatement();
                stm.executeUpdate(SQLCreateTable);
                System.out.println("Tabelle " + tabellenname + " wurde erstellt");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void addProduktobjekt(Produkte p1) {
        String insertSQL = "INSERT INTO Produkte2 VALUES(?,?)";

        String bezeichnung = p1.getBezeichnung();
        Double preis = p1.getPreis();

        try {
            PreparedStatement stmt = con.prepareStatement(insertSQL);
            stmt.setString(1, bezeichnung);
            stmt.setDouble(2, preis);
            stmt.executeUpdate();
            System.out.println("Das Produkt " + bezeichnung + " wurde der Tabelle hinzugefügt");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public ArrayList<Produkte> produkteAusTabelle() {
        ArrayList<Produkte> produktliste = new ArrayList<>();

        String selectSQL = "SELECT Bezeichnung, Preis FROM Produkte2";

        try {
            PreparedStatement stmt = con.prepareStatement(selectSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String bezeichnung = rs.getString("Bezeichnung");
                Double preis = rs.getDouble("Preis");

                Produkte p = new Produkte(bezeichnung, preis);
                produktliste.add(p);

                System.out.println("Produkt " + bezeichnung + " wurde in Liste aufgenommen.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return produktliste;
    }

    public void getTablesOfDB1() {
        //Lösung mit verschachtelten Loops und zweitem Resultset
        String SQLSelectAllTable = " SELECT * FROM sqlite_master WHERE type='table'";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLSelectAllTable);

            while (rs.next()) {
                //Ausgabe der Tabellennamen in der Datenbank
                String tabellenname = rs.getString("Name");
                System.out.println("Tabellenname: " + tabellenname);

                //Holt Spaltennamen aus der Tabelle
                ResultSet rs2 = con.createStatement().executeQuery("SELECT * FROM " + tabellenname);
                    int spaltenzahl = rs2.getMetaData().getColumnCount();
                    for (int i = 1; i <= spaltenzahl; i++) {
                        System.out.println("Spalte " + i + ": " + rs2.getMetaData().getColumnLabel(i));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getTablesOfDB2() {

        //Lösung mit ArrayList

        String SQLSelectAllTable = " SELECT * FROM sqlite_master WHERE type='table'";
        ArrayList<String> tabellenListe = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQLSelectAllTable);

            while (rs.next()) {
                //Ausgabe der Tabellennamen in der Datenbank
                String tabellenname = rs.getString("Name");
                tabellenListe.add(tabellenname);
            }

            //Holen der Spaltennamen aus der Tabelle
            for(String s: tabellenListe){
                String tabelle = s;
                System.out.println("Tabelle: " + s);
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM " + tabelle);
            int spaltenzahl = rs2.getMetaData().getColumnCount();
            for(int i=1; i<=spaltenzahl; i++) {
                    System.out.println("Spalte " + i + ": " + rs2.getMetaData().getColumnLabel(i));
            }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
