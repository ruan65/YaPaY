package premiumapp.org.yapay;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import premiumapp.org.yapay.events.PaymentEvent;

public class PayActivity extends AppCompatActivity {

    @Bind(R.id.et_sum)
    EditText mEtSum;

    @Bind(R.id.account)
    TextView mAccount;

    private String mAccNumber;
    public static final String KEY_ACC = "acc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        ActionBar toolBar = getSupportActionBar();

        if (toolBar != null) {
            toolBar.setTitle(getIntent().getStringExtra(getString(R.string.title)));
        }

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mAccNumber = savedInstanceState.getString(KEY_ACC);
        } else {
            mAccNumber = new Random().nextInt(1_000_000_000) + "";
        }
        mAccount.setText(mAccNumber);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_ACC, mAccNumber);
    }

    public void onPayClick(View view) {

        String sum = mEtSum.getText().toString();
        if (sum.equals(getString(R.string.empty_string))) {

            Snackbar.make(mAccount, getString(R.string.sum_missing), Snackbar.LENGTH_LONG)
                    .setAction(R.string.OK, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // do nothing - just close snack-bar
                        }
                    })
                    .show();
        } else {
            EventBus.getDefault().post(new PaymentEvent(sum,
                    getIntent().getStringExtra(getString(R.string.title)),
                    mAccNumber
            ));
            finish();
        }
    }
}
