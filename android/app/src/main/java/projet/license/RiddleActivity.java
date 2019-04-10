package projet.license;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import commun.Affichage;
import commun.Connexion;
import commun.Controleur;

public class RiddleActivity extends Activity implements View.OnClickListener, Affichage{
    private Button mButtonStart,mButtonValider ,mButtonClear;
    private TextView mTxtEnigme ,mTextRep;
    private PaintView myCanvas;
    final String[] enigmes = {"Je suis un quadrilatère convexe à quatre côtés de même longueur avec quatre angles droits , je suis .. ?",
            "Je suis est une figure plane, formée par trois points appelés sommets, par les trois segments qui les relient, , je suis .. ?",
             "Je suis  une courbe plane fermée constituée des points situés à égale distance d'un point nommé centre , je suis .. ?"};

    // enigmes[0] = Carré ;    enigmes[1] = Triangle ;   enigmes[2] = Cercle ;
    final String[] reponsePoss = {"Carre","Triangle","Circle"};
    String reponse ;
    Controleur ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riddle_activity);
        this.init();


        ctrl = new Controleur(this);
        Connexion connexion = new Connexion("http://192.168.0.100:10101", ctrl);
        connexion.seConnecter();


        mButtonStart.setOnClickListener(this);
        mButtonValider.setOnClickListener(this);
        mButtonClear.setOnClickListener(this);


    }
    private void init() {
        mButtonStart = findViewById(R.id.start);
        mTxtEnigme = findViewById(R.id.enigme);
        mTextRep = findViewById(R.id.rep);
        mButtonValider = findViewById(R.id.valider);
        mButtonClear = findViewById(R.id.clear);
        myCanvas = findViewById(R.id.myCanvas2);


    }

    public void newRiddle(){
        Random rand = new Random();
        int rando = rand.nextInt(3);
        String riddleRandom = enigmes[rando];
        mTxtEnigme.setText(riddleRandom);
        reponse = reponsePoss[rando];


    }

    @Override
    public void onClick(View v) {
        Button press = (Button) findViewById(v.getId());
        switch (v.getId()) {
            case R.id.start:
                newRiddle();
                mButtonStart.setVisibility(View.INVISIBLE);
                mButtonValider.setVisibility(View.VISIBLE);
                mButtonClear.setVisibility(View.VISIBLE);

                break;

            case R.id.valider:
                ctrl.riddleValider(reponse + ',' + myCanvas.encodeBitMap() );
                newRiddle();
                myCanvas.clear();
                break;

            case R.id.clear:
                myCanvas.clear();
                break;




        }
    }

    @Override
    public void majScor(boolean ok) {

    }

    @Override
    public void timeGameScor(Integer score, Integer tentative) {

    }

    @Override
    public void listTimeGame(String listDem, String listRec) {

    }


    @Override
    public void rtoGameScore(String coupJoueur, String coupServeur, String resultat) {

    }

    @Override
    public void riddleGame(boolean rep) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (rep){
                    mTextRep.setText("Bravo ! trouvé");
                    mTextRep.setTextColor(Color.GREEN);
                }
                else {
                    mTextRep.setText("Faux ! c'était un "+ reponse);
                    mTextRep.setTextColor(Color.RED);

                }
            }
        });
    }
}
