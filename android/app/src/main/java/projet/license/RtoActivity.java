package projet.license;


import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Random;



public class RtoActivity extends Activity implements  View.OnClickListener,Affichage{
    private static final long START_TIME_IN_MILLIS = 30000;


    private Button mButtonEffacer;
    private Button mButtonValider;
    private PaintView myCanvas;
    private Controleur ctrl;
    private Boolean control = true;

    final String[] formes = {"Triangle", "Carre", "Circle"};

    private TextView mTxtScoreRto;
    private TextView mTxtAdversaire;
    private TextView mTxtJoueur;

    private int scoreJr = 0;
    private int scoreSv = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rto);


        myCanvas = findViewById(R.id.paintView);
        mButtonEffacer = findViewById(R.id.button_effacer);
        mButtonValider = findViewById(R.id.valider);

        mTxtAdversaire = findViewById(R.id.adversaire);
        mTxtJoueur = findViewById(R.id.joueur);
        mTxtScoreRto = findViewById(R.id.scoreRto);

        mButtonEffacer.setOnClickListener(this);
        mButtonValider.setOnClickListener(this);


        ctrl = new Controleur(this);
        //fac
        //Connexion connexion = new Connexion("http://10.1.124.22:10101",ctrl);
        //spiti
        //Connexion connexion = new Connexion("http://192.168.0.18:10101", ctrl);
        //tilefono
        Connexion connexion = new Connexion("http://172.20.10.11:10101",ctrl);
        connexion.seConnecter();




        mButtonEffacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.clear();
            }
        });


        mButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.rtoValider(myCanvas.encodeBitMap());
                myCanvas.clear();

            }

        });

    }










    @Override
    public void majScor(boolean ok) {

    }



    @Override
    public void listTimeGame(List<String> listFormeDem, List<String> listFormeRec) {
    }

    @Override
    public void rtoGameScore(String coupJoueur, String coupServeur, String resultat) {

        if (resultat.equals("Joueur")){
            scoreJr ++;
            mTxtJoueur.setBackgroundColor(Color.GREEN);
            mTxtAdversaire.setBackgroundColor(Color.RED);
        }
        if (resultat.equals("Serveur")){
            scoreSv ++;
            mTxtJoueur.setBackgroundColor(Color.RED);
            mTxtAdversaire.setBackgroundColor(Color.GREEN);
        }
        else {
            mTxtJoueur.setBackgroundColor(Color.GRAY);
            mTxtAdversaire.setBackgroundColor(Color.GRAY);
        }

        mTxtJoueur.setText(coupJoueur);
        mTxtAdversaire.setText(coupServeur);
        mTxtScoreRto.setText(scoreJr + " | " + scoreSv);


    }


    @Override
    public void onClick(View v) { }

    @Override
    public void timeGameScor(Integer score, Integer tentative){}



}


