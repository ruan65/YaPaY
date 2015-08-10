package premiumapp.org.yapay;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import premiumapp.org.yapay.db.CategoriesDbHelper;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryTree;

public class TestSavingCategoryTreeToSqlite extends AndroidTestCase {

    public void testDbSavingAndReading() throws JSONException {

        Util.recreateCategoryTreeSqliteDb(mContext, new CategoryTree(TestUtil.testJsonString));

        SQLiteDatabase db = new CategoriesDbHelper(mContext).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Cv.SUBCATEGORIES_TABLE_NAME, null);

        assertEquals(10, cursor.getCount());

        cursor = db.rawQuery("SELECT * FROM " + Cv.SUBCATEGORIES_TABLE_NAME, null);

        Set<String> testSet = new HashSet<>(Arrays.asList(new String[]{
                "Онлайн-аукционы",
                "Благотворительность",
                "Прочее",
                "Мобильная связь",
                "Телевидение",
                "Музыка и фильмы"
        }));

        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndex(Cv.COL_NAME));
            testSet.remove(category);
        }

        assertEquals(20, cursor.getCount());
        assertTrue(testSet.isEmpty());

        cursor.close();
        db.close();
    }
}
