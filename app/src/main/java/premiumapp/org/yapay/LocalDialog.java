package premiumapp.org.yapay;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import premiumapp.org.yapay.ym_categories_tree_data_structure.SubCategory;
import premiumapp.org.yapay.ym_categories_tree_data_structure.LocalDialogListAdapter;

public class LocalDialog extends DialogFragment {

    protected Context mCtx;
    protected ListView mSubcategoriesList;
    protected List<SubCategory> subCategories;
    protected AdapterView.OnItemClickListener onItemClickListener;
    protected String mHeaderText;

    public static LocalDialog newInstance(Context ctx, Set<SubCategory> dataSet, String header) {

        LocalDialog ld = new LocalDialog();

        if (dataSet != null) {
            ld.subCategories = new ArrayList<>(dataSet);
        }
        ld.mCtx = ctx;
        ld.mHeaderText = header;
        return ld;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(mCtx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.local_dialog);

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));

        ((TextView) window.findViewById(R.id.tv_dialog_header)).setText(mHeaderText);

        mSubcategoriesList = (ListView) window.findViewById(R.id.subcategories_list);

        if (subCategories != null) {

            LocalDialogListAdapter adapter = new LocalDialogListAdapter(mCtx, subCategories);

            mSubcategoriesList.setAdapter(adapter);

            mSubcategoriesList.setOnItemClickListener(onItemClickListener);
        }
        return dialog;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
