package projet.license;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import commun.Affichage;
import commun.Connexion;
import commun.Controleur;

public class TimeDetectorActivity extends Activity implements View.OnClickListener, Affichage {
    private static final long START_TIME_IN_MILLIS = 30000;
    private TextView mTextViewCountDown,mDemande,mDessin;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button mButtonValider;
    private PaintView myCanvas;
    private Controleur ctrl;
    private Boolean control = true;
    private CountDownTimer mCountDownTimer;
    private List<String> listformeDem = new ArrayList<>();
    private List<String> listformeRec = new ArrayList<>();




    final String[] formes = {"Point", "Segment","Rectangle", "Triangle", "Carre", "Circle","Pentagon","Hexagon","Heptagon","Octagon"};

    private TextView formeDemande;
    private String formeCourant;
    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_detector_activity);
        formeDemande = findViewById(R.id.formeDemande);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        myCanvas = findViewById(R.id.myCanvas);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        mButtonValider = findViewById(R.id.send);
        mDemande = findViewById(R.id.demande);
        mDessin = findViewById(R.id.dessin);

        ctrl = new Controleur(this);
        Connexion connexion = new Connexion("http://192.168.43.175:10101", ctrl);
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
                listformeDem = new ArrayList<>();
                listformeRec = new ArrayList<>();
                mDemande.setVisibility(View.INVISIBLE);
                mDessin.setVisibility(View.INVISIBLE);
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
                mDemande.setVisibility(View.VISIBLE);
                mDessin.setVisibility(View.VISIBLE);
                showToast();
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


    public void listTimeGame( String listDem, String listRec) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listformeDem.add(listDem);
                listformeRec.add(listRec);
            }
        });

    }

    @Override
    public void rtoGameScore(String coupJoueur, String coupServeur, String resultat) {

    }

    public void showToast(){
        String listDemande = "";
        listDemande += "Demandé \n";
        String listDessin = "";
        listDessin += "| Dessiné \n";
        for(int i = 0; i < listformeDem.size();i++){
            listDemande += listformeDem.get(i) + "\n";
            listDessin += "| "+ listformeRec.get(i) +"\n";}

        mDessin.setText(listDessin);
        mDemande.setText(listDemande);
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
