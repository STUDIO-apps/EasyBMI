package uk.co.appsbystudio.easybmi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cache.db";
    public static final Integer DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS BMI_HISTORY(_id INTEGER PRIMARY KEY AUTOINCREMENT, bmi REAL, weight REAL, height REAL, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS BMI_HISTORY");
        onCreate(db);
    }

    public long saveData(SavedItemsModel savedItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_id", savedItem.getId());
        values.put("bmi", savedItem.getBmi());
        values.put("weight", savedItem.getWeight());
        values.put("height", savedItem.getHeight());
        values.put("Timestamp", savedItem.getDateTime());

        long savedItems = db.insert("BMI_HISTORY", null, values);

        return savedItems;
    }

    public List<SavedItemsModel> getBMI() {
        List<SavedItemsModel> savedItemsModelList = new ArrayList<SavedItemsModel>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM BMI_HISTORY ORDER BY Timestamp DESC", null);

        if (cursor.moveToFirst()) {
            do {
                SavedItemsModel savedItemsModel = new SavedItemsModel();
                savedItemsModel.setBmi(cursor.getFloat(cursor.getColumnIndex("bmi")));
                savedItemsModelList.add(savedItemsModel);
            } while (cursor.moveToNext());
        }

        return savedItemsModelList;
    }

    public List<SavedItemsModel> getDATETIME() {
        List<SavedItemsModel> savedItemsModelList = new ArrayList<SavedItemsModel>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM BMI_HISTORY ORDER BY Timestamp DESC", null);

        if (cursor.moveToFirst()) {
            do {
                SavedItemsModel savedItemsModel = new SavedItemsModel();
                savedItemsModel.setBmi(cursor.getFloat(cursor.getColumnIndex("Timestamp")));
                savedItemsModelList.add(savedItemsModel);
            } while (cursor.moveToNext());
        }

        return savedItemsModelList;
    }


}
