package Uebung2;

public class DBApp {
    public static void main(String[] args) {

        DBMethoden dbProdukt = new DBMethoden();

        dbProdukt.openProductDB();
        dbProdukt.CreateTableProdukte();
        dbProdukt.AddProducts("Toilettenpapier", 4.5);
        dbProdukt.AddProducts("Desinfektionsmittel", 17.5);
        dbProdukt.AddProducts("Atemschutzmaske", 7.5);
        dbProdukt.AddProducts("Impfstoff", 57.0);
        dbProdukt.ReadAllProducts();


    }
}
