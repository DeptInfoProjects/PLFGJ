package projet.license;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainHome extends Activity implements View.OnClickListener{
    private Button draw_detector, activity_rto ,time_detector;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);


        activity_rto = findViewById(R.id.rto_activity);
        activity_rto.setOnClickListener(this);

        draw_detector = findViewById(R.id.draw_detector);
        draw_detector.setOnClickListener(this);

        time_detector = findViewById(R.id.time_detector);
        time_detector.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        Log.e("PLUSIEURS ACTIVITES", "coucou");
        switch (v.getId()) {
            case R.id.draw_detector:
                 intent=new Intent(this, DrawDetectorActivity.class);
                break;

            case R.id.rto_activity:
                intent = new Intent(this,RtoActivity.class);
                break;

            case R.id.time_detector:
                intent = new Intent(this,TimeDetectorActivity.class);
                break;
        }
        if (intent != null) startActivity(intent);



    }


}
