package com.miximixi.noleftovers.ui.food;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FoodDatabaseHelper extends SQLiteOpenHelper {
    static final String FOOD_DB_NAME = "food-db";

    public FoodDatabaseHelper(@Nullable Context context) {
        super(context, FOOD_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
