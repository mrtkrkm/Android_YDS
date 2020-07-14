package muratk.yds.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import muratk.mkkpss.R;
import me.drakeet.materialdialog.MaterialDialog;

public class GameOverActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String type = b.getString("type", "false");
        String score = b.getString("score", "0");
        String best = b.getString("best", "0");
        final String soru = b.getString("question", "0");
        final String cevap = b.getString("answer", "0");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                mMaterialDialog = new MaterialDialog(new ContextThemeWrapper(GameOverActivity.this, R.style.MyAlertDialog))
                        .setTitle(soru)
                        .setMessage(cevap)
                        .setNegativeButton("Tamam", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        });
                mMaterialDialog.show();
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8034656764343756/7294772426");
        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });

        TextView txt_ust_type = (TextView) findViewById(R.id.txt_top_message);
        TextView txt_alt_type = (TextView) findViewById(R.id.txt_bottom_message);
        TextView txt_point = (TextView) findViewById(R.id.txt_point);
        TextView txt_highscore = (TextView) findViewById(R.id.txt_highscore);

        if (type.equalsIgnoreCase("false")){
            txt_ust_type.setText("Yanlış Cevap");
            txt_alt_type.setText("Üzgünüz Yanlış Cevap Verdiniz.");
        }
        else {
            txt_ust_type.setText("Süreniz Bitti");
            txt_alt_type.setText("Üzgünüz Size Verilen Süre Bitti.");
        }

        txt_point.setText("Puanınız : " + score);
        txt_highscore.setText("En Yüksek Puanınız : " + best);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    public void PlayAgain(View v){
        finish();
        startActivity(new Intent(GameOverActivity.this, QuizActivity.class));
    }

    public void GoHome(View v){
        finish();
    }

    public void RateUs(View v){
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
