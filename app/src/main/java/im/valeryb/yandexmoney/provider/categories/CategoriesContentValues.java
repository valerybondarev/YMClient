package im.valeryb.yandexmoney.provider.categories;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import im.valeryb.yandexmoney.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code categories} table.
 */
public class CategoriesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CategoriesColumns.CONTENT_URI;
    }


    public int update(ContentResolver contentResolver, @Nullable CategoriesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public CategoriesContentValues putTitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("title must not be null");
        mContentValues.put(CategoriesColumns.TITLE, value);
        return this;
    }


    public CategoriesContentValues putServerid(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("serverid must not be null");
        mContentValues.put(CategoriesColumns.SERVERID, value);
        return this;
    }


    public CategoriesContentValues putParentid(long value) {
        mContentValues.put(CategoriesColumns.PARENTID, value);
        return this;
    }

}
