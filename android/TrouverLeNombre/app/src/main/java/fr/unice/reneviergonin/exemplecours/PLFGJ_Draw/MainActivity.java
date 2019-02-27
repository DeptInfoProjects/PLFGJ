package fr.unice.reneviergonin.exemplecours.PLFGJ_Draw;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity implements Vue {

    Button effacer;
    Button couleur;
    Button btnJouer ;
    Button start;
    TextView msg;
    TextView fdem;
    TextView randform ;
    MyCanvas myCanvas;
    TextView titre;
    Controleur ctrl;
    public int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myCanvas = new MyCanvas(this,null);
        //setContentView(myCanvas);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // récupération des widget
        effacer = findViewById(R.id.effacer);
        couleur = findViewById(R.id.color);
        btnJouer = findViewById(R.id.valider);
        start = findViewById(R.id.start);
        msg = findViewById(R.id.textView6);
        fdem = findViewById(R.id.textView4);
        randform = findViewById(R.id.textView3);
        final String[] formes = {"Point","Segment","Triangle","Carré","Rond"} ;


        effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.effacerCanvas();
            }
        });

        couleur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.setColor();
                myCanvas.postInvalidate();}});


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int rando = rand.nextInt(5);
                randform.setText(formes[rando]);
            }
        });
        // création de la logique de l'application
        ctrl = new Controleur(this);
        ajouteÉcouteur();

        // connexion : grandement récupérer de client (javastd)
        // juste un découpage par rapport à javastd : on "refactore"
        // adresse pour parler depuis l'émulateur à la machien hote : 10.0.2.2
        Connexion connexion = new Connexion("http://10.1.124.22:10101", ctrl);

        // ici pas d'IA mais un joueur humain


        // on pourrait le faire à partir d'un bouton...
        connexion.seConnecter();

    }



    public void afficheMessage(final String texte) {

        // appelé depuis le thread de socketIO
        msg.post(new Runnable() {
            @Override
            public void run() {
                msg.setText(texte);
            }
        });

    }


    // adapteur entre le controleur et la vue, pour que le controleur ne dépendent pas d'android
    private void ajouteÉcouteur() {
        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ctrl.apresDessin();
               // ctrl.jeRejoue();
            }
        });
    }

}
