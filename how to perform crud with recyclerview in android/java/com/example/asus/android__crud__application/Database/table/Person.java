package com.example.asus.android__crud__application.Database.table;

/**
 * Created by asus on 11/18/2016.
 */
public class Person {

    /**
     * create table person
     */
    public static final String TABLE_PERSON = "person";


    /**
     * create fields
     */
    public static final String ID = "id";
    public static final String LASTNAME = "lastname";
    public static final String FIRSTNAME = "firstname";
    public static final String MIDDLENAME = "middlename";
    public static final String CONTACT = "contact";

    /**
     * create table
     */
    public static final String CREATE_TABLE_PERSON = "CREATE TABLE " + TABLE_PERSON + "("
    + ID + " INTEGER PRIMARY KEY,"
    + LASTNAME + " TEXT,"
    + FIRSTNAME + " TEXT,"
    + MIDDLENAME + " TEXT,"
    + CONTACT + " TEXT" + ")";

}
