package muratk.yds.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import muratk.yds.Database.Database;
import muratk.yds.Models.QuestionModel;
import muratk.mkkpss.R;
import me.drakeet.materialdialog.MaterialDialog;

public class QuizActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ArrayList<QuestionModel> questionModels;
    int question_count = 0;
    int total_point = 0;
    int question_point = 0;
    TextView txt_question;
    TextView txt_question_point;
    Button btn_a_option;
    Button btn_b_option;
    Button btn_c_option;
    Button btn_d_option;
    Button btn_time;
    Button btn_next;
    String str_answer = "";
    Timer timer;
    int time = 15;
    int theHighScore = 0;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        SharedPreferences preferences = getSharedPreferences("MYPREFERENCES", MODE_PRIVATE);
        theHighScore = Integer.parseInt(preferences.getString("SCORES", "0"));

        txt_question = (TextView) findViewById(R.id.txt_soru);
        txt_question_point = (TextView) findViewById(R.id.txt_puan_degeri);

        btn_time = (Button) findViewById(R.id.btn_kalan_sure);
        btn_a_option = (Button) findViewById(R.id.btn_a_option);
        btn_b_option = (Button) findViewById(R.id.btn_b_option);
        btn_c_option = (Button) findViewById(R.id.btn_c_option);
        btn_d_option = (Button) findViewById(R.id.btn_d_option);
        btn_next = (Button) findViewById(R.id.btn_gec);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("YDS Words");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Database dbOpenHelper = new Database(this, "english.sqlite");
        dbOpenHelper.openDataBase();
        questionModels = dbOpenHelper.GetAllQuestion();

        SetQuestionAndOption();
    }

    public void SetButtonEnabled(Boolean b){
        btn_a_option.setEnabled(b);
        btn_b_option.setEnabled(b);
        btn_c_option.setEnabled(b);
        btn_d_option.setEnabled(b);

        if (b){
            btn_a_option.setBackgroundResource(R.drawable.round_button_dark_grey_blue);
            btn_b_option.setBackgroundResource(R.drawable.round_button_dark_grey_blue);
            btn_c_option.setBackgroundResource(R.drawable.round_button_dark_grey_blue);
            btn_d_option.setBackgroundResource(R.drawable.round_button_dark_grey_blue);
        }
    }

    public void SetQuestionAndOption(){
        time = 15;

        final Handler handler = new Handler();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        time--;
                        btn_time.setText(Integer.toString(time));
                        if (time <= 0){
                            finish();
                            if (total_point > theHighScore){
                                theHighScore = total_point;
                                SharedPreferences scores = getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = scores.edit();
                                editor.putString("SCORES", String.valueOf(total_point));
                                editor.commit();
                            }
                            startActivity(new Intent(QuizActivity.this, GameOverActivity.class).putExtra("type", "time").putExtra("score", Integer.toString(total_point)).putExtra("best", Integer.toString(theHighScore)).putExtra("question", questionModels.get(question_count - 1).getIngilizce()).putExtra("answer", questionModels.get(question_count - 1).getTurkce()));
                            timer.cancel();
                            timer = null;
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(doAsynchronousTask, 0, 1000);

        Random r = new Random();
        int i1 = r.nextInt(8) + 1;
        question_point = i1 * 12;
        txt_question_point.setText("Point : " + question_point);

        txt_question.setText("Soru " + (question_count + 1) + "\n\n" + questionModels.get(question_count).getIngilizce());

        Random rand_option = new Random();
        int opt = rand_option.nextInt(4);

        if (opt == 0){
            btn_a_option.setText("A)  " + questionModels.get((question_count + 45) % questionModels.size()).getTurkce());
            btn_b_option.setText("B)  " + questionModels.get(question_count).getTurkce());
            btn_c_option.setText("C)  " + questionModels.get((question_count + 53) % questionModels.size()).getTurkce());
            btn_d_option.setText("D)  " + questionModels.get((question_count + 87) % questionModels.size()).getTurkce());
        }
        else if(opt == 1){
            btn_a_option.setText("A)  " + questionModels.get(question_count).getTurkce());
            btn_b_option.setText("B)  " + questionModels.get((question_count + 45) % questionModels.size()).getTurkce());
            btn_c_option.setText("C)  " + questionModels.get((question_count + 53) % questionModels.size()).getTurkce());
            btn_d_option.setText("D)  " + questionModels.get((question_count + 87) % questionModels.size()).getTurkce());
        }
        else if(opt == 2){
            btn_a_option.setText("A)  " + questionModels.get((question_count + 53) % questionModels.size()).getTurkce());
            btn_b_option.setText("B)  " + questionModels.get((question_count + 45) % questionModels.size()).getTurkce());
            btn_c_option.setText("C)  " + questionModels.get(question_count).getTurkce());
            btn_d_option.setText("D)  " + questionModels.get((question_count + 87) % questionModels.size()).getTurkce());
        }
        else {
            btn_a_option.setText("A)  " + questionModels.get((question_count + 87) % questionModels.size()).getTurkce());
            btn_b_option.setText("B)  " + questionModels.get((question_count + 45) % questionModels.size()).getTurkce());
            btn_c_option.setText("C)  " + questionModels.get((question_count + 53) % questionModels.size()).getTurkce());
            btn_d_option.setText("D)  " + questionModels.get(question_count).getTurkce());
        }

        btn_next.setVisibility(View.GONE);

        str_answer = questionModels.get(question_count).getTurkce();
        SetButtonEnabled(true);
        Log.d("Cevap", str_answer);

        question_count++;
    }

    public void ChooseOption(View v){

        timer.cancel();

        SetButtonEnabled(false);

        if (((Button)v).getText().toString().toLowerCase().contains(str_answer.toLowerCase())){
            total_point = total_point + question_point;
            btn_next.setVisibility(View.VISIBLE);
            ((Button)v).setBackgroundResource(R.drawable.round_button_green);
        }
        else {
            finish();
            btn_next.setVisibility(View.GONE);
            ((Button)v).setBackgroundResource(R.drawable.round_button_red);
            if (total_point > theHighScore){
                theHighScore = total_point;
                SharedPreferences scores = getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = scores.edit();
                editor.putString("SCORES", String.valueOf(total_point));
                editor.commit();
            }
            startActivity(new Intent(QuizActivity.this, GameOverActivity.class).putExtra("type", "false").putExtra("score", Integer.toString(total_point)).putExtra("best", Integer.toString(theHighScore)).putExtra("question", questionModels.get(question_count - 1).getIngilizce()).putExtra("answer", questionModels.get(question_count - 1).getTurkce()));
        }
    }

    public void NextQuestion(View v){
        SetQuestionAndOption();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mMaterialDialog = new MaterialDialog(new ContextThemeWrapper(QuizActivity.this, R.style.MyAlertDialog))
                .setTitle("Uyarı")
                .setMessage("Yarıştan çıkmak istiyor musunuz?")
                .setPositiveButton("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timer.cancel();
                        timer = null;
                        finish();
                    }
                })
                .setNegativeButton("Hayır", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mMaterialDialog = new MaterialDialog(new ContextThemeWrapper(QuizActivity.this, R.style.MyAlertDialog))
                .setTitle("Uyarı")
                .setMessage("Yarıştan çıkmak istiyor musunuz?")
                .setPositiveButton("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timer.cancel();
                        timer = null;
                        finish();
                    }
                })
                .setNegativeButton("Hayır", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }
}
