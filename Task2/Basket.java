package org.example;

public class Basket {
    private Toys[] buy;

    public Basket(Toys[] buy) {
        this.buy = buy;
    }

    public String getBuy() {
        String sStr = "";
        for (int i = 0; i < buy.length; i++) {
            sStr += "\n  " + this.buy[i].getId() + "  " +
                    this.buy[i].getName() + " - " +
                    this.buy[i].getQuantity() + " шт. - " +
                    this.buy[i].getProbability() + " %";
        }
        return sStr;
    }
}
