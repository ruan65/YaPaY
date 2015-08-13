package premiumapp.org.yapay.ym_categories_tree_data_structure;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.Set;

import premiumapp.org.yapay.Cv;
import premiumapp.org.yapay.LocalDialog;
import premiumapp.org.yapay.R;

public class CategoryAdapter extends ArrayAdapter<SubCategory> {

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

        SubCategory subcategory = getItem(position);
        ViewHolderCategory viewHolderCategory = (ViewHolderCategory) view.getTag();
        viewHolderCategory.categoryName.setText(subcategory.getName());
        viewHolderCategory.categoryName.setOnClickListener(onCategoryClickListener);
        viewHolderCategory.categoryFrame.removeAllViews();

        Set<SubCategory> subcategories = subcategory.getSubcategories();

        for (SubCategory sc : subcategories) {

            TextView tv = (TextView) mInflater.inflate(R.layout.text_view_subcategory, null);

            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f);
            tv.setLayoutParams(layoutParams);

            String plus = "";

            if (sc.getSubcategories() != null) {
                plus = " +";
                tv.setTextColor(Color.parseColor("#ff0000"));
            }

            tv.setText(sc.getName() + plus);

            tv.setOnClickListener(onCategoryClickListener);

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

    View.OnClickListener onCategoryClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.tv_category:
                    break;

                case R.id.tv_dynamic_subcategory:
                    String subCatName = ((TextView) v).getText().toString();
                    Log.d(Cv.LOG_TAG, "Clicked!!!!!!!!!!!!!!!!!!! " + subCatName);

                    LocalDialog dialog = LocalDialog.newInstance(mCtx, null, subCatName);
                    dialog.show(((AppCompatActivity) mCtx).getSupportFragmentManager(), null);
                    break;
            }
        }
    };
}
