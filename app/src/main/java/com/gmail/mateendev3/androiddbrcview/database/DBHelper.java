package com.gmail.mateendev3.androiddbrcview.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.gmail.mateendev3.androiddbrcview.model.Student;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //declaring members
    public static final String DB_NAME = "student.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "STUDENTS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_STUDENT_ROLL_NO = "STUDENT_ROLL_NO";
    public static final String COLUMN_STUDENT_NAME = "STUDENT_NAME";

    //public constructor
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table in db
        //query to create table
        final String CREATE_TABLE_QUERY = "create table " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER Primary Key AutoIncrement, " + COLUMN_STUDENT_ROLL_NO + " INTEGER, " +
                COLUMN_STUDENT_NAME + " TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    /**
     * method to insert record to DB
     *
     * @param student data object to add to db
     * @return true if data inserted successfully else false
     */
    public boolean insertData(Student student) {
        //getting instance of db
        SQLiteDatabase db = getWritableDatabase();
        //ContentValues obj to insert data to db
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_ROLL_NO, student.getRollNo());
        cv.put(COLUMN_STUDENT_NAME, student.getName());
        //inserting cv to db
        long inserted = db.insert(TABLE_NAME, null, cv);
        return inserted != -1;
    }

    /**
     * method to get data from the db in list
     * @return list of the student from db
     */
    public List<Student> getData() {
        //creating instance of List
        List<Student> studentList = new ArrayList<>();
        //getting instance of db
        SQLiteDatabase db = getReadableDatabase();
        //creating Cursor object with given command/query
        final String GET_DATA_QUERY = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(GET_DATA_QUERY, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                studentList.add(new Student(cursor.getInt(0), cursor.getInt(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        return studentList;
    }

    public boolean deleteRecord(Student student) {
        //String Array for args
        String[] args = {Integer.toString(student.getId())};
        //getting instance of db
        SQLiteDatabase db = getWritableDatabase();
        //query for checking elements
        final String DELETE_RECORD_QUERY = "select * from " + TABLE_NAME + " where " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(DELETE_RECORD_QUERY, args);
        if (cursor.getCount() != 0) {
            int deleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", args);
            return deleted == 1;
        } else
            return false;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Student> searchPerson(int rollNo) {
        List<Student> studentList = new ArrayList<>();
        String[] args = {Integer.toString(rollNo)};
        //getting instance of db
        SQLiteDatabase db = getReadableDatabase();
        //query to get data
        final String FIND_PERSON_QUERY = "select * from " + TABLE_NAME + " where " + COLUMN_STUDENT_ROLL_NO + " = ?";
        Cursor cursor = db.rawQuery(FIND_PERSON_QUERY, args);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                studentList.add(new Student(cursor.getInt(0),
                        cursor.getInt(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        return studentList;
    }
}
