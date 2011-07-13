/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.contacts.calllog;

import com.android.common.io.MoreCloseables;

import android.database.AbstractCursor;
import android.database.Cursor;

/**
 * Wraps a cursor to add an additional column with the same value for all rows.
 * <p>
 * The number of rows in the cursor and the set of columns is determined by the cursor being
 * wrapped.
 */
public class ExtendedCursor extends AbstractCursor {
    /** The cursor to wrap. */
    private final Cursor mCursor;
    /** The name of the additional column. */
    private final String mColumnName;
    /** The value to be assigned to the additional column. */
    private final Object mValue;

    /**
     * Creates a new cursor which extends the given cursor by adding a column with a constant value.
     *
     * @param cursor the cursor to extend
     * @param columnName the name of the additional column
     * @param value the value to be assigned to the additional column
     */
    public ExtendedCursor(Cursor cursor, String columnName, Object value) {
        mCursor = cursor;
        mColumnName = columnName;
        mValue = value;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public String[] getColumnNames() {
        String[] columnNames = mCursor.getColumnNames();
        int length = columnNames.length;
        String[] extendedColumnNames = new String[length + 1];
        System.arraycopy(columnNames, 0, extendedColumnNames, 0, length);
        extendedColumnNames[length] = mColumnName;
        return extendedColumnNames;
    }

    @Override
    public String getString(int column) {
        if (column == mCursor.getColumnCount()) {
            return (String) mValue;
        }
        return mCursor.getString(column);
    }

    @Override
    public short getShort(int column) {
        if (column == mCursor.getColumnCount()) {
            return (Short) mValue;
        }
        return mCursor.getShort(column);
    }

    @Override
    public int getInt(int column) {
        if (column == mCursor.getColumnCount()) {
            return (Integer) mValue;
        }
        return mCursor.getInt(column);
    }

    @Override
    public long getLong(int column) {
        if (column == mCursor.getColumnCount()) {
            return (Long) mValue;
        }
        return mCursor.getLong(column);
    }

    @Override
    public float getFloat(int column) {
        if (column == mCursor.getColumnCount()) {
            return (Float) mValue;
        }
        return mCursor.getFloat(column);
    }

    @Override
    public double getDouble(int column) {
        if (column == mCursor.getColumnCount()) {
            return (Double) mValue;
        }
        return mCursor.getDouble(column);
    }

    @Override
    public boolean isNull(int column) {
        if (column == mCursor.getColumnCount()) {
            return mValue == null;
        }
        return mCursor.isNull(column);
    }

    @Override
    public boolean onMove(int oldPosition, int newPosition) {
        return mCursor.moveToPosition(newPosition);
    }

    @Override
    public void close() {
        MoreCloseables.closeQuietly(mCursor);
        super.close();
    }
}