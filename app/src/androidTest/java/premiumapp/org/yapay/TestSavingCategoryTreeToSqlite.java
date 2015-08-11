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

    public void testFunctionRecreateCategoryTreeSqliteDb() throws JSONException {

        Util.recreateCategoryTreeSqliteDb(mContext, new CategoryTree(TestUtil.testJsonString));

        SQLiteDatabase db = new CategoriesDbHelper(mContext).getReadableDatabase();

        Cursor cursor = db.rawQuery(Cv.SQL_SELECT_CATEGORIES, null);

        assertEquals(3, cursor.getColumnCount());
        assertEquals(10, cursor.getCount());

        cursor = db.rawQuery(Cv.SQL_SELECT_SUBCATEGORIES, null);

        assertEquals(29, cursor.getCount());

        cursor = db.rawQuery("SELECT * FROM " + Cv.SUBCATEGORIES_TABLE_NAME, null);

        assertEquals(39, cursor.getCount());

        Set<String> testSet = new HashSet<>(Arrays.asList(new String[]{
                "Онлайн-аукционы",
                "Благотворительность",
                "Прочее",
                "Мобильная связь",
                "Телевидение",
                "Музыка и фильмы",
                "Жанры:Action и шутеры"
        }));

        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndex(Cv.COL_NAME));
            testSet.remove(category);
        }

        assertTrue(testSet.isEmpty());

        cursor.close();
        db.close();
    }
}
