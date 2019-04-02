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



public class TimeDetectorActivity extends Activity implements  View.OnClickListener,Affichage{
    private static final long START_TIME_IN_MILLIS = 30000;


    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button mButtonValider;
    private PaintView myCanvas;
    private Controleur ctrl;
    private Boolean control = true;
    final String[] formes = {"Point", "Segment","Rectangle", "Triangle", "Carre", "Circle","Pentagon","Hexagon","Heptagon","Octagon"};
    private CountDownTimer mCountDownTimer;
    private TextView formeDemande;
    private String formeCourant;
    private Button mButtonHistory;
    private String resultat;





    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timedetector);



        formeDemande = findViewById(R.id.formeDemande);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        myCanvas = findViewById(R.id.paintView);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        mButtonValider = findViewById(R.id.valider);
        mButtonHistory = findViewById(R.id.history);
        ctrl = new Controleur(this);
        //fac
        //Connexion connexion = new Connexion("http://10.1.124.22:10101",ctrl);
        //spiti
        //Connexion connexion = new Connexion("http://192.168.0.18:10101", ctrl);
        //tilefono
        Connexion connexion = new Connexion("http://192.168.43.179:10101",ctrl);
        connexion.seConnecter();


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                    if(control){
                        formeDemande.setText(randForm());
                        control = false;
                    }
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                myCanvas.clear();
                formeDemande.setText("");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TimeDetectorActivity.this);
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
        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                ctrl.endTimeGame();
                //ctrl.listTimeGame();
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    public void majScor(boolean ok) {

    }

    @Override
    public void timeGameScor(Integer score, Integer tentative) {
        mTextViewCountDown.setText(score +"/" +tentative);
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
    public void rtoGameScore(String coupJoueur, String coupServeur, String resultat) {

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


