package com.example.asus.android__crud__application.Database.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.asus.android__crud__application.model.Item;
import com.example.asus.android__crud__application.Database.table.Person;
import java.util.ArrayList;

/**
 * Created by asus on 11/18/2016.
 */
public class Functions extends SQLiteOpenHelper {

    private static final String DB_NAME = "crud.db";
    private static final int DB_VERSION = 1;

    private Person person = new Person();

    public Functions(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //execute table person
        sqLiteDatabase.execSQL(person.CREATE_TABLE_PERSON);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + person.TABLE_PERSON);
    }

    //////////////start of person functions//////////////////////////////////////////////
    /**
     * insert new records
     */
    public void Insert(Item item){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(person.LASTNAME,item.getLastname());
        contentValues.put(person.FIRSTNAME,item.getFirstname());
        contentValues.put(person.MIDDLENAME,item.getMiddlename());
        contentValues.put(person.CONTACT,item.getContact());

        database.insert(person.TABLE_PERSON,null,contentValues);
    }

    /**
     * display all records
     */
    public ArrayList<Item>getAllRecords(){

        ArrayList<Item>list = new ArrayList<Item>();
        SQLiteDatabase database = getReadableDatabase();

        String sql = "SELECT * FROM " + person.TABLE_PERSON;

        Cursor cursor = database.rawQuery(sql,null);

        if (cursor.moveToFirst()){
           do {
               Item item = new Item();
               item.setId(Integer.parseInt(cursor.getString(0)));
               item.setLastname(cursor.getString(1));
               item.setFirstname(cursor.getString(2));
               item.setMiddlename(cursor.getString(3));
               item.setContact(cursor.getString(4));

               list.add(item);

           }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }

    /**
     * get single records
     */
    public Item getSingleItem(int id){

        SQLiteDatabase database = getReadableDatabase();

        String sql = "SELECT * FROM " + person.TABLE_PERSON + " WHERE " + person.ID + "=?";
        Cursor cursor = database.rawQuery(sql,new String[]{String.valueOf(id)});

        if (cursor != null)
            cursor.moveToNext();

        Item item = new Item();
        item.setId(Integer.parseInt(cursor.getString(0)));
        item.setLastname(cursor.getString(1));
        item.setFirstname(cursor.getString(2));
        item.setMiddlename(cursor.getString(3));
        item.setContact(cursor.getString(4));

        cursor.close();
        database.close();
        return item;
    }

    /**
     * delete item
     */
    public void DeleteItem(int id){

        SQLiteDatabase database = getWritableDatabase();
        database.delete(person.TABLE_PERSON,person.ID + "=?",new String[]{String.valueOf(id)});
        database.close();
    }

    /**
     *update
     */
    public void Update(Item item){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(person.LASTNAME,item.getLastname());
        contentValues.put(person.FIRSTNAME,item.getFirstname());
        contentValues.put(person.MIDDLENAME,item.getMiddlename());
        contentValues.put(person.CONTACT,item.getContact());
        database.update(person.TABLE_PERSON,contentValues,person.ID + "=?",new String[]{String.valueOf(item.getId())});

    }

    /**
     * Delete All
     */
    public void DeleteAll(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(person.TABLE_PERSON,null,null);
        database.close();
    }

    ////////////////End of Person Functions/////////////////////////////////

}
