package premiumapp.org.yapay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import premiumapp.org.yapay.ym_categories_tree_data_structure.Category;
import premiumapp.org.yapay.ym_categories_tree_data_structure.SubCategory;

public class Util {

    public static List<Category> getCategoryTreeFromJsonString(String json) throws JSONException {

        List<Category> categoriesTree = new ArrayList<>();

        JSONArray categoriesTreeJSON = new JSONArray(json);

        for (int i = 0; i < categoriesTreeJSON.length(); i++) {

            JSONObject categoryJSON = categoriesTreeJSON.getJSONObject(i);

            Category category = new Category(categoryJSON.getString(Cv.C_TITLE));

            JSONArray subcategoriesJSON = categoryJSON.getJSONArray(Cv.SC_ARRAY);

            for (int j = 0; j < subcategoriesJSON.length(); j++) {

                JSONObject subcategoryJSON = subcategoriesJSON.getJSONObject(j);

                SubCategory subCategory = new SubCategory(
                        subcategoryJSON.getLong(Cv.SC_ID),
                        subcategoryJSON.getString(Cv.SC_TITLE));

                category.addSubcategory(subCategory);
            }
            categoriesTree.add(category);
        }
        return categoriesTree;
    }
}
