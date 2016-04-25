package im.valeryb.yandexmoney.provider.categories;

import im.valeryb.yandexmoney.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code categories} table.
 */
public interface CategoriesModel extends BaseModel {

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTitle();

    /**
     * Get the {@code serverid} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getServerid();

    /**
     * Get the {@code parentid} value.
     */
    long getParentid();
}
