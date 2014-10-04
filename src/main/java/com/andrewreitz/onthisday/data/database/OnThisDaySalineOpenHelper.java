package com.andrewreitz.onthisday.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.andrewreitz.onthisday.data.api.reddit.model.Data;
import shillelagh.Shillelagh;

public class OnThisDaySalineOpenHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "shillelagh_example.db";
  private static final int DATABASE_VERSION = 1;

  public OnThisDaySalineOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    Shillelagh.createTable(db, Data.class);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    throw new UnsupportedOperationException("Not Implmented");
  }
}
