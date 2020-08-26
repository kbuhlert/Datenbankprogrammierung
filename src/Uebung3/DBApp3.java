package Uebung3;

import java.util.ArrayList;

public class DBApp3 {
    public static void main(String[] args) {

        DBHelper3 dbh = new DBHelper3();

        String tabellenname = "Produkte2";

        dbh.openProductDB();
        dbh.tabelleErstellen(tabellenname, " (Bezeichnung VARCHAR(50), Preis DECIMAL(7,2))");
        dbh.addProduktobjekt(new Produkte("Topfdeckel", 22.55));
        dbh.addProduktobjekt(new Produkte("Babyelefant", 5200.31));

        dbh.tabelleErstellen("Bestellungen", " (Bestelldatum DATE, Nachname VARCHAR(100))");

        ArrayList<Produkte> ergebnis = dbh.produkteAusTabelle();

        System.out.println(ergebnis);

        System.out.println();
        System.out.println("xxxxxxxxxxxxxxxxxxx------Uebung4------xxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println("Ausgabe aller Tabellen der Datenbank und deren Spalten");
        System.out.println();
        System.out.println("Lösung1 mit zweitem con-Statement ------------");
        dbh.getTablesOfDB1();
        System.out.println();
        System.out.println("Lösung2 mit Arraylist ------------");
        dbh.getTablesOfDB2();



    }
}
