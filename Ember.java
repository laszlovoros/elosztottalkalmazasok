public class Ember {


    protected String nev;
    protected String azonosito;

    public Ember() {
        nev = null;
        azonosito = null;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setAzonosito(String azonosito) {
        this.azonosito = azonosito;
    }

    public String getNev() {
        return nev;
    }

    public String getAzonosito() {
        return azonosito;
    }

    public Ember(String name, String ID) {
        this.nev = name;
        this.azonosito = ID;
    }


    public String toString() {
        return nev + " [" + azonosito + "]";
    }
}
