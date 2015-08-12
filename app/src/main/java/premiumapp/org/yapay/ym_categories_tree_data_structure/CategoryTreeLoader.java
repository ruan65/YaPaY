package premiumapp.org.yapay.ym_categories_tree_data_structure;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class CategoryTreeLoader extends AsyncTaskLoader<CategoryTree> {


    public CategoryTreeLoader(Context context) {
        super(context);
    }

    @Override
    public CategoryTree loadInBackground() {
        return null;
    }
}
