package com.youngmanster.collectionlibrary.data.database;

import android.content.ContentValues;

import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangy
 * 2020-02-26
 * Describe:
 */
public class SqlHelper {

    public final static String PREFS_TABLE_VERSION_KEY = "collection_library_prefs_versioins";
    public final static String  isFirstUseKey = "collection_library_db_use_key";

    /**
     * return table version
     * @return
     */
    public static int getTableVersion() {
        return Config.SQLITE_DB_VERSION;
    }

    /**
     * get primary key
     *
     * @param clazz
     * @return
     */
    public static  <T> String getPrimaryKey(Class<T> clazz){
        Field[] fields = clazz.getDeclaredFields();

        for (Field field:fields){

            if(field.getName().contains("$")){
                continue;
            }

            if (!field.isAccessible())
                field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column!=null&&column.isPrimaryKey()) {
                return field.getName();
            }
        }
        return null;
    }

    /**
     *
     * @param queryResultList
     * @param mList
     * @param mdlType
     */
    public static <T> void parseResultSetListToModelList(List<ResultSet> queryResultList, List<T> mList,Class<T>  mdlType) {
        try {
            if (queryResultList == null || queryResultList.isEmpty())
                return;
            for (ResultSet queryResult: queryResultList) {
                T model = mdlType.newInstance();
                parseResultSetToModel(queryResult, model);
                mList.add( model);
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    /**
     * use reflection to parse queryResult's value into model
     * @param queryResult
     * @param model
     */
    public static void parseResultSetToModel(ResultSet queryResult, Object model) {
        Class clazz = model.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Class fieldType;
        try {
            for (Field field:fields) {

                if(field.getName().contains("$")){
                    continue;
                }

                if (!field.isAccessible())
                    field.setAccessible(true);
                fieldType = field.getType();
                if (fieldType == short.class ||fieldType == Short.class || fieldType == Integer.class||fieldType ==int.class) {
                    field.set(model, queryResult.getIntValue(field.getName()));
                } else if (fieldType == Long.class || fieldType == long.class) {
                    field.setLong(model,queryResult.getLongValue(field.getName()));
                } else if (fieldType == float.class || fieldType == Float.class) {
                    field.setFloat(model,queryResult.getFloatValue(field.getName()));
                } else if (fieldType == Double.class|| fieldType == double.class ) {
                    field.setDouble(model,queryResult.getDoubleValue(field.getName()));
                } else if (fieldType == Boolean.class|| fieldType == boolean.class) {
                    field.setBoolean(model,queryResult.getBooleanValue(field.getName()));
                } else if (fieldType == String.class) {
                    field.set(model, queryResult.getStringValue(field.getName()));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * 创建表
     */
    public static  <T> String  createTable(Class<T> clazz)  {
        StringBuffer sqlBuidler =new StringBuffer();
        sqlBuidler.append("CREATE TABLE IF NOT EXISTS ");

        sqlBuidler.append(getBeanName(clazz.getName()));
        sqlBuidler.append("(");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            if(field.getName().contains("$")){
                continue;
            }

            if (!field.isAccessible())
                field.setAccessible(true);

            Column column= null;
            try {
                column = field.getAnnotation(Column.class);
            } catch ( Exception e) {
            }

            sqlBuidler.append(field.getName() + " ");
            sqlBuidler.append(getColumType(field.getType().getName()));
            if (column!=null&&column.isNull() == false) {
                sqlBuidler.append(" NOT NULL ");
            }

            if (column!=null&&column.isPrimaryKey() == true) {
                sqlBuidler.append(" PRIMARY KEY ");
            }

            if (column!=null&&column.isUnique() == true) {
                sqlBuidler.append(" UNIQUE ");
            }

            sqlBuidler.append(",");
        }

        sqlBuidler.deleteCharAt(sqlBuidler.lastIndexOf(","));
        sqlBuidler.append(")");

        return sqlBuidler.toString();
    }


    /**
     * 获取bean名
     */

    public static String getBeanName(String className) {
        String[] list=className.split("\\.");
        return list[list.length - 1];
    }

    /**
     * 获取表属性类型
     */
    public static String  getColumType(String clazz) {

        String type = "";
        if (clazz .equals("java.lang.Integer") || clazz .equals("int") || clazz .equals("java.lang.Short")
                || clazz .equals("short")) {
            type = "INTEGER";
        } else if (clazz .equals("double") || clazz .equals("java.lang.Double")) {
            type = "REAL";
        } else if (clazz .equals("float") || clazz .equals("java.lang.Float")) {
            type = "REAL";
        } else if (clazz .equals("boolean") || clazz .equals("java.lang.Boolean")) {
            type = "INTEGER";
        } else if (clazz .equals("long") || clazz.equals("java.lang.Long")) {
            type = "NUMERIC";
        } else if (clazz.equals("java.lang.String")) {
            type = "TEXT";
        }

        return type;
    }


    /**
     * use reflection to parse model's value to contentValues
     * @param model
     */
    public static void parseModelToContentValues(
            Object model, ContentValues contentValues) {
        if (contentValues.size() > 0)
            contentValues.clear();

        Class clazz = model.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Class fieldType;

        for (Field field : fields) {

            if(field.getName().contains("$")){
                continue;
            }

            try {
                if (!field.isAccessible())
                    field.setAccessible(true);
                fieldType = field.getType();

                if (fieldType == Integer.class || fieldType == int.class
                        || fieldType == Short.class|| fieldType == short.class
                    ) {
                    contentValues.put(field.getName(), (int)field.get(model));
                } else if (fieldType == Long.class || fieldType == long.class) {
                    contentValues.put(field.getName(), (Long) field.get(model));
                } else if (fieldType == Float.class || fieldType == float.class) {
                    contentValues.put(field.getName(), (Float)field.get(model));
                } else if (fieldType == Double.class || fieldType == double.class) {
                    contentValues.put(field.getName(),(Double)field.get(model));
                } else if (fieldType == Boolean.class || fieldType == boolean.class) {
                    if (field.getBoolean(model)) {
                        contentValues.put(field.getName(), "1");
                    } else {
                        contentValues.put(field.getName(), "0");
                    }
                } else if (fieldType == String.class) {
                    contentValues.put(field.getName(), (String)field.get(model));
                }
            } catch (IllegalArgumentException e ) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * return sql of add a columm to table
     * @param table
     * @param columnInfo
     * @return
     */
    public static String getAddColumnSql(String table, ColumnInfo columnInfo) {
        StringBuffer sbSql =new StringBuffer();
        sbSql.append(
                String.format(
                        "ALTER TABLE %s ADD %s %s ",
                        table,
                        columnInfo.getName(),
                        columnInfo.getType()
                )
        );
        if (!columnInfo.isNull()) {
            sbSql.append(" NOT NULL ");
        }
        if (columnInfo.isPrimaryKey()) {
            sbSql.append(" PRIMARY KEY ");
        }

        if (columnInfo.isUnique()) {
            sbSql.append(" UNIQUE ");
        }

        if (!columnInfo.getDefaultValue().equals("null")) {
            sbSql.append(" DEFAULT " + columnInfo.getDefaultValue());
        }

        sbSql.append(";");

        return sbSql.toString();
    }

}
