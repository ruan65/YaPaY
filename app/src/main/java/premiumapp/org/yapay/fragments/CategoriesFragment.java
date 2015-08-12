package premiumapp.org.yapay.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import premiumapp.org.yapay.Cv;
import premiumapp.org.yapay.R;
import premiumapp.org.yapay.db.CategoriesDbHelper;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryAdapter;

public class CategoriesFragment extends Fragment {

    private ListView mCategoriesList;
    private CategoryAdapter mCategoryAdapter;
    private Context mCtx;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCtx = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Cursor cursor = new CategoriesDbHelper(mCtx).getReadableDatabase().rawQuery(Cv.SQL_SELECT_CATEGORIES, null);

        mCategoryAdapter = new CategoryAdapter(mCtx, cursor, 0);

        View root = inflater.inflate(R.layout.fr_categories, container, false);

        mCategoriesList = (ListView) root.findViewById(R.id.listview_categories);
        mCategoriesList.setAdapter(mCategoryAdapter);

        return root;
    }
}
