package premiumapp.org.yapay.ym_categories_tree_data_structure;

import android.content.AsyncTaskLoader;
import android.content.Context;

import premiumapp.org.yapay.Util;

public class CategoryTreeLoader extends AsyncTaskLoader<CategoryTree> {

    private Context mCtx;
    private CategoryTree mCategoryTree;

    public CategoryTreeLoader(Context context) {
        super(context);
        mCtx = context;
    }

    @Override
    public CategoryTree loadInBackground() {

        return Util.getCategoryTreeFromSqliteDb(mCtx);
    }
//
//    @Override
//    public void deliverResult(CategoryTree data) {
//
//        if (isReset()) {
//            return;
//        }
//        mCategoryTree = data;
//
//        if (isStarted()) {
//            super.deliverResult(data);
//        }
//    }
//
//    @Override
//    protected void onStartLoading() {
//        if (mCategoryTree != null) {
//            deliverResult(mCategoryTree);
//        }
//    }
}
