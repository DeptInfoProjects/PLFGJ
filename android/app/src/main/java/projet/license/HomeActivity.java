package projet.license;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity implements  View.OnClickListener {
    private Button draw_detector,rto_detector,time_detector,riddle,training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        draw_detector = findViewById(R.id.DrawDetector);
        draw_detector.setOnClickListener(this);

        time_detector = findViewById(R.id.TimeDetector);
        time_detector.setOnClickListener(this);

        rto_detector = findViewById(R.id.RtoDetector);
        rto_detector.setOnClickListener(this);

        training = findViewById(R.id.TrainingDetector);
        training.setOnClickListener(this);

        riddle = findViewById(R.id.Riddle);
        riddle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.DrawDetector:
                intent = new Intent(this,DrawDetectorActivity.class);
                break;
            case R.id.TimeDetector:
                intent = new Intent(this,TimeDetectorActivity.class);
                break;
            case R.id.Riddle:
                intent = new Intent(this,RiddleActivity.class);
                break;
            case R.id.TrainingDetector:
                intent = new Intent(this,TrainingActivity.class);
                break;
            case R.id.RtoDetector:
                intent = new Intent(this,RtoDetectorActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);

    }
}
