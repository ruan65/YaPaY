package premiumapp.org.yapay.ym_categories_tree_data_structure;

import java.util.Set;

public interface ParentCategory {

    void addSubcategory(SubCategory subCategory);

    Set<SubCategory> getSubcategories();
}
