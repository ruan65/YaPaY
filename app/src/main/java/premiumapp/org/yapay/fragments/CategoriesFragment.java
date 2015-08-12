package premiumapp.org.yapay.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;

import premiumapp.org.yapay.R;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryAdapter;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryTree;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryTreeLoader;

public class CategoriesFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<CategoryTree> {

    private ListView mCategoriesList;
    private CategoryAdapter mCategoryAdapter;
    private Context mCtx;

    private static final int LOADER_ID = 0xbee;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCtx = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getLoaderManager().initLoader(LOADER_ID, null, this);

        mCategoryAdapter = new CategoryAdapter(mCtx);

        View root = inflater.inflate(R.layout.fr_categories, container, false);

        mCategoriesList = (ListView) root.findViewById(R.id.listview_categories);
        mCategoriesList.setAdapter(mCategoryAdapter);

        return root;
    }

    @Override
    public Loader<CategoryTree> onCreateLoader(int id, Bundle args) {
        return new CategoryTreeLoader(mCtx);
    }

    @Override
    public void onLoadFinished(Loader<CategoryTree> loader, CategoryTree data) {

        switch (loader.getId()) {
            case LOADER_ID:
                mCategoryAdapter.clear();
                mCategoryAdapter.addAll(Arrays.asList(data.getCategories()));
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<CategoryTree> loader) {
    }
}
