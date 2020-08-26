package Uebung3;

public class Produkte {

    private String bezeichnung;
    private Double preis;

    public Produkte(String bezeichnung, Double preis) {
        this.bezeichnung = bezeichnung;
        this.preis = preis;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public Double getPreis() {
        return preis;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    @Override
    public String toString() {
        return "Produkte{" +
                "bezeichnung='" + bezeichnung + '\'' +
                ", preis=" + preis +
                '}';
    }
}
