package projet.license;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import gestion.Affichage;
import gestion.Connexion;
import gestion.Controleur;

public class StatisticsActivity extends Activity implements View.OnClickListener , Affichage {

    private TextView myProgRto,myProgRiddle,myProgDraw,myProgTime;
    private TextView myBestRto,myBestRiddle,myBestDraw,myBestTime;
    Controleur ctrl;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);
        this.init();

        ctrl = new Controleur(this);
        Connexion connexion = new Connexion("http://172.20.10.11:10101", ctrl);
        connexion.seConnecter();

        ctrl.getStat();
    }

    private void init() {
        myBestRto = findViewById(R.id.bestRto);
        myBestDraw = findViewById(R.id.bestDraw);
        myBestRiddle = findViewById(R.id.bestRiddle);
        myBestTime = findViewById(R.id.bestTime);

        myProgDraw = findViewById(R.id.progDraw);
        myProgRiddle = findViewById(R.id.progRiddle);
        myProgTime = findViewById(R.id.progTime);
        myProgRto = findViewById(R.id.progRto);



    }

    @Override
    public void majGraphic(String message, Bundle parameters) {
        if(message.equals("statJoueur")) runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int x = parameters.getInt("tentDraw");
                int y = parameters.getInt("scorDraw");
                String perc = Integer.toString(calculatePercentage(y,x));
                myProgDraw.setText(perc+"%");
                int x2 = parameters.getInt("tentRiddle");
                int y2 = parameters.getInt("scorRiddle");
                String perc2 = Integer.toString(calculatePercentage(y2,x2));
                myProgRiddle.setText(perc2+"%");
                int x3 = parameters.getInt("tentTime");
                int y3 = parameters.getInt("scorTime");
                String perc3 = Integer.toString(calculatePercentage(y3,x3));
                myProgTime.setText(perc3+"%");
                int x4 = parameters.getInt("tentRto");
                int y4 = parameters.getInt("scorRto");
                String perc4 = Integer.toString(calculatePercentage(y4,x4));
                myProgRto.setText(perc4+"%");
            }
        });
    }

    @Override
    public void onClick(View v) {

    }


    public int calculatePercentage(int obtained, int total) {
        if(total == 0){
            return 0;
        }
        return obtained * 100 / total;
    }
}
