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
        tableNamesSet.add(Cv.CATEGORIES_TABLE_NAME);
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

    public void testCategoryTable() {

        String phone = "Телефон";
        String www = "Интернет и ТВ";

        Set<String> catNames = new HashSet<>();
        catNames.add(phone);
        catNames.add(www);

        setUp();

        SQLiteDatabase db = new CategoriesDbHelper(mContext).getWritableDatabase();

        db.execSQL(String.format(Cv.SQL_INSERT_CATEGORY, 0, phone));
        db.execSQL(String.format(Cv.SQL_INSERT_CATEGORY, 1, www));

        Cursor cursor = db.query(Cv.CATEGORIES_TABLE_NAME, null, null, null, null, null, null);

        assertEquals(2, cursor.getCount());
        assertEquals(2, cursor.getColumnCount());

        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndex(Cv.COL_NAME));
            catNames.remove(category);
        }
        assertTrue(catNames.isEmpty());

        cursor.close();
        db.close();
    }

    public void testSubcategoryTable() {

        String phone = "Городской телефон";
        String mob = "Мобильная связь";
        String ip = "IP-телефония";

        Set<String> catNames = new HashSet<>();
        catNames.add(phone);
        catNames.add(mob);
        catNames.add(ip);

        SQLiteDatabase db = new CategoriesDbHelper(mContext).getWritableDatabase();

        db.execSQL(String.format(Cv.SQL_INSERT_SUBCATEGORY, 524624, phone, 0));
        db.execSQL(String.format(Cv.SQL_INSERT_SUBCATEGORY, 157291, mob, 0));
        db.execSQL(String.format(Cv.SQL_INSERT_SUBCATEGORY, 157298, ip, 0));

        Cursor cursor = db.query(Cv.SUBCATEGORIES_TABLE_NAME, null, null, null, null, null, null);

        assertEquals(3, cursor.getCount());
        assertEquals(3, cursor.getColumnCount());

        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndex(Cv.COL_NAME));
            catNames.remove(category);
        }
        assertTrue(catNames.isEmpty());

        cursor.close();
        db.close();
    }
}
