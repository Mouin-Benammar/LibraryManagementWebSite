package com.ensta.librarymanager.model;

import java.util.NoSuchElementException;

public enum Abonnement {
    BASIC("BASIC", 2),PREMIUM("PREMIUM", 5),VIP("VIP", 20);
    private  String val;
    private int quota;
    private Abonnement(String s,int x){
        this.val=s;
        this.quota=x;
    }
    public static Abonnement fromInt(int value) {
        for (Abonnement o : Abonnement.values()) {
            if (o.quota == value) {
                return o;
            }
        }
        throw new NoSuchElementException("no enum for value " + value);
    }
    public int getQuota() {
 return this.quota;

    }
    public String toString() {
        return this.val;
    }
}
