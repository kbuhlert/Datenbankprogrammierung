package Uebung2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        String SQLCreate = "CREATE TABLE Produkte (Nettopreis DECIMAL (5,2), Bezeichnung VARCHAR(50));";
    }


}
