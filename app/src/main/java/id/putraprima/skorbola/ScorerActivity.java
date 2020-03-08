package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class ScorerActivity extends AppCompatActivity {
    private static final String SCORER_KEY = "scorer";
    private static final String TIME_KEY = "time";
    private EditText time;
    private EditText scorer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);
        scorer = findViewById(R.id.editText_Scorer);
        time = findViewById(R.id.editText_Time);
    }

    public void handlepemain(View view) {
        String pemain = scorer.getText().toString();
        String waktu = time.getText().toString();
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra(SCORER_KEY, pemain);
        intent.putExtra(TIME_KEY, waktu);
        setResult(MatchActivity.RESULT_OK, intent);
        finish();
    }
}
