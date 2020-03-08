package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchActivity extends AppCompatActivity {
    private static final String HASIL_KEY = "hasil";
    private static final String LOGO_KEY = "logo";
    private static final String PEMAIN_KEY = "Pemain";
    private TextView homeText;
    private TextView awayText;
    private TextView homepemain;
    private TextView awaypemain;
    private TextView hometime;
    private TextView awaytime;
    private ImageView homelogo;
    private ImageView awaylogo;
    int skorHome=0;
    int skorAway=0;
    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final int HOME_REQUEST= 1;
    private static final int AWAY_REQUEST = 2;
    private String [] pemainhome;
    private String [] pemainaway;
    private String [] pemain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        //TODO
        homeText = findViewById(R.id.txt_home);
        awayText = findViewById(R.id.txt_away);
        homelogo = findViewById(R.id.home_logo);
        awaylogo = findViewById(R.id.away_logo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            // TODO: display value here
            Bundle extra = getIntent().getExtras();
            Bitmap bmp = extra.getParcelable("imageHome");
            Bitmap bmp2 = extra.getParcelable("imageAway");

            homelogo.setImageBitmap(bmp);
            awaylogo.setImageBitmap(bmp2);

            homeText.setText(extras.getString("home"));
            awayText.setText(extras.getString("away"));

            //1.Menampilkan detail match sesuai data dari main activity
            //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
            //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
            //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode==HOME_REQUEST) {
            if (data != null) {
                Bundle extras = data.getExtras();
                homepemain = findViewById(R.id.home_pemain);
                homepemain.setText(extras.getString("scorer"));
                String input = homepemain.getText().toString();
                pemainhome =  input.split(",");
                hometime = findViewById(R.id.home_time);
                hometime.setText(extras.getString("time"));
                skorHome++;
                TextView scoreView=findViewById(R.id.score_home);
                scoreView.setText(String.valueOf(skorHome));
            }
        }else if (requestCode==AWAY_REQUEST) {
            if (data != null) {
                Bundle extras = data.getExtras();
                awaypemain = findViewById(R.id.away_pemain);
                awaypemain.setText(extras.getString("scorer"));
                String input = awaypemain.getText().toString();
                pemainaway =  input.split(",");
                awaytime = findViewById(R.id.away_time);
                awaytime.setText(extras.getString("time"));
                skorAway++;
                TextView scoreView=findViewById(R.id.score_away);
                scoreView.setText(String.valueOf(skorAway));
            }
        }
    }

    public void addHome(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, HOME_REQUEST);
    }

    public void addAway(View view){
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, AWAY_REQUEST);
    }

    public void CekHasil(View view) {
        String skor="" ;
        homelogo.buildDrawingCache();
        awaylogo.buildDrawingCache();
        Bitmap imageHome = homelogo.getDrawingCache();
        Bitmap imageAway = awaylogo.getDrawingCache();
        Bundle extra =new Bundle();
        if(skorHome==skorAway){
            skor = "DRAW";
        } if(skorHome>skorAway){
            skor = homeText.getText().toString();
            extra.putParcelable(LOGO_KEY,imageHome);
            pemain = pemainhome;
        } if(skorHome<skorAway){
            skor = awayText.getText().toString();
            extra.putParcelable(LOGO_KEY,imageAway);
            pemain = pemainaway;
        }

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtras(extra);
        intent.putExtra(PEMAIN_KEY, pemain);
        intent.putExtra(HASIL_KEY, skor );
        startActivity(intent);
    }
}
