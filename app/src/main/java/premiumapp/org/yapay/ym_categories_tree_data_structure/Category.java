package premiumapp.org.yapay.ym_categories_tree_data_structure;

import java.util.HashSet;
import java.util.Set;

public class Category implements ParentCategory {

    private String name;
    private Set<SubCategory> subCategories;

    public Category(String name) {
        this.name = name;
        subCategories = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<SubCategory> getSubcategories() {
        return subCategories;
    }

    public void addSubcategory(SubCategory subCategory) {

        subCategories.add(subCategory);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", subCategories=" + subCategories +
                '}';
    }
}
