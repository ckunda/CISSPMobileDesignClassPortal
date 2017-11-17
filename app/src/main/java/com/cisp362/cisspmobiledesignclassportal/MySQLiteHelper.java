package com.cisp362.cisspmobiledesignclassportal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "AppVisitorDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create AppVisitor table
        String CREATE_AppVisitor_TABLE = "CREATE TABLE AppVisitors ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT," +
                "language TEXT, " +
                "studentType TEXT, " +
                "date TEXT, " +
                "time TEXT, " +
                "rating TEXT )";

        // create AppVisitors table
        db.execSQL(CREATE_AppVisitor_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older AppVisitors table if exists
        db.execSQL("DROP TABLE IF EXISTS AppVisitors");

        // create fresh AppVisitors table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) AppVisitor + get all AppVisitors + delete all AppVisitors
     */

    // AppVisitors table name
    private static final String TABLE_APPVISITORS = "AppVisitors";

    // AppVisitors Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_STUDENT = "studentType";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_RATING = "rating";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_EMAIL};

    public void addAppVisitor(AppVisitor AppVisitor){

        Log.d("addAppVisitor", AppVisitor.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, AppVisitor.getName()); // get name 
        values.put(KEY_EMAIL, AppVisitor.getEmail()); // get email
        values.put(KEY_LANGUAGE, AppVisitor.getLanguage()); // get language
        values.put(KEY_STUDENT, AppVisitor.getStudentType()); // get student type
        values.put(KEY_DATE, AppVisitor.getDate()); // get date
        values.put(KEY_TIME, AppVisitor.getTime()); // get time
        values.put(KEY_RATING, AppVisitor.getRating()); // get rating

        // 3. insert
        db.insert(TABLE_APPVISITORS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public AppVisitor getAppVisitor(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_APPVISITORS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections 
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build AppVisitor object
        AppVisitor AppVisitor = new AppVisitor();
        AppVisitor.setId(Integer.parseInt(cursor.getString(0)));
        AppVisitor.setName(cursor.getString(1));
        AppVisitor.setEmail(cursor.getString(2));
        AppVisitor.setLanguage(cursor.getString(3));
        AppVisitor.setStudentType(cursor.getString(4));
        AppVisitor.setDate(cursor.getString(5));
        AppVisitor.setTime(cursor.getString(6));
        AppVisitor.setRating(cursor.getString(7));

        Log.d("getAppVisitor("+id+")", AppVisitor.toString());

        // 5. return AppVisitor
        return AppVisitor;
    }

    // Get All AppVisitors
    public List<AppVisitor> getAllAppVisitors() {
        List<AppVisitor> AppVisitors = new LinkedList<AppVisitor>();

        // 1. build the query
        String query = "SELECT * FROM " + TABLE_APPVISITORS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build AppVisitor and add it to list
        AppVisitor AppVisitor = null;
        if (cursor.moveToFirst()) {
            do {
                AppVisitor = new AppVisitor();
                AppVisitor.setId(Integer.parseInt(cursor.getString(0)));
                AppVisitor.setName(cursor.getString(1));
                AppVisitor.setEmail(cursor.getString(2));
                AppVisitor.setLanguage(cursor.getString(3));
                AppVisitor.setStudentType(cursor.getString(4));
                AppVisitor.setDate(cursor.getString(5));
                AppVisitor.setTime(cursor.getString(6));
                AppVisitor.setRating(cursor.getString(7));

                // Add AppVisitor to AppVisitors
                AppVisitors.add(AppVisitor);
            } while (cursor.moveToNext());
        }

        Log.d("getAllAppVisitors()", AppVisitors.toString());

        // return AppVisitors
        return AppVisitors;
    }

    // Updating single AppVisitor
    public int updateAppVisitor(AppVisitor AppVisitor) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", AppVisitor.getName()); // get name 
        values.put("email", AppVisitor.getEmail()); // get email

        // 3. updating row
        int i = db.update(TABLE_APPVISITORS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(AppVisitor.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single AppVisitor
    public void deleteAppVisitor(AppVisitor AppVisitor) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_APPVISITORS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(AppVisitor.getId()) });

        // 3. close
        db.close();

        Log.d("deleteAppVisitor", AppVisitor.toString());

    }

    // Deleting all AppVisitor
    public void deleteAllAppVisitors() {

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_APPVISITORS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build AppVisitor and delete it
        AppVisitor AppVisitor = null;
        if (cursor.moveToFirst()) {
            do {
                AppVisitor = new AppVisitor();
                AppVisitor.setId(Integer.parseInt(cursor.getString(0)));
                deleteAppVisitor(AppVisitor);
            } while (cursor.moveToNext());
        }

        // 4. close
        db.close();

        Log.d("deleteAllAppVisitors", AppVisitor.toString());

    }

}