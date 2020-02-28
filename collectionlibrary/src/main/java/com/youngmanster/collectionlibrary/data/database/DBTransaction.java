package com.youngmanster.collectionlibrary.data.database;

import android.database.sqlite.SQLiteDatabase;

/**
 *
 */
public class DBTransaction {

	private DBTransaction() {
	}
	
	/**
	 * executes sqls in a transction
	 */
	public static void transact(DbSqlite db, DBTransactionInterface transctionInterface){
		if(transctionInterface!=null){
			SQLiteDatabase sqliteDb = db.getSQLiteDatabase();
			sqliteDb.beginTransaction();
			try{
				transctionInterface.onTransact();
				sqliteDb.setTransactionSuccessful();
			}finally{
				sqliteDb.endTransaction();
			}
		}
	}
	
	public interface DBTransactionInterface{
   	 void onTransact();
   }
}
