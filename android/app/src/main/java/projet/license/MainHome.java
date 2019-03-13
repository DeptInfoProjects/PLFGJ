package projet.license;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainHome extends Activity implements View.OnClickListener{
    private Button draw_detector, game2 ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);


        game2 = findViewById(R.id.game2);
        game2.setOnClickListener(this);

        draw_detector = findViewById(R.id.draw_detector);
        draw_detector.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        Log.e("PLUSIEURS ACTIVITES", "coucou");
        switch (v.getId()) {
            case R.id.draw_detector:
                 intent=new Intent(this, DrawDetectorActivity.class);
                    break;

            case R.id.game2:
                intent = new Intent(this,Game2Activity.class);
        }
        if (intent != null) startActivity(intent);



    }


}
