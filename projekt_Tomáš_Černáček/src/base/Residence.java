package base;

public abstract class Residence {
    private String townName;
    private String PSC;
    private String street;

    public String getTownName() {
        return townName;
    }

    public String getPSC() {
        return PSC;
    }

    public String getStreet() {
        return street;
    }

    public Residence(String townName, String PSC, String street){
        this.townName = townName;
        this.PSC = PSC;
        this.street = street;
    }

    public Residence() {

    }
}
