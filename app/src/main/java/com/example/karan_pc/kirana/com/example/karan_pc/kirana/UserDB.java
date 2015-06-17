package com.example.karan_pc.kirana.com.example.karan_pc.kirana;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB {

	Context userContext;

	private static final String DATABASE_NAME = "theyodb";
	private static final String TABLE_NAME = "user";
	private static final int DB_VERSION = 1;

	private String ID = "id";
	public static String NAME = "name", AGE = "age", COST = "cost";
	private DBHelper dbHelper;
	private SQLiteDatabase databaseObj;

	private class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			/*db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT," + INCOME
					+ " TEXT NOT NULL," + FOOD + " TEXT NOT NULL," + CLOTHES
					+ " TEXT NOT NULL," + LIABILITIES + " TEXT NOT NULL,"
					+ MEDICINE + " TEXT NOT NULL," + BILLS + " TEXT NOT NULL,"
					+ ENTERTAINMENT + " TEXT NOT NULL," + FUEL
					+ " TEXT NOT NULL," + OTHERS + " TEXT NOT NULL," + EXPENSE
					+ " TEXT NOT NULL," + MONTH + " TEXT NOT NULL," + YEAR
					+ " TEXT NOT NULL)");*/
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

	public UserDB(Context userContext) {
		this.userContext = userContext;
	}

	public UserDB openDatabase() throws SQLException {
		dbHelper = new DBHelper(userContext);
		databaseObj = dbHelper.getWritableDatabase();
		return this;
	}

	public void closeDatabase() throws SQLException {
		dbHelper.close();
	}

	public long createEntry(List<CSVTestObject> csvTestObjects) throws SQLException {
		ContentValues userData = new ContentValues();
		CSVTestObject csvTestObject = csvTestObjects.get(0);
		userData.put(ID, csvTestObject.getId());
		userData.put(NAME, csvTestObject.getName());
		userData.put(AGE, csvTestObject.getAge());
		userData.put(COST, csvTestObject.getCost());

		return databaseObj.insert(TABLE_NAME, null, userData);
	}

	public void updateEntry(List<CSVTestObject> csvTestObjects) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues updatedData = new ContentValues();
		CSVTestObject csvTestObject = csvTestObjects.get(0);
		updatedData.put(ID, csvTestObject.getId());
		updatedData.put(NAME, csvTestObject.getName());
		updatedData.put(AGE, csvTestObject.getAge());
		updatedData.put(COST, csvTestObject.getCost());
		//databaseObj.update(TABLE_NAME, updatedData, MONTH + " = " + monthIndex + " AND " + YEAR + " = " + yearIndex, null);
		databaseObj.update(TABLE_NAME, updatedData, null, null);
	}
}
