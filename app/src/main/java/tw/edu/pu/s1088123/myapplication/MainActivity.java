package tw.edu.pu.s1088123.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        EditText mEdit = findViewById(R.id.editText);
        Button btText = findViewById(R.id.button);
        dashbord mBoard = (dashbord) findViewById(R.id.dashbord);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    mBoard.setPercentage(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btText.setOnClickListener((view -> {
            EditText edMax, edMin;
            edMax = findViewById(R.id.edMax);
            edMin = findViewById(R.id.edMin);
            String s = mEdit.getText().toString();
            String ms = edMax.getText().toString();
            String ns = edMin.getText().toString();

            if (s.length()!=0 &&ms.length()!=0&&ns.length()!=0) {
                    mBoard.setPercontage(Float.parseFloat(s),Float.parseFloat(ms),Float.parseFloat(ns));
            }
        }));

    }
}