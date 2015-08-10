package premiumapp.org.yapay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Set;

import de.greenrobot.event.EventBus;
import premiumapp.org.yapay.services.YmConnectService;
import premiumapp.org.yapay.ym_categories_tree_data_structure.Category;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryTree;
import premiumapp.org.yapay.ym_categories_tree_data_structure.ParentCategory;
import premiumapp.org.yapay.ym_categories_tree_data_structure.SubCategory;

public class Main extends AppCompatActivity {

    private TextView mJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJson = (TextView) findViewById(R.id.json_result);

        EventBus.getDefault().registerSticky(this); // singleton event bus for all events across app
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                startService(new Intent(this, YmConnectService.class));
        }
        return true;
    }

    public void onEventMainThread(CategoryTree tree) {

        StringBuilder sb = new StringBuilder();

        Category[] categories = tree.getCategories();

        for (Category category : categories) {

            String name = category.getName();
            sb.append(name).append(name.endsWith(":") ? "\n" : ":\n");

            fillSubcategoriesRecur(sb, category);
        }
        mJson.setText(sb);
    }

    private void fillSubcategoriesRecur(StringBuilder sb, ParentCategory parentCategory) {

        Set<SubCategory> subCategories = parentCategory.getSubcategories();

        if (subCategories == null) {
            return;
        }

        for (SubCategory subCategory : subCategories) {
            sb.append("  - ").append(subCategory.getName()).append("\n");
            fillSubcategoriesRecur(sb, subCategory);
        }
    }
}
