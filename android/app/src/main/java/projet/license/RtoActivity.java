package projet.license;


import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Random;



public abstract class RtoActivity extends Activity implements  View.OnClickListener,Affichage{
    private static final long START_TIME_IN_MILLIS = 30000;


    private Button mButtonStartPause;
    private Button mButtonValider;
    private PaintView myCanvas;
    private Controleur ctrl;
    private Boolean control = true;
    final String[] formes = {"Triangle", "Carre", "Circle"};

    private TextView formeDemande;
    private String formeCourant;
    private Button mButtonHistory;
    private String resultat;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timedetector);


        myCanvas = findViewById(R.id.paintView);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonValider = findViewById(R.id.valider);
        mButtonHistory = findViewById(R.id.history);
        ctrl = new Controleur(this);
        //fac
        //Connexion connexion = new Connexion("http://10.1.124.22:10101",ctrl);
        //spiti
        //Connexion connexion = new Connexion("http://192.168.0.18:10101", ctrl);
        //tilefono
        Connexion connexion = new Connexion("http://192.168.0.29:10101",ctrl);
        connexion.seConnecter();



        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.timeDetectorValider(formeCourant + "," +myCanvas.encodeBitMap());
                formeDemande.setText(randForm());
                myCanvas.clear();


            }

        });
        mButtonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RtoActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Resultat");


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //textRes.setText(resultat);
                        //textRes.setVisibility(View.VISIBLE);
                    }
                });
                builder.show();
            }
        });

    }



    @Override
    public void majScor(boolean ok) {

    }



    @Override
    public void listTimeGame(List<String> listFormeDem, List<String> listFormeRec) {
        String res = "Demandé    |  Dessiné    '\n'";
        for (int i = 0; i < listFormeDem.size(); i++) {
            res += listFormeDem.get(i) + "'\t' |  " + listFormeRec.get(i) + "'\n";
        }
        this.resultat = res;



    }
    @Override
    public void onClick(View v) { }

    private String randForm(){
        Random rand = new Random();
        int x = rand.nextInt(9);
        formeCourant = formes[x];
        return formeCourant;
    }



}


