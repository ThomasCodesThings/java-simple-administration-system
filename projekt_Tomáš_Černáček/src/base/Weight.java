package base;

public class Weight {

    private int oldAmount;
    private int newAmount;
    private double oldWeight;

    public double calculate(){
        return ((oldWeight/oldAmount) * newAmount);
    }

    public Weight(int oldAmount, double oldWeight, int newAmount){
        this.oldAmount = oldAmount;
        this.oldWeight = oldWeight;
        this.newAmount = newAmount;
    }

}
