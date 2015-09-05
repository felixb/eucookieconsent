package de.ub0r.android.eucookieconsent.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import de.ub0r.android.eucookieconsent.EuCookieConsentView;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        findViewById(R.id.reset_acknowledgement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EuCookieConsentView) findViewById(R.id.eucookieconsent)).resetAcknowledged();
            }
        });
    }

}
