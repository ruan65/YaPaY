package premiumapp.org.yapay.ym_categories_tree_data_structure;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import de.greenrobot.event.EventBus;
import premiumapp.org.yapay.Cv;
import premiumapp.org.yapay.Util;
import premiumapp.org.yapay.services.YmConnectService;

public class CategoryTreeLoader extends AsyncTaskLoader<CategoryTree> {

    private Context mCtx;

    public CategoryTreeLoader(Context context) {
        super(context);
        mCtx = context;
        EventBus.getDefault().register(this);
        onContentChanged();
    }

    @Override
    public CategoryTree loadInBackground() {

        CategoryTree categoryTree = Util.getCategoryTreeFromSqliteDb(mCtx);

        if (categoryTree == null) {

            String categoriesFromYmServer = YmConnectService.getCategoriesFromYmServer();

            if (categoriesFromYmServer != null) {  // It could be if app starts first time

                try {
                    categoryTree = new CategoryTree(categoriesFromYmServer);
                    Log.d(Cv.LOG_TAG, "Got data from server");

                    Util.recreateCategoryTreeSqliteDb(mCtx, categoryTree);  // Save data to the local db

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mCtx, "Something wrong with YM server response... ((",
                            Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Log.d(Cv.LOG_TAG, "Got data from Sqlite Db");
        }
        return categoryTree;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(DataChangedEvent event) {
        onContentChanged();
        Toast.makeText(mCtx, "Data has refreshed!!!", Toast.LENGTH_LONG).show();
    }
}
