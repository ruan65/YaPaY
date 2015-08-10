package premiumapp.org.yapay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import premiumapp.org.yapay.db.CategoriesDbHelper;
import premiumapp.org.yapay.ym_categories_tree_data_structure.Category;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryTree;
import premiumapp.org.yapay.ym_categories_tree_data_structure.ParentCategory;
import premiumapp.org.yapay.ym_categories_tree_data_structure.SubCategory;

public class Util {

    public static List<Category> getCategoryTreeFromJsonString(String json) throws JSONException {

        List<Category> categoriesTree = new ArrayList<>();

        JSONArray categoriesTreeJSON = new JSONArray(json);

        for (int i = 0; i < categoriesTreeJSON.length(); i++) {

            JSONObject categoryJSON = categoriesTreeJSON.getJSONObject(i);

            Category category = new Category(categoryJSON.getString(Cv.C_TITLE));

            fillSubcategories(categoryJSON, category);

            categoriesTree.add(category);
        }
        return categoriesTree;
    }

    private static void fillSubcategories(JSONObject categoryJSON, ParentCategory category)
            throws JSONException {

        JSONArray subcategoriesJSON = categoryJSON.optJSONArray(Cv.SC_ARRAY);

        if (subcategoriesJSON == null) {
            return;
        }

        for (int j = 0; j < subcategoriesJSON.length(); j++) {

            JSONObject subcategoryJSON = subcategoriesJSON.optJSONObject(j);

            SubCategory subCategory = new SubCategory(
                    subcategoryJSON.getLong(Cv.SC_ID),
                    subcategoryJSON.getString(Cv.SC_TITLE));

            fillSubcategories(subcategoryJSON, subCategory);  // fill subcategories recursively

            category.addSubcategory(subCategory);
        }
    }

    public static void recreateCategoryTreeSqliteDb(Context ctx, CategoryTree categoryTreeObject) {

        ctx.deleteDatabase(Cv.DB_NAME);

        SQLiteDatabase db = new CategoriesDbHelper(ctx).getWritableDatabase();

        Category[] categories = categoryTreeObject.getCategories();

        for (int i = 0; i < categories.length; i++) {

            Category category = categories[i];

            // fill categories table
            db.execSQL(String.format(Cv.SQL_INSERT_CATEGORY, i, category.getName()));

            Set<SubCategory> subCategories = category.getSubcategories();

            for (SubCategory sc : subCategories) {

                // fill subcategories table
                db.execSQL(String.format(Cv.SQL_INSERT_SUBCATEGORY, sc.getId(), sc.getName(), i));
            }
        }
    }
}
