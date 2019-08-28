package com.youngmanster.collectionlibrary.base.helper.debug;

import java.util.List;

public class DebugFragmentRecord {
    public CharSequence fragmentName;
    public List<DebugFragmentRecord> childFragmentRecord;

    public DebugFragmentRecord(CharSequence fragmentName, List<DebugFragmentRecord> childFragmentRecord) {
        this.fragmentName = fragmentName;
        this.childFragmentRecord = childFragmentRecord;
    }
}
