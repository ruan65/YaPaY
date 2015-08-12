package premiumapp.org.yapay.ym_categories_tree_data_structure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import premiumapp.org.yapay.R;

public class CategoryAdapter extends ArrayAdapter<Category> {

    private LayoutInflater mInflater;

    public CategoryAdapter(Context context) {
        super(context, R.layout.list_item_category);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.list_item_category, parent, false);
            view.setTag(new ViewHolderCategory(view));
        } else {
            view = convertView;
        }

        ((ViewHolderCategory) view.getTag()).categoryView.setText(getItem(position).getName());

        return view;
    }

    public static class ViewHolderCategory {

        public final TextView categoryView;

        public ViewHolderCategory(View view) {
            categoryView = (TextView) view.findViewById(R.id.tv_category);
        }
    }
}
