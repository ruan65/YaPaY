package premiumapp.org.yapay.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import de.greenrobot.event.EventBus;
import premiumapp.org.yapay.Cv;
import premiumapp.org.yapay.R;
import premiumapp.org.yapay.Util;
import premiumapp.org.yapay.ym_categories_tree_data_structure.Category;
import premiumapp.org.yapay.ym_categories_tree_data_structure.CategoryTree;

public class YmConnectService extends IntentService {

    private EventBus bus = EventBus.getDefault();  // singleton event bus for all events across app

    public YmConnectService() {
        super(Cv.YM_SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String categories;

        try {

            URL url = new URL(Cv.YM_CATEGORIES_URL);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            if (inputStream == null) {
                return; // nothing to do
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {

                builder.append(line);
            }

            if (builder.length() == 0) {
                return; // Stream was empty.  No point in parsing.
            }

            categories = builder.toString();

            CategoryTree cTree = new CategoryTree(categories);

            bus.postSticky(cTree);

            Util.recreateCategoryTreeSqliteDb(this, cTree);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.err_json, Toast.LENGTH_LONG).show();

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
    }
}
