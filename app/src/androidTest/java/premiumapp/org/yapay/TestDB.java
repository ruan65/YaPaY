package premiumapp.org.yapay;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;
import java.util.Set;

import premiumapp.org.yapay.db.CategoriesDbHelper;

public class TestDB extends AndroidTestCase {

    void deleteTheDb() {
        mContext.deleteDatabase(Cv.DB_NAME);
    }

    public void setUp() {
        deleteTheDb();
    }

    public void testCreateDb() throws Throwable {

        final HashSet<String> tableNamesSet = new HashSet<>();
        tableNamesSet.add(Cv.SUBCATEGORIES_TABLE_NAME);
//        tableNamesSet.add("foo");

        setUp();

        SQLiteDatabase db = new CategoriesDbHelper(mContext).getWritableDatabase();

        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: The database has not been created correctly", c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNamesSet.remove(c.getString(0));
        } while( c.moveToNext() );

        assertTrue("Error: Database was created without categories or subcategories tables",
                tableNamesSet.isEmpty());

        db.close();
    }

    public void testCategoryInsertion() {

        String phone = "Телефон";
        String www = "Интернет и ТВ";

        Set<String> testSetCatNames = new HashSet<>();
        testSetCatNames.add(phone);
        testSetCatNames.add(www);

        setUp();

        SQLiteDatabase db = new CategoriesDbHelper(mContext).getWritableDatabase();

        db.execSQL(String.format(Cv.SQL_INSERT_CATEGORY, phone));
        db.execSQL(String.format(Cv.SQL_INSERT_CATEGORY, www));

        Cursor cursor = db.query(Cv.SUBCATEGORIES_TABLE_NAME, null, null, null, null, null, null);

        assertEquals(2, cursor.getCount());
        assertEquals(3, cursor.getColumnCount());

        while (cursor.moveToNext()) {

            String category = cursor.getString(cursor.getColumnIndex(Cv.COL_NAME));

            assertEquals(null, cursor.getString(cursor.getColumnIndex(Cv.COL_PARENT_NAME)));
            assertEquals(0, cursor.getInt(cursor.getColumnIndex(Cv.COL_ID)));

            testSetCatNames.remove(category);
        }
        assertTrue(testSetCatNames.isEmpty());

        cursor.close();
        db.close();
    }

    public void testSubcategoryInsertion() {

        String phone = "Городской телефон";
        String mob = "Мобильная связь";
        String ip = "IP-телефония";

        Set<String> catNames = new HashSet<>();
        catNames.add(phone);
        catNames.add(mob);
        catNames.add(ip);

        SQLiteDatabase db = new CategoriesDbHelper(mContext).getWritableDatabase();

        db.execSQL(String.format(Cv.SQL_INSERT_SUBCATEGORY, 524624, phone, "Телефон"));
        db.execSQL(String.format(Cv.SQL_INSERT_SUBCATEGORY, 157291, mob, "Телефон"));
        db.execSQL(String.format(Cv.SQL_INSERT_SUBCATEGORY, 157298, ip, "Телефон"));

        Cursor cursor = db.query(Cv.SUBCATEGORIES_TABLE_NAME, null, null, null, null, null, null);

        assertEquals(3, cursor.getCount());
        assertEquals(3, cursor.getColumnCount());

        while (cursor.moveToNext()) {

            String category = cursor.getString(cursor.getColumnIndex(Cv.COL_NAME));

            assertNotNull(cursor.getString(cursor.getColumnIndex(Cv.COL_PARENT_NAME)));
            assertTrue(cursor.getInt(cursor.getColumnIndex(Cv.COL_ID)) != 0);
            catNames.remove(category);
        }
        assertTrue(catNames.isEmpty());

        cursor.close();
        db.close();
    }
}
