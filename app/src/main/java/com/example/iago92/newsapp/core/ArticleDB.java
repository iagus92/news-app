package com.example.iago92.newsapp.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;

import com.example.iago92.newsapp.models.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iago92 on 6/12/17.
 */

public class ArticleDB extends SQLiteOpenHelper {

    public static class ArticleEntry implements BaseColumns {
        public static final String TABLE_NAME = "article";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_DATE = "date";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ArticleEntry.TABLE_NAME + " (" +
                    ArticleEntry._ID + " INTEGER PRIMARY KEY," +
                    ArticleEntry.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                    ArticleEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    ArticleEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    ArticleEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    ArticleEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
                    ArticleEntry.COLUMN_NAME_DATE + TEXT_TYPE + ")";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ArticleEntry.TABLE_NAME;


    public ArticleDB(Context context) {
        super(context, "news.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insert(String author, String title, String description, String url, String image, String date){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ArticleEntry.COLUMN_NAME_AUTHOR, author);
        values.put(ArticleEntry.COLUMN_NAME_TITLE, title);
        values.put(ArticleEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(ArticleEntry.COLUMN_NAME_URL, url);
        values.put(ArticleEntry.COLUMN_NAME_IMAGE, image);
        values.put(ArticleEntry.COLUMN_NAME_DATE, date);
        // Insert the new row, returning the primary key value of the new row
        return db.insert(ArticleEntry.TABLE_NAME, null, values);
    }

    public List<Article> getArticles(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ArticleEntry.TABLE_NAME, null);
        List<Article> lista = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                lista.add(new Article(cursor.getString(1),
                                      cursor.getString(2),
                                      cursor.getString(3),
                                      cursor.getString(4),
                                      cursor.getString(5),
                                      cursor.getString(6)));
            }while (cursor.moveToNext());

        }
        return lista;
    }

    public void dropNew(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        String selection = ArticleEntry.COLUMN_NAME_TITLE + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { title };
        // Issue SQL statement.
        db.delete(ArticleEntry.TABLE_NAME, selection, selectionArgs);
    }
}
