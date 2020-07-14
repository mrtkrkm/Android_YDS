package muratk.yds.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import muratk.mkkpss.R;

public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NextActivity();
            }
        }, 2000);
    }

    public void NextActivity(){
        Intent main = new Intent(this, MenuActivity.class);
        startActivity(main);
        finish();
    }
}
