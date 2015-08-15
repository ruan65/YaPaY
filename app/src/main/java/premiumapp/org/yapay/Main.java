package premiumapp.org.yapay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import premiumapp.org.yapay.events.PaymentEvent;
import premiumapp.org.yapay.services.YmConnectService;
import premiumapp.org.yapay.events.DataChangedEvent;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setElevation(0);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                startService(new Intent(this, YmConnectService.class));
        }
        return true;
    }

    public void onEventMainThread(DataChangedEvent event) {

        Snackbar.make(findViewById(R.id.fragment_categories),
                getString(R.string.data_refresh),
                Snackbar.LENGTH_LONG)
                .setAction(R.string.OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // nothing to do
                    }
                })
                .show();
    }

    public void onEventMainThread(PaymentEvent paymentEvent) {

        String message = String.format(getString(R.string.pay_message),
                paymentEvent.getSum(), paymentEvent.getVendor(), paymentEvent.getAccount());

        Snackbar.make(findViewById(R.id.fragment_categories),
                message,
                Snackbar.LENGTH_LONG)
                .setAction(R.string.OK, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // do nothing - just close snack-bar
                    }
                })
                .show();
    }
}
