package premiumapp.org.yapay.services;

import android.app.IntentService;
import android.content.Intent;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.greenrobot.event.EventBus;
import premiumapp.org.yapay.Cv;
import premiumapp.org.yapay.Util;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryTree;
import premiumapp.org.yapay.events.DataChangedEvent;

public class YmConnectService extends IntentService {

    public YmConnectService() {
        super(Cv.YM_SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String categoriesJsonString = getCategoriesFromYmServer();

        if (categoriesJsonString == null) return; // Stream was empty. No point in parsing.

        CategoryTree cTree = null;

        try {
            cTree = new CategoryTree(categoriesJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Util.recreateCategoryTreeSqliteDb(this, cTree);

        EventBus.getDefault().post(new DataChangedEvent());
    }

    public static String getCategoriesFromYmServer() {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String categoriesJsonString = null;

        try {

            URL url = new URL(Cv.YM_CATEGORIES_URL);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                builder.append(line);
            }

            if (builder.length() == 0) {
                return null;
            }

            categoriesJsonString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            if (connection != null) {
                connection.disconnect();
            }

            if (reader != null) {

                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return categoriesJsonString;
    }
}
