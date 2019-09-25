package com.example.arknights;

import java.io.Serializable;

public class employee implements Serializable {
    String Name;
    int rarity;
    public employee(String n,int r)
    {
        Name=n;
        rarity=r;
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
