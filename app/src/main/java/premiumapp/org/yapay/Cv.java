package premiumapp.org.yapay;


// Cv stands for Constants Values
public interface Cv {

    String LOG_TAG = "yaplachy";

    String YM_CATEGORIES_URL = "https://money.yandex.ru/api/categories-list";

    String YM_SERVICE_NAME = "ym_connect_service";

    // YD https json categories-list response keys
    String C_TITLE = "title";
    String SC_TITLE = "title";
    String SC_ARRAY = "subs";
    String SC_ID = "id";

    // Db
    String DB_NAME = "yapay.db";
    int DB_VER = 1;

    String CATEGORIES_TABLE_NAME = "categories";

    String SUBCATEGORIES_TABLE_NAME = "subcategories";

    String COL_NAME = "name";
    String COL_ID = "_id";
    String COL_CATEGORY_ID = "category_id";

    String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " + CATEGORIES_TABLE_NAME + " (" +
            "_id INTEGER PRIMARY KEY, " +
            COL_NAME + " TEXT NOT NULL, " +
            "UNIQUE (" + COL_NAME + ") ON CONFLICT IGNORE);";

    String SQL_CREATE_SUBCATEGORIES_TABLE = "CREATE TABLE " + SUBCATEGORIES_TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_NAME + " TEXT NOT NULL, " +
            COL_CATEGORY_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + COL_CATEGORY_ID + ") REFERENCES "
            + CATEGORIES_TABLE_NAME + " (" + COL_ID + "));";

    String SQL_DROP_CATEGORIES_TABLE = "DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME;
    String SQL_DROP_SUBCATEGORIES_TABLE = "DROP TABLE IF EXISTS " + SUBCATEGORIES_TABLE_NAME;

    String SQL_INSERT_CATEGORY = "INSERT INTO " + CATEGORIES_TABLE_NAME + " (" +
            COL_ID + ", " + COL_NAME + ") VALUES (%d, '%s');";

    String SQL_INSERT_SUBCATEGORY = "INSERT INTO " + SUBCATEGORIES_TABLE_NAME + " (" +
            COL_ID + ", " + COL_NAME + ", " + COL_CATEGORY_ID + ") VALUES (%d, '%s', %d);";
}
