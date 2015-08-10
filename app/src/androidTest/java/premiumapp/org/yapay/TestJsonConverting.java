package premiumapp.org.yapay;

import android.test.AndroidTestCase;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import premiumapp.org.yapay.ym_categories_tree_data_structure.Category;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryTree;
import premiumapp.org.yapay.ym_categories_tree_data_structure.SubCategory;

public class TestJsonConverting extends AndroidTestCase {

    public void testGetCategoryTreeFromJsonString() throws JSONException {

        CategoryTree categoryTree = new CategoryTree(TestUtil.testJsonString);

        Category[] categories = categoryTree.getCategories();

        assertEquals("Телефон", categories[0].getName());
        assertEquals("А также:", categories[9].getName());

        Set<SubCategory> subCategories = categories[9].getSubCategories();

        Set<String> testSet = new HashSet<>(Arrays.asList(new String[] {
                "Онлайн-аукционы",
                "Благотворительность",
                "Прочее"
        }));

        for (SubCategory sc : subCategories) {
            testSet.remove(sc.getName());
        }
        assertTrue(testSet.isEmpty());
    }
}
