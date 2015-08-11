package premiumapp.org.yapay.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import premiumapp.org.yapay.Cv;

public class CategoriesDbHelper extends SQLiteOpenHelper {

    public CategoriesDbHelper(Context context) {
        super(context, Cv.DB_NAME, null, Cv.DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Cv.SQL_CREATE_SUBCATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Cv.SQL_DROP_SUBCATEGORIES_TABLE);
        onCreate(db);
    }
}
