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
    private static final Integer DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS BMI_HISTORY(_id INTEGER PRIMARY KEY AUTOINCREMENT, bmi REAL, weight REAL, height REAL, timestamp TEXT)");
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

    //BMI_HISTORY

    //Add data to BMI_HISTORY
    public void saveData(SavedItemsModel savedItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("bmi", savedItem.getBmi());
        values.put("weight", savedItem.getWeight());
        values.put("height", savedItem.getHeight());
        values.put("Timestamp", savedItem.getDateTime());

        db.insert("BMI_HISTORY", null, values);
    }

    //Get data from BMI_HISTORY
    public List<SavedItemsModel> getAllData() {
        List<SavedItemsModel> savedItemsModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM BMI_HISTORY ORDER BY timestamp DESC", null);

        if (cursor.moveToFirst()) {
            do {
                SavedItemsModel savedItemsModel = new SavedItemsModel();
                savedItemsModel.setBmi(cursor.getFloat(cursor.getColumnIndex("bmi")));
                savedItemsModel.setWeight(cursor.getFloat(cursor.getColumnIndex("weight")));
                savedItemsModel.setHeight(cursor.getFloat(cursor.getColumnIndex("height")));
                savedItemsModel.setDateTime(cursor.getString(cursor.getColumnIndex("timestamp")));
                savedItemsModelList.add(savedItemsModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return savedItemsModelList;
    }

    //Get the latest entry
    public Float getLatestEntry() {
        Float entry;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT bmi FROM BMI_HISTORY ORDER BY timestamp DESC LIMIT 1", null);
        cursor.moveToFirst();
        entry = cursor.getFloat(cursor.getColumnIndex("bmi"));

        cursor.close();
        db.close();

        return entry;
    }
}
