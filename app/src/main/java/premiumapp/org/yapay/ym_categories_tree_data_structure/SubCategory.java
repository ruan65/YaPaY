package premiumapp.org.yapay.ym_categories_tree_data_structure;

public class SubCategory {

    private long id;
    private String name;

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

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
