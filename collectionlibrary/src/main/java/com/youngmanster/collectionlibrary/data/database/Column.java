package com.youngmanster.collectionlibrary.data.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangy
 * 2020-02-24
 * Describe:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    boolean isPrimaryKey()default false;
    boolean isNull()default true;
    boolean isUnique()default false;
}