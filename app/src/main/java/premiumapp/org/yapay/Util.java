package premiumapp.org.yapay;

import android.content.Context;
import android.database.Cursor;
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

        for (Category category : categories) {

            // fill categories table
            String categoryName = category.getName();

            db.execSQL(String.format(Cv.SQL_INSERT_CATEGORY, categoryName));

            saveSubCatRecursive(db, category);
        }
    }

    private static void saveSubCatRecursive(SQLiteDatabase db, ParentCategory parent) {

        Set<SubCategory> subCategories = parent.getSubcategories();

        if (subCategories == null) {
            return;
        }

        String parentName = parent.getName();

        for (SubCategory sc : subCategories) {

            // fill subcategories table
            db.execSQL(String.format(Cv.SQL_INSERT_SUBCATEGORY, sc.getId(), sc.getName(), parentName));

            saveSubCatRecursive(db, sc);
        }
    }

    public static CategoryTree getCategoryTreeFromSqliteDb(Context ctx) {

        SQLiteDatabase db = new CategoriesDbHelper(ctx).getReadableDatabase();

        Cursor catCursor = db.rawQuery(Cv.SQL_SELECT_CATEGORIES, null);

        Category[] categories = new Category[catCursor.getCount()];

        int i = 0;
        while (catCursor.moveToNext()) {

            String catName = catCursor.getString(catCursor.getColumnIndex(Cv.COL_NAME));

            Category category = new Category(catName);

            fillSubCategoriesRecursive(db, category);

            categories[i] = category;
            i++;
        }
        return new CategoryTree(categories);
    }

    private static void fillSubCategoriesRecursive(SQLiteDatabase db, ParentCategory parent) {

        Cursor subCursor =
                db.rawQuery(String.format(Cv.SQL_SELECT_SUBCATEGORIES, parent.getName()), null);

        if (subCursor.moveToFirst()) {

            do {
                SubCategory subCategory = new SubCategory(
                        subCursor.getInt(subCursor.getColumnIndex(Cv.COL_ID)),
                        subCursor.getString(subCursor.getColumnIndex(Cv.COL_NAME))
                );

                fillSubCategoriesRecursive(db, subCategory);

                parent.addSubcategory(subCategory);

            } while (subCursor.moveToNext());
        }
    }
}
