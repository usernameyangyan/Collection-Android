package com.youngmanster.collectionlibrary.network.convert;

import android.text.TextUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class SetterExclusionStrategy implements ExclusionStrategy {

    private String field;

    public SetterExclusionStrategy(String field) {
        this.field = field;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        if (!TextUtils.isEmpty(field)) {
            if (f.getName().equals(field)) {
                /** true 代表此字段要过滤 */
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
