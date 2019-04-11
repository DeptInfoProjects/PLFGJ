package projet.license;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import gestion.Affichage;
import gestion.Connexion;
import gestion.Controleur;


public class RtoDetectorActivity extends Activity implements  View.OnClickListener,Affichage{

    private Button mButtonEffacer;
    private Button mButtonValider;
    private Button mButtonHelp;
    private PaintView myCanvas;
    private Controleur ctrl;


    private TextView mTxtAdversaire;
    private TextView mTxtJoueur;

    private TextView mTxtcoupAdversaire;
    private TextView mTxtcoupJoueur;

    private TextView mTxtscoreAdversaire;
    private TextView mTxtscoreJoueur;

    private int scoreJr = 0;
    private int scoreSv = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rto_detector_activity);


        myCanvas = findViewById(R.id.paintView);
        mButtonEffacer = findViewById(R.id.button_effacer);
        mButtonValider = findViewById(R.id.valider);
        mButtonHelp = findViewById(R.id.helpRto);

        mTxtAdversaire = findViewById(R.id.adversaire);
        mTxtJoueur = findViewById(R.id.joueur);

        mTxtscoreAdversaire = findViewById(R.id.scoreAdversaire);
        mTxtscoreJoueur = findViewById(R.id.scoreJoueur);

        mTxtcoupAdversaire = findViewById(R.id.coupAdversaire);
        mTxtcoupJoueur = findViewById(R.id.coupJoueur);

        mButtonEffacer.setOnClickListener(this);
        mButtonValider.setOnClickListener(this);


        ctrl = new Controleur((Affichage) this);
        Connexion connexion = new Connexion("http://172.20.10.11:10101", ctrl);
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

        mButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHelp();
            }

        });

    }


    public void showHelp(){
        Toast.makeText(RtoDetectorActivity.this,"RTO est un pierre-papier-ciseau :\nTriangle > Carre\nCarre > Circle\nCircle > Triangle", Toast.LENGTH_LONG).show();
    }



    @Override
    public void majGraphic(String message, Bundle parameters) {

        if (message.equals("resultatRto")){
            String coupJoueur = parameters.getString("coupJoueur");
            String coupServeur = parameters.getString("coupServeur");
            String resultat = parameters.getString("resultat");
            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {

                    String coupSv = coupServeur;
                    mTxtcoupJoueur.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);

                    if (resultat.equals("Joueur")){
                        scoreJr ++;
                        mTxtcoupJoueur.setBackgroundColor(Color.GREEN);
                        mTxtcoupAdversaire.setBackgroundColor(Color.RED);
                    }
                    else if (resultat.equals("Serveur")){
                        scoreSv ++;
                        mTxtcoupJoueur.setBackgroundColor(Color.RED);
                        mTxtcoupAdversaire.setBackgroundColor(Color.GREEN);
                    }
                    else if (resultat.equals("Egalite")){
                        mTxtcoupJoueur.setBackgroundColor(Color.GRAY);
                        mTxtcoupAdversaire.setBackgroundColor(Color.GRAY);
                    }

                    else {
                        mTxtcoupJoueur.setBackgroundColor(Color.BLUE);
                        mTxtcoupJoueur.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                        coupSv = "";
                        mTxtcoupAdversaire.setBackgroundColor(Color.TRANSPARENT);
                    }

                    mTxtcoupJoueur.setText(coupJoueur);
                    mTxtscoreJoueur.setText("" + scoreJr);

                    mTxtcoupAdversaire.setText(coupSv);
                    mTxtscoreAdversaire.setText("" + scoreSv);

                }


            });
        }

    }



    @Override
    public void onClick(View v) { }




}


