package uk.co.appsbystudio.easybmi.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Zac on 18/12/2014.
 */
public class HistoryRepo {
    private DBHelper dbHelper;

    public HistoryRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(SQL_History_table sql_history_table) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQL_History_table.KEY_weight, sql_history_table.weight_1);
        values.put(SQL_History_table.KEY_height, sql_history_table.height_1);
        values.put(SQL_History_table.KEY_bmi, sql_history_table.bmi_1);

        long user_ID = db.insert(SQL_History_table.TABLE, null, values);
        db.close();
        return (int) user_ID;
    }

    public void delete(int user_ID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(SQL_History_table.TABLE, SQL_History_table.KEY_ID + "= ?", new String[] {String.valueOf(user_ID)});
        db.close();
    }

    public void update(SQL_History_table sql_history_table){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SQL_History_table.KEY_weight, sql_history_table.weight_1);
        values.put(SQL_History_table.KEY_height, sql_history_table.height_1);
        values.put(SQL_History_table.KEY_bmi, sql_history_table.bmi_1);

        db.update(SQL_History_table.TABLE, values, SQL_History_table.KEY_ID + "= ?", new String[]{String.valueOf(sql_history_table.user_ID)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getUserList() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SQL_History_table.KEY_ID + "," +
                SQL_History_table.KEY_weight + "," +
                SQL_History_table.KEY_height + "," +
                SQL_History_table.KEY_bmi +
                " FROM " + SQL_History_table.TABLE;

        ArrayList<HashMap<String, String>> sqlList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<String, String>();
                user.put("id", cursor.getString(cursor.getColumnIndex(SQL_History_table.KEY_ID)));
                user.put("name", cursor.getString(cursor.getColumnIndex(SQL_History_table.KEY_bmi)));
                sqlList.add(user);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sqlList;

    }

    public SQL_History_table getUserById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SQL_History_table.KEY_ID + "," +
                SQL_History_table.KEY_weight + "," +
                SQL_History_table.KEY_height + "," +
                SQL_History_table.KEY_bmi +
                " FROM " + SQL_History_table.TABLE
                + " WHERE " +
                SQL_History_table.KEY_ID + "=?";

        int iCount =0;
        SQL_History_table user = new SQL_History_table();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                user.user_ID =cursor.getInt(cursor.getColumnIndex(SQL_History_table.KEY_ID));
                user.weight_1 =cursor.getInt(cursor.getColumnIndex(SQL_History_table.KEY_weight));
                user.height_1  =cursor.getInt(cursor.getColumnIndex(SQL_History_table.KEY_height));
                user.bmi_1 =cursor.getInt(cursor.getColumnIndex(SQL_History_table.KEY_bmi));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return user;
    }

}
