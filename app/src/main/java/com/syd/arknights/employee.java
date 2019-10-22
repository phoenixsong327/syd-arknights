package com.syd.arknights;

import java.io.Serializable;

public class employee implements Serializable {
    String Name;
    int rarity;
    String location;

    public employee(String n,int r,String l) {
        Name=n;
        rarity=r;
        location=l;
    }

    public String getLocation() {
        return location;
    }

    public String getName()
    {
        return Name;
    }
    public int getRarity(){
        return rarity;
    }
    public void setName(String n){
        Name=n;
    }
    public void setRarity(int r){
        rarity=r;
    }
}
