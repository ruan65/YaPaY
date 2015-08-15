package premiumapp.org.yapay;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import premiumapp.org.yapay.ym_categories_tree_data_structure.LocalDialogListAdapter;
import premiumapp.org.yapay.ym_categories_tree_data_structure.SubCategory;

public class LocalDialog extends DialogFragment {

    protected Context mCtx;
    protected ListView mSubcategoriesList;
    protected SubCategory mSubCategory;
    protected String mHeaderText;

    public static LocalDialog newInstance(Context ctx, SubCategory sCat, String header) {

        LocalDialog ld = new LocalDialog();

        if (sCat != null) {
            ld.mSubCategory = sCat;
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

        ((TextView) window.findViewById(R.id.tv_dialog_header))
                .setText(mHeaderText.endsWith(mCtx.getString(R.string.plus)) ?
                        mHeaderText.substring(0, mHeaderText.length() - 2) : mHeaderText);

        mSubcategoriesList = (ListView) window.findViewById(R.id.subcategories_list);

        LocalDialogListAdapter adapter;

        if (mSubCategory != null && mSubCategory.getSubcategories() != null) {

            adapter = new LocalDialogListAdapter(mCtx,
                    new ArrayList<>(mSubCategory.getSubCategories()));

        } else {

            window.findViewById(R.id.list_header).setVisibility(View.VISIBLE);
            adapter = new LocalDialogListAdapter(mCtx, createVendorsList());
        }

        mSubcategoriesList.setAdapter(adapter);

        mSubcategoriesList.setOnItemClickListener(onItemClickListener);

        return dialog;
    }

    // I need to override next two methods to prevent app from crash on screen rotation
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    private List<SubCategory> createVendorsList() {

        List<SubCategory> vendors = new ArrayList<>(4);

        String[] stringArray = mCtx.getResources().getStringArray(R.array.vendors);

        for (String s : stringArray) {
            SubCategory sCut = new SubCategory(s);
            sCut.vendor = true;
            vendors.add(sCut);
        }
        return vendors;
    }

    protected AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            dismiss();

            TextView tv = (TextView) view.findViewById(R.id.tv_dialog_subcat);

            SubCategory subCategory = (SubCategory) tv.getTag();
            if (subCategory.vendor) {

                Intent intent = new Intent(mCtx, PayActivity.class);
                intent.putExtra(mCtx.getString(R.string.title), tv.getText());
                startActivity(intent);
            } else {
                LocalDialog dialog = LocalDialog.newInstance(
                        mCtx,
                        subCategory,
                        tv.getText().toString());

                try {
                    dialog.show(((AppCompatActivity) mCtx).getSupportFragmentManager(), null);
                } catch (Exception e) {
                    // for some reason after screen rotation and new call to show dialog
                    // the app can crash. I have not found the reason yet((
                    e.printStackTrace();
                }
            }
        }
    };
}
