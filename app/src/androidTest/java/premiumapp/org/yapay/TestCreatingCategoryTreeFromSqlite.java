package premiumapp.org.yapay;

import android.test.AndroidTestCase;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryTree;
import premiumapp.org.yapay.ym_categories_tree_data_structure.SubCategory;

public class TestCreatingCategoryTreeFromSqlite extends AndroidTestCase {

    public void testGetCategoryTreeFromSqlite() throws JSONException {

        Util.recreateCategoryTreeSqliteDb(mContext, new CategoryTree(TestUtil.testJsonString));

        CategoryTree categoryTree = Util.getCategoryTreeFromSqliteDb(mContext);

        assertEquals(10, categoryTree.getCategories().length);

        Set<String> testCatSet = new HashSet<>(Arrays.asList(new String[] {
                "Телефон", "Интернет и ТВ", "Развлечения"
        }));

        for (SubCategory c : categoryTree.getCategories()) {

            testCatSet.remove(c.getName());
        }
        assertTrue(testCatSet.isEmpty());

        assertEquals(3, categoryTree.getCategories()[0].getSubcategories().size());

        assertEquals(1, categoryTree.getCategories()[2].getSubcategories().size());

        Set<String> testSubCatSet = new HashSet<>(Arrays.asList(new String[] {
                "Проводной интернет", "Телевидение", "Беспроводной интернет"
        }));

        for (SubCategory sc : categoryTree.getCategories()[1].getSubcategories()) {

            testSubCatSet.remove(sc.getName());
        }

        assertTrue(testSubCatSet.isEmpty());

        // test adding subcategories recursively

        Set<String> testSubCatRecursiveSet = new HashSet<>(Arrays.asList(new String[] {
                "Жанры:Казуальные", "Жанры:Онлайн-игры", "Жанры:Игровые артефакты"
        }));

        for (SubCategory subCategory : categoryTree.getCategories()[4].getSubcategories()) {

            if (subCategory.getName().equals("Игры")) {
                assertEquals(false, subCategory.getSubcategories() == null);

                for (SubCategory sub : subCategory.getSubcategories()) {
                    testSubCatRecursiveSet.remove(sub.getName());
                }
            }
        }
        assertTrue(testSubCatRecursiveSet.isEmpty());
    }
}
