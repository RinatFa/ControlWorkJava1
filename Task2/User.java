package org.example;

public class User {
    private String first_name;
    private String last_name;
    private Basket bask;

    public User(String first_name, String last_name, Basket bask) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.bask = bask;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getBask() {
        String sStr = "\n";
        sStr = this.bask.getBuy();
        return sStr;
    }
}
