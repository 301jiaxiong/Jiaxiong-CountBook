package com.example.jiaxiong_countbook;

import android.content.ClipData;

import java.util.Date;

/**
 * Created by damon on 9/30/2017.
 */

//Item object have name, current-value ,inital value and comment
public class Item {
    private String name;
    private int value;
    private Date date;
    private String comment;
    private int init;
    public Item(String name,int value,String comment,int init){
        this.name = name;
        this.date = new Date();
        this.value = value;
        this.init = init;
        this.comment = comment;

    }

    public int getInit() {
        return init;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    @Override
    public String toString(){

        return date.toString()+" \n Item:"+ name+" \n " +
                "Intial value:"+init +"\n Current Value : "+value;
    }
}



