package premiumapp.org.yapay.ym_categories_tree_data_structure;

import org.json.JSONException;

import java.util.List;

import premiumapp.org.yapay.Util;

public class CategoryTree {

    private Category[] categories;

    public CategoryTree(String jsonString) throws JSONException {

        List<Category> categoriesTree = Util.getCategoryTreeFromJsonString(jsonString);

        categories = new Category[categoriesTree.size()];

        for (int i = 0; i < categories.length; i++) {

            categories[i] = categoriesTree.get(i);
        }
    }

    public Category[] getCategories() {
        return categories;
    }
}
