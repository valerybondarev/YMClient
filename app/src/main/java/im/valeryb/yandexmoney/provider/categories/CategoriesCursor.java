package im.valeryb.yandexmoney.provider.categories;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import im.valeryb.yandexmoney.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code categories} table.
 */
public class CategoriesCursor extends AbstractCursor implements CategoriesModel {
    public CategoriesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(CategoriesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTitle() {
        String res = getStringOrNull(CategoriesColumns.TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'title' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code serverid} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getServerid() {
        String res = getStringOrNull(CategoriesColumns.SERVERID);
        if (res == null)
            throw new NullPointerException("The value of 'serverid' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code parentid} value.
     */
    public long getParentid() {
        Long res = getLongOrNull(CategoriesColumns.PARENTID);
        if (res == null)
            throw new NullPointerException("The value of 'parentid' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
