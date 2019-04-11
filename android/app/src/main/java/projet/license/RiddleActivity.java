package projet.license;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import gestion.Affichage;
import gestion.Connexion;
import gestion.Controleur;

public class RiddleActivity extends Activity implements View.OnClickListener, Affichage{
    private Button mButtonStart,mButtonValider ,mButtonClear;
    private TextView mTxtEnigme ,mTextRep;
    private PaintView myCanvas;
    final String[] enigmes = {"Je suis un quadrilatÃ¨re convexe Ã  quatre cÃ´tÃ©s de mÃªme longueur avec quatre angles droits , je suis .. ?",
            "Je suis est une figure plane, formÃ©e par trois points appelÃ©s sommets, par les trois segments qui les relient, , je suis .. ?",
            "Je suis  une courbe plane fermÃ©e constituÃ©e des points situÃ©s Ã  Ã©gale distance d'un point nommÃ© centre , je suis .. ?"};

    // enigmes[0] = CarrÃ© ;    enigmes[1] = Triangle ;   enigmes[2] = Cercle ;
    final String[] reponsePoss = {"Carre","Triangle","Circle"};
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
    public void majGraphic(String message, Bundle parameters) {
        if(message.equals("riddleRep")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (parameters.getBoolean("reponse")){
                        mTextRep.setText("Bravo ! trouvÃ©");
                        mTextRep.setTextColor(Color.GREEN);
                    }
                    else {
                        mTextRep.setText("Faux ! c'Ã©tait un "+ reponse);
                        mTextRep.setTextColor(Color.RED);

                    }
                }
            });
        }

    }
}
