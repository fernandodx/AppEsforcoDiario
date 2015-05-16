package com.indra.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	private String[] scriptSQLCreate;
	private String scriptSQLDelete;

	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version, String[] scriptCreate, String scriptDelete) {
		super(context, BaseDAO.NOME_BANCO, factory, version);
		
		this.scriptSQLCreate = scriptCreate;
		this.scriptSQLDelete = scriptDelete;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for(int i=0; i < scriptSQLCreate.length; i++){
			db.execSQL(this.scriptSQLCreate[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(this.scriptSQLDelete);
		onCreate(db);
	}

}
