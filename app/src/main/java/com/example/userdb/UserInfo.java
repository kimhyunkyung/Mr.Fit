package com.example.userdb;

/**
 * Created by H on 2015-08-20.
 */
public class UserInfo {
    //private variables
    int _id;
    String _name;
    String _height;
    String _foot_size;
    String _leg_length;

    // Empty constructor
    public UserInfo(){

    }

    // constructor
    public UserInfo(int id, String name, String height, String fsize, String llength){
        this._id = id;
        this._name = name;
        this._height = height;
        this._foot_size = fsize;
        this._leg_length = llength;
    }

    // constructor w/o id
    public UserInfo(String name, String height, String fsize, String llength){
        this._name = name;
        this._height = height;
        this._foot_size = fsize;
        this._leg_length = llength;
    }

    // getting ID
    public int getID(){ return this._id; }
    // setting id
    public void setID(int id){ this._id = id; }

    // getting name
    public String getName(){ return this._name; }
    // setting name
    public void setName(String name){ this._name = name; }

    // getting height
    public String getHeight(){ return this._height; }
    // setting height
    public void setHeight(String height){ this._height = height; }

    // getting foot size
    public String getFootSize(){ return this._foot_size; }
    // setting foot size
    public void setFootSize(String fsize){ this._foot_size = fsize; }

    // getting leg length
    public String getLegLength(){ return this._leg_length; }
    // setting leg length
    public void setLegLength(String llength){ this._leg_length = llength; }

}
