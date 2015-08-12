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

    String SUBCATEGORIES_TABLE_NAME = "subcategories";

    String COL_NAME = "name";
    String COL_ID = "_id";
    String COL_PARENT_NAME = "parent_name";

    String SQL_CREATE_SUBCATEGORIES_TABLE = "CREATE TABLE " + SUBCATEGORIES_TABLE_NAME + " (" +
            COL_ID + " INTEGER, " +
            COL_NAME + " TEXT NOT NULL, " +
            COL_PARENT_NAME + " TEXT, " +
            "FOREIGN KEY (" + COL_PARENT_NAME + ") REFERENCES " +
            SUBCATEGORIES_TABLE_NAME + " (" + COL_NAME + "));";

    String SQL_DROP_SUBCATEGORIES_TABLE = "DROP TABLE IF EXISTS " + SUBCATEGORIES_TABLE_NAME;

    String SQL_INSERT_CATEGORY = "INSERT INTO " + SUBCATEGORIES_TABLE_NAME + " (" +
            COL_NAME + ") VALUES ('%s');";

    String SQL_INSERT_SUBCATEGORY = "INSERT INTO " + SUBCATEGORIES_TABLE_NAME + " (" +
            COL_ID + ", " + COL_NAME + ", " + COL_PARENT_NAME + ") VALUES (%d, '%s', '%s');";

    String SQL_SELECT_CATEGORIES = "SELECT * FROM " + Cv.SUBCATEGORIES_TABLE_NAME +
            " WHERE " + Cv.COL_PARENT_NAME + " IS null;";

    String SQL_SELECT_SUBCATEGORIES = "SELECT * FROM " + Cv.SUBCATEGORIES_TABLE_NAME +
            " WHERE " + Cv.COL_PARENT_NAME + " = '%s';";
}
