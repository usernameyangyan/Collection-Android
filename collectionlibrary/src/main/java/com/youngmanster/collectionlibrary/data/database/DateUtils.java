package com.youngmanster.collectionlibrary.data.database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yangy
 * 2020-02-26
 * Describe:
 */
public class DateUtils {
    private final static DateFormat DF_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private final static DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());

    private DateUtils(){}

    static Date parseStr2Date(String str){
        try {
            if(str.endsWith(".SSS")){
                return DF_SSS.parse(str);
            }else{
                return DF.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String formatDate2Str(Date date){
        return DF_SSS.format(date);
    }

}
