package premiumapp.org.yapay.ym_categories_tree_data_structure;

import java.util.HashSet;
import java.util.Set;

public class SubCategory implements ParentCategory {

    private long id;
    private String name;
    private Set<SubCategory> subCategories;

    public SubCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSubcategory(SubCategory subCategory) {

        if (subCategories == null) {
            subCategories = new HashSet<>();
        }
        subCategories.add(subCategory);
    }

    @Override
    public Set<SubCategory> getSubcategories() {
        return subCategories;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subCategories=" + subCategories +
                '}';
    }
}
