package im.valeryb.yandexmoney.provider.categories;

import android.net.Uri;
import android.provider.BaseColumns;

import im.valeryb.yandexmoney.provider.YMContentProvider;
import im.valeryb.yandexmoney.provider.categories.CategoriesColumns;

/**
 * Columns for the {@code categories} table.
 */
public class CategoriesColumns implements BaseColumns {
    public static final String TABLE_NAME = "categories";
    public static final Uri CONTENT_URI = Uri.parse(YMContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String TITLE = "title";

    public static final String SERVERID = "serverId";

    public static final String PARENTID = "parentId";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            SERVERID,
            PARENTID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(SERVERID) || c.contains("." + SERVERID)) return true;
            if (c.equals(PARENTID) || c.contains("." + PARENTID)) return true;
        }
        return false;
    }

}
