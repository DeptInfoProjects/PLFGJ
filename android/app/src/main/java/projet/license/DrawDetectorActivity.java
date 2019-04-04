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


import static android.graphics.Color.MAGENTA;


public  class DrawDetectorActivity extends Activity implements View.OnClickListener, Affichage {

    private Button effacer, couleur, start, tutoriel,valider;
    private TextView  fdem, score;
    Controleur ctrl;
    public int i = 0;
    private String formeDemande = "Press start";
    public PaintView myCanvas;
    final String[] formes = {"Point", "Segment", "Triangle", "Carre", "Rond"};
    private RelativeLayout mRelativeLayout;
    private Context mContext;
    private int textCol;
    private boolean debut = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_detector_activity);
        this.init();


        ctrl = new Controleur(this);
        //tilefono
        //Connexion connexion = new Connexion("http://192.168.0.101:10101",ctrl);
        Connexion connexion = new Connexion("http://192.168.43.175:10101", ctrl);
        connexion.seConnecter();


    }


    public static Bitmap viewToBitmap(View view,int width,int height){
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        return bitmap;
    }


    private void init() {
        effacer = findViewById(R.id.CLEAR);
        couleur = findViewById(R.id.pencolor);
        start = findViewById(R.id.start);
        valider = findViewById(R.id.valider);

        fdem = findViewById(R.id.forme);
        tutoriel = findViewById(R.id.help);
        score = findViewById(R.id.ticks);





        mContext = getApplicationContext();
        myCanvas = findViewById(R.id.myCanvas);

        effacer.setOnClickListener(this);
        couleur.setOnClickListener(this);
        start.setOnClickListener(this);
        valider.setOnClickListener(this);


    }

    public void back_home(View v){
        finish();    // retourner page home
    }


    private int getColor() {
        switch (this.i) {
            case 0:
                this.i = this.i + 1;
                return Color.GREEN;
            case 1:
                this.i = this.i + 1;
                return Color.LTGRAY;
            case 2:
                this.i = this.i + 1;
                return MAGENTA;
            case 3:
                this.i = this.i + 1;
                return Color.YELLOW;
            case 4:
                this.i = this.i + 1;
                return Color.RED;
            case 5:
                this.i = 0;
                return Color.BLUE;
        }
        return Color.BLUE;

    }


    private void afficherTick() {
        score.setText(myCanvas.countTicks+"");
    }

    public void newForme(){
        Random rand = new Random();
        int rando = rand.nextInt(5);
        String formeRandom = formes[rando];
        fdem.setText(formeRandom);
        formeDemande = formeRandom;
    }
    public void onClick(View v){
        Button press = (Button)findViewById(v.getId());
        switch (v.getId()){
            case R.id.CLEAR:
                myCanvas.clear();
                myCanvas.countTicks = 0;
                afficherTick();
                break;
            case R.id.start:
                newForme();
                myCanvas.countTicks = 0;
                afficherTick();
                start.setVisibility(View.INVISIBLE);
                valider.setVisibility(View.VISIBLE);
                break;
            case R.id.valider:
                ctrl.msgValider(formeDemande +"," + myCanvas.getTicks());
                afficherTick();
                myCanvas.countTicks = 0;
                newForme();
                myCanvas.clear();
                break;

                case R.id.pencolor:
                myCanvas.setBackgroundColor(Color.BLACK);
                myCanvas.mPaint.setColor(getColor());
                break;

        }
    }



    public void showToast(View v){
        Toast.makeText(this,"Dessiner les points de la forme demande\nps: Rond -> 5",Toast.LENGTH_LONG).show();
    }

    public void timeGameScor(Integer score,Integer tentative){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void listTimeGame(String listDem, String listRec) {

    }


    public void listTimeGame(List<Object> listFormeDem, List<Object> listFormeRec) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void rtoGameScore(String coupJoueur, String coupServeur, String resultat) {

    }

    public void majScor(final boolean b){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b){
                    String txt = ("Juste");
                    score.setText(txt);
                    score.setTextColor(Color.GREEN);

                }
                else {
                    String txt = ("Faux");
                    score.setText(txt);
                    score.setTextColor(Color.RED);



                }
            }
        });

    }
    public String FormeCourant(){
        return this.formeDemande;
    }
}


