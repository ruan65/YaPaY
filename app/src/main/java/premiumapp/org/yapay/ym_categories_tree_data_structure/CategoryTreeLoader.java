package premiumapp.org.yapay.ym_categories_tree_data_structure;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import premiumapp.org.yapay.Util;

public class CategoryTreeLoader extends AsyncTaskLoader<CategoryTree> {

    private Context mCtx;

    public CategoryTreeLoader(Context context) {
        super(context);
        mCtx = context;
        onContentChanged();
    }

    @Override
    public CategoryTree loadInBackground() {
        return Util.getCategoryTreeFromSqliteDb(mCtx);
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }
}
