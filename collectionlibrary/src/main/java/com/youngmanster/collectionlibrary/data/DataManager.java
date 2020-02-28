package com.youngmanster.collectionlibrary.data;

import android.test.suitebuilder.annotation.Suppress;

import com.youngmanster.collectionlibrary.data.database.PagingList;
import com.youngmanster.collectionlibrary.data.database.ResultSet;
import com.youngmanster.collectionlibrary.data.database.SQLiteDataBase;
import com.youngmanster.collectionlibrary.network.RequestBuilder;
import com.youngmanster.collectionlibrary.network.RequestManager;
import com.youngmanster.collectionlibrary.utils.LogUtils;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * Created by yangy
 * 2020-02-27
 * Describe:
 */
public class DataManager {

    public static class DataForSqlite{

        /**
         * 插入数据
         * -1 代表失败
         */
        public static <T> boolean insert(T model) {
            return SQLiteDataBase.getInstance().insert(model);
        }


        /**
         * 批量插入数据
         */
        public static  <T>  boolean insertList(Class<T> clazz, List<T> dataList) {
            return SQLiteDataBase.getInstance().batchInsert(clazz,dataList);
        }

        public static  <T> void insertListBySync(Class<T> clazz, List<T> dataList, SQLiteDataBase.InsertDataCompleteListener onInsertDataCompleteListener) {
            SQLiteDataBase.getInstance().batchInsertBySync(clazz,dataList,onInsertDataCompleteListener);
        }


        /**
         * 根据条件查询
         * selection:条件语句 ：id=?   id=? or age=?
         */
        public static  <T> T queryByFirstByWhere(
                Class<T> clazz,
                String selection,
                String...selectionArgs){
            return SQLiteDataBase.getInstance().queryByFirstByWhere(clazz,selection,
                    selectionArgs);
        }

        /**
         * 查询表里的全部数据
         */
        public  static <T> List<T> queryAll(Class<T> clazz) {
            return SQLiteDataBase.getInstance().queryAll(clazz);
        }

        public static  <T> void queryAllBySync(Class<T> clazz,SQLiteDataBase.QueryDataCompleteListener<T> onQueryDataComplete){
            SQLiteDataBase.getInstance().queryAllBySync(clazz,onQueryDataComplete);
        }

        /**
         * 查询表里的第一条数据
         */
        public static  <T> T queryByFirst( Class<T> clazz) {
            return SQLiteDataBase.getInstance().queryByFirst(clazz);
        }


        /**
         * 根据条件删除
         * selection:条件语句 ：id=?   id=? or age=?
         */

        public  <T> boolean delete(Class<T> clazz , String whereClause , String...whereArgs) {
            return SQLiteDataBase.getInstance().delete(clazz,whereClause, whereArgs);
        }
        /**
         * 删除全部数据
         */
        public static  <T> boolean deleteAll(Class<T> clazz) {
            return SQLiteDataBase.getInstance().deleteAll(clazz);
        }

        /**
         * 删除表
         */
        public  <T> void deleteTable(Class<T> clazz) {
            SQLiteDataBase.getInstance().deleteTable(clazz);
        }



        /**
         * 更新数据
         * -1失败
         */
        public static  <T> boolean update(T model,String whereClause, String...whereArgs) {
            return SQLiteDataBase.getInstance().update(model,whereClause, whereArgs);
        }


        /***
         * 分页查询，实体类必须包含PrimaryKey
         */


        public static <T> PagingList<T> queryOfPageByWhere(
                Class<T> clazz,
                int page,
                int pageSize,
                String selection,
                String...selectionArgs
        ) {

            return SQLiteDataBase.getInstance().queryOfPageByWhere(clazz,selection,selectionArgs,page,pageSize);

        }

        public static <T> PagingList<T> queryOfPage(
                Class<T> clazz,
                int page,
                int pageSize
        ){
            return SQLiteDataBase.getInstance().queryOfPage(clazz,page,pageSize);
        }

        /**
         * 使用SQL语句
         */

        public static List<ResultSet> execQuerySQL(String sql) {
            return SQLiteDataBase.getInstance().execQuerySQL(sql);
        }


        /**
         * 更新表,用于更新表格字段，只可增加字段，需要配合版本号已经SQLiteVersionMigrate使用
         */
        public static <T> void updateTable( Class<T> clazz) {
            SQLiteDataBase.getInstance().updateTable(clazz);
        }

    }

    public static class DataForHttp{
        public static <T> DisposableObserver<ResponseBody> httpRequest(RequestBuilder<T> requestBuilder) {
            return RequestManager.getInstance().request(requestBuilder);
        }
    }

    public static class DataForSharePreferences{
        public static  <T> void saveObject(String key,T con){
            if(con.getClass()==int.class||con.getClass()==Integer.class){
                SharePreference.getInstance().putInt(key, (Integer) con);
            }else if(con.getClass()==String.class){
                SharePreference.getInstance().putString(key, (String) con);
            }else if(con.getClass()==Boolean.class||con.getClass()==boolean.class){
                SharePreference.getInstance().putBoolean(key, (Boolean) con);
            }else if(con.getClass()==Long.class||con.getClass()==long.class){
                SharePreference.getInstance().putLong(key, (Long) con);
            }else if(con.getClass()==Float.class||con.getClass()==float.class){
                SharePreference.getInstance().putFloat(key, (Float) con);
            }else{
                LogUtils.error("DataManager","暂不支持该类型数据");
            }

        }

        @SuppressWarnings("unchecked")
        public static  <T> T getObject(String key,T defaultValue) {

            if(defaultValue.getClass()==int.class||defaultValue.getClass()==Integer.class){
               return  (T)Integer.valueOf(SharePreference.getInstance().getInt(key,(Integer) defaultValue));
            }else if(defaultValue.getClass()==String.class){
                return (T) SharePreference.getInstance().getString(key,(String)defaultValue);
            }else if(defaultValue.getClass()==Boolean.class||defaultValue.getClass()==boolean.class){
                return (T)Boolean.valueOf(SharePreference.getInstance().getBoolean(key, (Boolean) defaultValue));
            }else if(defaultValue.getClass()==Long.class||defaultValue.getClass()==long.class){
                return (T) SharePreference.getInstance().getLong(key, (Long) defaultValue);
            }else if(defaultValue.getClass()==Float.class||defaultValue.getClass()==float.class){
                return (T)Float.valueOf(SharePreference.getInstance().getFloat(key, (Float) defaultValue));
            }else{
                LogUtils.error("DataManager","暂不支持该类型数据");
            }

            return null;
        }

    }


}
