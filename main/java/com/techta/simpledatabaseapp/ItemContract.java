package com.techta.simpledatabaseapp;

import android.provider.BaseColumns;

public class ItemContract {

    private ItemContract () {}

    public static final class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "itemList", COLUMN_NAME = "name", COLUMN_AMOUNT = "amount", COLUMN_TIMESTAMP = "timestamp";

    }
}
