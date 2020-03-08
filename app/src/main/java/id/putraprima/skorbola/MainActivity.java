package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String HOMETEAM_KEY = "home";
    private static final String AWAYTEAM_KEY = "away";
    private static final String HOMEIMAGE_KEY = "imageHome";
    private static final String AWAYIMAGE_KEY = "imageAway";

    private EditText homeTeam;
    private EditText awayTeam;
    private ImageView homeImage;
    private ImageView awayImage;
    Bitmap bitmap1;
    Bitmap bitmap2;
    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE_HOME = 1;
    private static final int GALLERY_REQUEST_CODE_AWAY = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        homeTeam = findViewById(R.id.home_team);
        awayTeam = findViewById(R.id.away_team);
        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity
    }
    public void handleTeam(View view) {
        String home = homeTeam.getText().toString();
        String away = awayTeam.getText().toString();

        if(away.equals("")||home.equals("")||bitmap1==null||bitmap2==null||homeImage.equals("")||awayImage.equals("")){
            Toast.makeText(getApplicationContext(), "Data Harus diisi!",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, MatchActivity.class);
            homeImage.buildDrawingCache();
            awayImage.buildDrawingCache();
            Bitmap imageHome = homeImage.getDrawingCache();
            Bitmap imageAway = awayImage.getDrawingCache();
            Bundle extra =new Bundle();
            extra.putParcelable(HOMEIMAGE_KEY,imageHome);
            extra.putParcelable(AWAYIMAGE_KEY,imageAway);
            intent.putExtras(extra);
            intent.putExtra(HOMETEAM_KEY, home);
            intent.putExtra(AWAYTEAM_KEY, away);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode==GALLERY_REQUEST_CODE_HOME) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    homeImage.setImageBitmap(bitmap1);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }else if (requestCode==GALLERY_REQUEST_CODE_AWAY) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    awayImage.setImageBitmap(bitmap2);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleaway(View view) {
        Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(in, GALLERY_REQUEST_CODE_AWAY);
    }

    public void handlehome(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE_HOME);
    }
}
