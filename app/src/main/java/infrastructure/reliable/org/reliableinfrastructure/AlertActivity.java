package infrastructure.reliable.org.reliableinfrastructure;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import infrastructure.reliable.org.reliableinfrastructure.general.Constants;

public class AlertActivity extends AppCompatActivity {

    private static final String TAG = "AlertActivity";
    TextView alertMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_activity);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.ALERT_MESSAGE);

        alertMessageTextView = (TextView) findViewById(R.id.alertMessage);
        alertMessageTextView.setText(message);
        alertMessageTextView.setMovementMethod(new ScrollingMovementMethod());
    }
}
