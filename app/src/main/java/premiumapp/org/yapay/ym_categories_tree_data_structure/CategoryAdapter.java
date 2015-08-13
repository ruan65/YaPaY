package premiumapp.org.yapay.ym_categories_tree_data_structure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.Set;

import premiumapp.org.yapay.R;

public class CategoryAdapter extends ArrayAdapter<Category> {

    private LayoutInflater mInflater;
    private Context mCtx;

    public CategoryAdapter(Context context) {
        super(context, R.layout.grid_item_category);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCtx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.grid_item_category, parent, false);
            view.setTag(new ViewHolderCategory(view));
        } else {
            view = convertView;
        }

        Category category = getItem(position);
        ViewHolderCategory viewHolderCategory = (ViewHolderCategory) view.getTag();
        viewHolderCategory.categoryName.setText(category.getName());
        viewHolderCategory.categoryFrame.removeAllViews();

        Set<SubCategory> subcategories = category.getSubcategories();

        for (SubCategory sc : subcategories) {

            TextView tv = (TextView) mInflater.inflate(R.layout.text_view_subcategory, null);

            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f);
            tv.setLayoutParams(layoutParams);

            tv.setText(sc.getName());

            viewHolderCategory.categoryFrame.addView(tv);
        }
        return view;
    }

    public static class ViewHolderCategory {

        public final TextView categoryName;
        public final LinearLayout categoryFrame;

        public ViewHolderCategory(View view) {
            categoryFrame = (LinearLayout) view.findViewById(R.id.grid_frame_subcategories);
            categoryName = (TextView) view.findViewById(R.id.tv_category);

        }
    }
}
