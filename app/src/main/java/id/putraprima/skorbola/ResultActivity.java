package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ResultActivity extends AppCompatActivity {

    private TextView hasilText;
    private ImageView winlogo;
    private String[] pemain_win;
    private ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ListView listView = findViewById(R.id.listitem);


        winlogo = findViewById(R.id.logo_win);
        hasilText = findViewById(R.id.team_win);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            // TODO: display value here
            Bundle extra = getIntent().getExtras();
            Bitmap bmp = extra.getParcelable("logo");
            pemain_win = extras.getStringArray("Pemain");
            data = new ArrayList<>();
            getData();
            ArrayAdapter <String> adapter = new ArrayAdapter<>
                    (this, R.layout.support_simple_spinner_dropdown_item, data);
            listView.setAdapter(adapter);
            hasilText.setText("Congratulations "+extras.getString("hasil"));
            winlogo.setImageBitmap(bmp);
        }
    }
    private void getData(){
        //Memasukan Semua Data mahasiswa kedalam ArrayList
        Collections.addAll(data, pemain_win);
    }
}
