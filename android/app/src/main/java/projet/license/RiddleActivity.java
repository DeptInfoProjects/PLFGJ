package projet.license;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gestion.Affichage;
import gestion.Connexion;
import gestion.Controleur;

public class RiddleActivity extends Activity implements View.OnClickListener, Affichage{
    private Button mButtonStart,mButtonValider ,mButtonClear , mPropo;
    private TextView mTxtEnigme ,mTextRep;
    private PaintView myCanvas;
    public ArrayList<String> enigmes = new ArrayList<>();
    public ArrayList<String> reponsePoss = new ArrayList<>();
    public int numEnigme = 4;

    // enigmes[0] = Carré ;    enigmes[1] = Triangle ;   enigmes[2] = Cercle ;
    String reponse ;
    Controleur ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riddle_activity);
        this.init();


        ctrl = new Controleur(this);
        Connexion connexion = new Connexion("http://172.20.10.11:10101", ctrl);
        connexion.seConnecter();

        ctrl.getListEnigme();



        mButtonStart.setOnClickListener(this);
        mButtonValider.setOnClickListener(this);
        mButtonClear.setOnClickListener(this);
        mPropo.setOnClickListener(this);
    }
    private void init() {
        mButtonStart = findViewById(R.id.start);
        mTxtEnigme = findViewById(R.id.enigme);
        mTextRep = findViewById(R.id.rep);
        mButtonValider = findViewById(R.id.valider);
        mButtonClear = findViewById(R.id.clear);
        myCanvas = findViewById(R.id.myCanvas2);
        mPropo = findViewById(R.id.propo);


    }



    @Override
    public void onClick(View v) {
        Button press = (Button) findViewById(v.getId());
        switch (v.getId()) {
            case R.id.start:
                ctrl.getNewEnigme();
                mButtonStart.setVisibility(View.INVISIBLE);
                mButtonValider.setVisibility(View.VISIBLE);
                mButtonClear.setVisibility(View.VISIBLE);

                break;

            case R.id.valider:
                ctrl.riddleValider(reponse + ',' + myCanvas.encodeBitMap() );
                ctrl.getNewEnigme();
                myCanvas.clear();
                break;

            case R.id.clear:
                myCanvas.clear();
                break;

            case R.id.propo:
                Intent intent = new Intent(this,PropositionActivity.class);
                if (intent != null)
                    startActivity(intent);
        }
    }




    @Override
    public void majGraphic(String message, Bundle parameters) {
        if(message.equals("enigmeRec")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        mTxtEnigme.setText(parameters.getString("enigme"));
                        reponse = parameters.getString("reponse");
                }
            });
        }
        if(message.equals("riddleRep")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (parameters.getBoolean("reponse")){
                        mTextRep.setText("Bravo ! trouve");
                        mTextRep.setTextColor(Color.GREEN);
                    }
                    else {
                        mTextRep.setText("Faux ! c'etait un "+ reponse);
                        mTextRep.setTextColor(Color.RED);

                    }
                }
            });
        }

    }
}
