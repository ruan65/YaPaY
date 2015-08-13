package premiumapp.org.yapay.ym_categories_tree_data_structure;

import org.json.JSONException;

import java.util.List;

import premiumapp.org.yapay.Util;

public class CategoryTree {

    private SubCategory[] categories;

    public CategoryTree(String jsonString) throws JSONException {

        List<SubCategory> categoriesTree = Util.getCategoryTreeFromJsonString(jsonString);

        categories = new SubCategory[categoriesTree.size()];

        for (int i = 0; i < categories.length; i++) {

            categories[i] = categoriesTree.get(i);
        }
    }

    public CategoryTree(SubCategory[] categories) {
        this.categories = categories;
    }

    public SubCategory[] getCategories() {
        return categories;
    }
}
