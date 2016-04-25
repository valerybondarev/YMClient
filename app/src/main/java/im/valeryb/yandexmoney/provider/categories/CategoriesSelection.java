package im.valeryb.yandexmoney.provider.categories;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import im.valeryb.yandexmoney.provider.base.AbstractSelection;

/**
 * Selection for the {@code categories} table.
 */
public class CategoriesSelection extends AbstractSelection<CategoriesSelection> {
    @Override
    protected Uri baseUri() {
        return CategoriesColumns.CONTENT_URI;
    }


    public CategoriesCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new CategoriesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public CategoriesCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public CategoriesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public CategoriesSelection id(long... value) {
        addEquals("categories." + CategoriesColumns._ID, toObjectArray(value));
        return this;
    }

    public CategoriesSelection title(String... value) {
        addEquals(CategoriesColumns.TITLE, value);
        return this;
    }

    public CategoriesSelection titleNot(String... value) {
        addNotEquals(CategoriesColumns.TITLE, value);
        return this;
    }

    public CategoriesSelection titleLike(String... value) {
        addLike(CategoriesColumns.TITLE, value);
        return this;
    }

    public CategoriesSelection titleContains(String... value) {
        addContains(CategoriesColumns.TITLE, value);
        return this;
    }

    public CategoriesSelection titleStartsWith(String... value) {
        addStartsWith(CategoriesColumns.TITLE, value);
        return this;
    }

    public CategoriesSelection titleEndsWith(String... value) {
        addEndsWith(CategoriesColumns.TITLE, value);
        return this;
    }

    public CategoriesSelection serverid(String... value) {
        addEquals(CategoriesColumns.SERVERID, value);
        return this;
    }

    public CategoriesSelection serveridNot(String... value) {
        addNotEquals(CategoriesColumns.SERVERID, value);
        return this;
    }

    public CategoriesSelection serveridLike(String... value) {
        addLike(CategoriesColumns.SERVERID, value);
        return this;
    }

    public CategoriesSelection serveridContains(String... value) {
        addContains(CategoriesColumns.SERVERID, value);
        return this;
    }

    public CategoriesSelection serveridStartsWith(String... value) {
        addStartsWith(CategoriesColumns.SERVERID, value);
        return this;
    }

    public CategoriesSelection serveridEndsWith(String... value) {
        addEndsWith(CategoriesColumns.SERVERID, value);
        return this;
    }

    public CategoriesSelection parentid(long... value) {
        addEquals(CategoriesColumns.PARENTID, toObjectArray(value));
        return this;
    }

    public CategoriesSelection parentidNot(long... value) {
        addNotEquals(CategoriesColumns.PARENTID, toObjectArray(value));
        return this;
    }

    public CategoriesSelection parentidGt(long value) {
        addGreaterThan(CategoriesColumns.PARENTID, value);
        return this;
    }

    public CategoriesSelection parentidGtEq(long value) {
        addGreaterThanOrEquals(CategoriesColumns.PARENTID, value);
        return this;
    }

    public CategoriesSelection parentidLt(long value) {
        addLessThan(CategoriesColumns.PARENTID, value);
        return this;
    }

    public CategoriesSelection parentidLtEq(long value) {
        addLessThanOrEquals(CategoriesColumns.PARENTID, value);
        return this;
    }
}
