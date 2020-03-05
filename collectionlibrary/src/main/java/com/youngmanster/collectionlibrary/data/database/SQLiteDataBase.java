package com.youngmanster.collectionlibrary.data.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.data.DataManager;
import com.youngmanster.collectionlibrary.network.rx.utils.RxAsyncTask;
import com.youngmanster.collectionlibrary.network.rx.utils.RxJavaUtils;
import com.youngmanster.collectionlibrary.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by yangy
 * 2020-02-27
 * Describe:
 */
public class SQLiteDataBase {
    public interface QueryDataCompleteListener<T> {
        void onQueryComplete(List<T> datas);
    }

    public interface InsertDataCompleteListener {
        void onInsertDataComplete(boolean isInsert);
    }

    private SQLiteDataBase() {
    }


    private static DbSqlite db = null;
    private static SQLiteDataBase sqliteDataBase = null;

    public static SQLiteDataBase getInstance() {
        if (sqliteDataBase == null) {
            synchronized (SQLiteDataBase.class) {
                if (sqliteDataBase == null) {
                    sqliteDataBase = new SQLiteDataBase();
                    db = new DbSqlite(
                            Config.CONTEXT,
                            Config.CONTEXT.openOrCreateDatabase(
                                    Config.SQLITE_DB_NAME,
                                    Context.MODE_PRIVATE,
                                    null
                            )
                    );
                }
            }
        }
        return sqliteDataBase;
    }


    /**
     * 插入数据
     * -1 代表失败
     */
    public <T> boolean insert(T model) {
        db.execSQL(SqlHelper.createTable((model).getClass()));
        ContentValues contentValues = new ContentValues();
        SqlHelper.parseModelToContentValues(model, contentValues);
        return db.insertOrReplace(
                SqlHelper.getBeanName((model).getClass().getName()),
                contentValues
        ) > -1;
    }


    /**
     * 批量插入数据
     */
    public <T> boolean batchInsert(Class<T> clazz, List<T> dataList) {
        db.execSQL(SqlHelper.createTable(clazz));
        List<ContentValues> listVal = new ArrayList<>();
        for (T model : dataList) {
            ContentValues contentValues = new ContentValues();
            SqlHelper.parseModelToContentValues(model, contentValues);
            listVal.add(contentValues);
        }

        return db.batchInsert(SqlHelper.getBeanName(clazz.getName()), listVal);
    }

    @SuppressLint("CheckResult")
    public <T> void batchInsertBySync(final Class<T> clazz, final List<T> dataList, final InsertDataCompleteListener onInsertDataCompleteListener) {


        RxJavaUtils.executeAsyncTask(new RxAsyncTask<String, Boolean>("") {
            @Override
            public void doInUIThread(Boolean aBoolean) {
                onInsertDataCompleteListener.onInsertDataComplete(aBoolean);
            }

            @Override
            public Boolean doInIOThread(String s) {
                db.execSQL(SqlHelper.createTable(clazz));
                List<ContentValues> listVal = new ArrayList<>();
                for (T model : dataList) {
                    ContentValues contentValues = new ContentValues();
                    SqlHelper.parseModelToContentValues(model, contentValues);
                    listVal.add(contentValues);
                }

                boolean isInsert= db.batchInsert(SqlHelper.getBeanName(clazz.getName()), listVal);
                return isInsert;
            }
        });

    }


    /**
     * 查找满足条件的第一条数据
     */

    private <T> List<T> query(
            Class<T> clazz,
            String[] columns, String selection, String[] selectionArgs,
            String groupBy, String having, String orderBy) {

        try {
            List<ResultSet> queryList =
                    db.query(
                            SqlHelper.getBeanName(clazz.getName()),
                            columns,
                            selection,
                            selectionArgs,
                            groupBy,
                            having,
                            orderBy
                    );
            if (queryList == null || queryList.isEmpty()) {
                return null;
            }
            List<T> resultList = new ArrayList<>();
            SqlHelper.parseResultSetListToModelList(queryList, resultList, clazz);
            return resultList;
        }catch (SQLiteException exception){

        }

        return null;

    }

    /**
     * 根据条件查询
     * selection:条件语句 ：id=?   id=? or age=?
     */
    public <T> T queryByFirstByWhere(
            Class<T> clazz,
            String selection,
            String[] selectionArgs
    ) {
        List<T> resultList =
                query(clazz, null, selection, selectionArgs, null, null, null);

        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     * 查询表里的全部数据
     */
    public <T> List<T> queryAll(Class<T> clazz) {
        return query(clazz, null, null, null, null, null, null);
    }

    /**
     * 根据条件查询表里的全部数据
     */
    public <T> List<T> queryAllByWhere(Class<T> clazz,String selection,
                                       String[] selectionArgs){
        return query(clazz, null, selection, selectionArgs, null, null, null);
    }

    @SuppressLint("CheckResult")
    public <T> void queryAllBySync(final Class<T> clazz, final QueryDataCompleteListener<T> onQueryDataComplete) {

        Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(ObservableEmitter<List<T>> emitter) {
                emitter.onNext(queryAll(clazz));
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<T>>() {
                    @Override
                    public void accept(List<T> ts) {
                        onQueryDataComplete.onQueryComplete(ts);
                    }
                });
    }

    /**
     * 查询表里的第一条数据
     */
    public <T> T queryByFirst(Class<T> clazz) {
        List<T> resultList = queryAll(clazz);

        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;
    }


    /**
     * 根据条件删除
     * selection:条件语句 ：id=?   id=? or age=?
     */

    public <T> boolean delete(Class<T> clazz, String whereClause, String... whereArgs) {
        return db.delete(SqlHelper.getBeanName(clazz.getName()), whereClause, whereArgs) > 0;
    }

    /**
     * 删除全部数据
     */
    public <T> boolean deleteAll(Class<T> clazz) {
        return delete(clazz, "1");
    }


    /**
     * 删除表
     */
    public <T> void deleteTable(Class<T> clazz) {
        String dropTableSql = String.format("DROP TABLE %s", SqlHelper.getBeanName(clazz.getName()));
        db.execSQL(dropTableSql);
    }

    /**
     * 更新数据
     * -1失败
     */
    public <T> boolean update(T model, String whereClause, String[] whereArgs) {
        ContentValues contentValues = new ContentValues();
        SqlHelper.parseModelToContentValues(model, contentValues);
        return db.update(
                SqlHelper.getBeanName((model).getClass().getName()),
                contentValues,
                whereClause,
                whereArgs
        ) == 1;
    }

    /**
     * 分页查询
     */
    private <T> PagingList<T> pagingQuery(
            Class<T> clazz,
            String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having,
            String orderBy, int page, int pageSize
    ) {

        String order = orderBy;

        if (orderBy == null) {
            order = SqlHelper.getPrimaryKey(clazz);
        }

        PagingList<ResultSet> queryList = db.pagingQuery(
                SqlHelper.getBeanName(clazz.getName()), columns, selection, selectionArgs,
                groupBy, having, order, page, pageSize);

        PagingList<T> resultList = new PagingList<>();
        resultList.setTotalSize(queryList.getTotalSize());
        SqlHelper.parseResultSetListToModelList(queryList, resultList, clazz);
        return resultList;
    }

    public <T> PagingList<T> queryOfPageByWhere(
            Class<T> clazz,
            String selection,
            String[] selectionArgs,
            int page,
            int pageSize) {
        return pagingQuery(clazz, null, selection, selectionArgs, null, null, null, page, pageSize);
    }

    public <T> PagingList<T> queryOfPage(
            Class<T> clazz,
            int page,
            int pageSize
    ) {
        return pagingQuery(clazz, null, null, null, null, null, null, page, pageSize);
    }

    /**
     * 使用SQL语句
     */

    public List<ResultSet> execQuerySQL(String sql) {
        return db.execQuerySQL(sql);
    }


    /**
     * 更新表
     */
    public <T> void updateTable(final Class<T> clazz) {
        int newTableVersion = SqlHelper.getTableVersion();
        int curTableVersion = getCurTableVersion();
        if (newTableVersion != curTableVersion) {
            DBTransaction.transact(db, new DBTransaction.DBTransactionInterface() {
                @Override
                public void onTransact() {
                    List<ResultSet> rs = db.query("sqlite_master", new String[]{"sql"}, "type=? AND name=?", new String[]{"table", SqlHelper.getBeanName(clazz.getName())});

                    String curTableSql = rs.get(0).getStringValue("sql");
                    Map<String, Boolean> curColumns = getTableColumnsInfo(curTableSql);

                    List<ColumnInfo> newColumnInfos = getTableColumnInfos(clazz);
                    int newColumnSize = newColumnInfos.size();
                    ColumnInfo newColumnInfo;
                    String newColumnName;
                    String sql;

                    for (int index = 0; index < newColumnSize; ++index) {
                        newColumnInfo = newColumnInfos.get(index);
                        newColumnName = newColumnInfo.getName().toLowerCase();
                        if (curColumns.containsKey(newColumnName)) {
                            curColumns.put(newColumnName, false);
                        } else {
                            sql = SqlHelper.getAddColumnSql(SqlHelper.getBeanName(clazz.getName()), newColumnInfo);
                            db.execSQL(sql);
                        }
                    }


                    DataManager.DataForSharePreferences.saveObject(
                            SqlHelper.PREFS_TABLE_VERSION_KEY,
                            Config.SQLITE_DB_VERSION);
                }
            });
        }
    }


    /**
     * get current table version
     *
     * @return
     */
    private int getCurTableVersion() {
        return DataManager.DataForSharePreferences.getObject(
                SqlHelper.PREFS_TABLE_VERSION_KEY, 0
        );
    }

    /**
     * get table columns in createSql
     *
     * @param createSql
     * @return map, key is column name, value default true means need to delete
     */
    private Map<String, Boolean> getTableColumnsInfo(String createSql) {
        String subSql = createSql.substring(createSql.indexOf('(') + 1, createSql.lastIndexOf(')'));
        String[] columnInfos = subSql.split(",");
        Map<String, Boolean> tableInfo = new HashMap<>();

        String columnName;
        String columnInfo;
        for (int i = 0; i < columnInfos.length; ++i) {
            columnInfo = columnInfos[i].trim();
            columnName = columnInfo.substring(0, columnInfo.indexOf(' '));
            tableInfo.put(columnName.toLowerCase(), true);
        }

        return tableInfo;
    }


    /**
     * return info about table's all columns
     *
     * @param clazz
     * @return
     */
    private <T> List<ColumnInfo> getTableColumnInfos(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();
        for (Field field : fields) {

            if(field.getName().contains("$")){
                continue;
            }

            if (!field.isAccessible())
                field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);

            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setName(field.getName());
            columnInfo.setType(SqlHelper.getColumType(field.getType().getName()));
            if (column != null) {
                columnInfo.setPrimaryKey(column.isPrimaryKey());
                columnInfo.setUnique(column.isUnique());
                columnInfo.setNull(column.isNull());
            }

            columnInfos.add(columnInfo);
        }
        return columnInfos;
    }

}
