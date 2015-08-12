package premiumapp.org.yapay.ym_categories_tree_data_structure;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import premiumapp.org.yapay.Cv;
import premiumapp.org.yapay.R;

public class CategoryAdapter extends CursorAdapter {


    public CategoryAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_item_category, parent, false);
        v.setTag(new ViewHolderCategory(v));

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolderCategory holder = (ViewHolderCategory) view.getTag();

        holder.categoryView.setText(cursor.getString(cursor.getColumnIndex(Cv.COL_NAME)));

    }

    public static class ViewHolderCategory {

        public final TextView categoryView;

        public ViewHolderCategory(View view) {
            categoryView = (TextView) view.findViewById(R.id.tv_category);
        }
    }
}
