package premiumapp.org.yapay.ym_categories_tree_data_structure;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import premiumapp.org.yapay.Cv;
import premiumapp.org.yapay.R;

public class LocalDialogListAdapter extends ArrayAdapter<SubCategory> {

    public LocalDialogListAdapter(Context context, List<SubCategory> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SubCategory subcategory = getItem(position);

        Log.d(Cv.LOG_TAG, subcategory.toString());

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_list_item, parent, false);
        }

        TextView tvSubcat = (TextView) convertView.findViewById(R.id.tv_dialog_subcat);
        tvSubcat.setText(subcategory.getName());

        tvSubcat.setTag(subcategory);  // pass branch of CategoryTree further

        return convertView;
    }
}
