package com.example.tobibur.journalapp.model;

/**
 * Created by admin on 2017-10-11.
 */

public class Journal {
    //private variables
    private int _id;
    private String _Journal_data;
    private String _date_time;

    // Empty constructor
    public Journal(){

    }
    // constructor
    public Journal(int id, String Journal_data, String date_time){
        this._id = id;
        this._Journal_data = Journal_data;
        this._date_time = date_time;
    }

    // constructor
    public Journal(String Journal_data, String date_time){
        this._Journal_data = Journal_data;
        this._date_time = date_time;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting post
    public String get_Journal_data(){
        return this._Journal_data;
    }

    // setting post
    public void set_Journal_data(String co_id){
        this._Journal_data = co_id;
    }

    // getting date_time
    public String get_date_time(){
        return this._date_time;
    }

    // setting date_time
    public void set_date_time(String date_time){
        this._date_time = date_time;
    }
    
}
