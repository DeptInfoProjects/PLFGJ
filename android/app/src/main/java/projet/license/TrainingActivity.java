package projet.license;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.graphics.Color.MAGENTA;

public class TrainingActivity extends Activity {
    private int i = 0;
    private PaintView myCanvas;
    private Button effacer,changeColor,help;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_activity);

        myCanvas =findViewById(R.id.canvas);
        effacer = findViewById(R.id.CLEAR);
        changeColor = findViewById(R.id.couleur);
        help = findViewById(R.id.HELP);

        effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.clear();
            }
        });

        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.mPaint.setColor(getColor()); }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TrainingActivity.this,"Pour bien reussir les dessins ",Toast.LENGTH_LONG).show();
            }
        });





    }

    private int getColor() {
        switch (this.i) {
            case 0:
                this.i = this.i + 1;
                return Color.GREEN;
            case 1:
                this.i = this.i + 1;
                return Color.LTGRAY;
            case 2:
                this.i = this.i + 1;
                return MAGENTA;
            case 3:
                this.i = this.i + 1;
                return Color.YELLOW;
            case 4:
                this.i = this.i + 1;
                return Color.RED;
            case 5:
                this.i = 0;
                return Color.BLUE;
        }
        return Color.BLUE;

    }
}
