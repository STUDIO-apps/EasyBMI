package uk.co.appsbystudio.easybmi.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zac on 18/12/2014.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "history.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_HISTORY = "CREATE TABLE" + SQL_History_table.TABLE + "("
                + SQL_History_table.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + SQL_History_table.KEY_weight + " INTEGER, "
                + SQL_History_table.KEY_height + " INTEGER, "
                + SQL_History_table.KEY_bmi + " INTEGER ) ";
        db.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQL_History_table.TABLE);

        onCreate(db);
    }
}
