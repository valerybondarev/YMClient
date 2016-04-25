package im.valeryb.yandexmoney;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;

import im.valeryb.yandexmoney.provider.categories.CategoriesColumns;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView listView;
    private final static String DATA_DOWNLOAD_URL = "https://money.yandex.ru/api/categories-list";
    private CursorAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean adapterSwapped = false;
    private boolean refreshingNow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.inner_main_list);
        adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, null,
                new String[]{CategoriesColumns.TITLE},
                new int[]{R.id.list_item_title}, 0);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.empty_list_view));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InnerActivity.class);
                intent.putExtra(InnerActivity.EXTRA_ID, id);
                String title = ((TextView) ((LinearLayout) view).getChildAt(0)).getText().toString();
                intent.putExtra(InnerActivity.EXTRA_TITLE, title);
                if (!adapterSwapped) {
                    startActivity(intent);
                }
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (refreshingNow) { //No way to have more than one refresh action at a time
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                downloadData();
            }
        });
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            downloadData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void downloadData() {
        if (refreshingNow) {
            return;
        }
        refreshingNow = true;
        swipeRefreshLayout.setRefreshing(true);
        if (!adapterSwapped) {
            Cursor c = adapter.getCursor();
            ArrayList<HashMap<String, String>> tempAdapterList = new ArrayList<>();
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    HashMap<String, String> m = new HashMap<>();
                    m.put(CategoriesColumns.TITLE, c.getString(c.getColumnIndex(CategoriesColumns.TITLE)));
                    tempAdapterList.add(m);
                } while (c.moveToNext());
            }
            listView.setAdapter(new SimpleAdapter(MainActivity.this,
                    tempAdapterList, R.layout.list_item,
                    new String[]{CategoriesColumns.TITLE},
                    new int[]{R.id.list_item_title}));
            adapterSwapped = true;
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listView.setAdapter(adapter);
                getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                adapterSwapped = false;
                swipeRefreshLayout.setRefreshing(false);
                refreshingNow = false;
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                refreshingNow = false;
            }
        };

        ProcessJsonRequest processJsonRequest = new ProcessJsonRequest(this, Request.Method.GET, DATA_DOWNLOAD_URL, listener, errorListener);
        queue.add(processJsonRequest);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(MainActivity.this, CategoriesColumns.CONTENT_URI, null, CategoriesColumns.PARENTID + "=-1", null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

}


