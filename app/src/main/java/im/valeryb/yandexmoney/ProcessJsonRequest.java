package im.valeryb.yandexmoney;

import android.content.ContentUris;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import im.valeryb.yandexmoney.provider.categories.CategoriesContentValues;
import im.valeryb.yandexmoney.provider.categories.CategoriesSelection;

/**
 * Heavy JSON should be parsed not in UI thread.
 * One solution is to create IntentService which will parse and save JSON and send message to UI thread.
 * This solution requires lots of code with callbacks and other stuff.
 * As we know, Volley Request is processed in thread from its thread pool, not in UI thread, so
 * the second solution is to override standard Request's parseNetworkResponse(...) and do all job there.
 * No more callbacks, few code, clear MainActivity code and behavior.
 */

public class ProcessJsonRequest extends StringRequest {

    Context context;

    public ProcessJsonRequest(Context context, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.context = context;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONArray jsonArray = new JSONArray(jsonString);
            new CategoriesSelection().delete(context.getContentResolver());
            for (int i = 0; i < jsonArray.length(); i++) {
                saveJsonDfs(jsonArray.getJSONObject(i), -1);
            }
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
        return super.parseNetworkResponse(response);
    }

    private void saveJsonDfs(JSONObject current, long parentId) {
        int serverId = -1;
        try {
            serverId = current.getInt("id");
        } catch (JSONException ignored) {}
        try {
            long insertedId = ContentUris.parseId((
                    new CategoriesContentValues()
                            .putParentid(parentId)
                            .putServerid(serverId + "")
                            .putTitle(current.getString("title"))
            ).insert(context.getContentResolver()));
            try {
                JSONArray subs = current.getJSONArray("subs");
                for (int i = 0; i < subs.length(); i++) {
                    saveJsonDfs(subs.getJSONObject(i), insertedId);
                }
            } catch (JSONException ignored) {}
        } catch (JSONException ignored) {}
    }
}
