public class Main {
    public static void main(String[] args) {

        DBHelper dbh = new DBHelper();
        dbh.OpenDatabase();
        System.out.println("Hello JDB");
        dbh.CreateKundenTable();
        dbh.ReadTable();
    }
}
