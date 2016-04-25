package im.valeryb.yandexmoney;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import im.valeryb.yandexmoney.provider.categories.CategoriesColumns;
import im.valeryb.yandexmoney.provider.categories.CategoriesCursor;
import im.valeryb.yandexmoney.provider.categories.CategoriesSelection;

public class InnerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "title";

    private long parentId = -1;
    private String title;
    private CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            parentId = extras.getLong(EXTRA_ID);
            title = extras.getString(EXTRA_TITLE);
        }
        setContentView(R.layout.activity_inner);
        ListView listView = (ListView) findViewById(R.id.inner_main_list);
        adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, null,
                new String[]{CategoriesColumns.TITLE},
                new int[]{R.id.list_item_title}, 0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoriesCursor exists = new CategoriesSelection().parentid(id)
                        .query(getContentResolver());
                if (exists.getCount() > 0) {
                    Intent intent = new Intent(InnerActivity.this, InnerActivity.class);
                    intent.putExtra(InnerActivity.EXTRA_ID, id);
                    String title = ((TextView) ((LinearLayout) view).getChildAt(0)).getText().toString();
                    intent.putExtra(InnerActivity.EXTRA_TITLE, title);
                    startActivity(intent);
                } else {
                    Toast.makeText(InnerActivity.this, R.string.no_children, Toast.LENGTH_SHORT).show();
                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(InnerActivity.this, CategoriesColumns.CONTENT_URI, null, CategoriesColumns.PARENTID + "=" + parentId, null, null);
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
