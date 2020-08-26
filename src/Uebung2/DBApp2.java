package Uebung2;

public class DBApp2 {
    public static void main(String[] args) {

        DBHelper2 dbProdukt = new DBHelper2();

        dbProdukt.openProductDB();
        dbProdukt.CreateTableProdukte();
        dbProdukt.AddProducts("Toilettenpapier", 4.5);
        dbProdukt.AddProducts("Desinfektionsmittel", 17.5);
        dbProdukt.AddProducts("Atemschutzmaske", 7.5);
        dbProdukt.AddProducts("Impfstoff", 57.0);
        dbProdukt.addProduktPreparedStatement("Kuchen", 12.30);
        dbProdukt.ReadAllProducts();

        dbProdukt.createTableMitNamen("Uebung3", "(Spalte1 VARCHAR(50))" );
        dbProdukt.AddProductsToUebung2("Spalte1");
        dbProdukt.readTabelleUebung2();


    }
}
