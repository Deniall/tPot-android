package com.cpssd.organizr.pojos.Settings;

/**
 * Created by Niall on 4/13/2017.
 */

public class CuisinePref {
    String name;
    int value;

    public CuisinePref(String name, int value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }

}
