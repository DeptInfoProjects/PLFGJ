package projet.license;

import android.app.Activity;
import android.app.AlertDialog;
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
import java.util.List;
import java.util.Random;
import commun.ListDemande;
import static android.graphics.Color.MAGENTA;


public class DrawDetectorActivity extends Activity implements View.OnClickListener, Affichage{

    private Button effacer, couleur, btnJouer, start, tutoriel;
    private TextView titre, fdem, randform, click, score,rightBord,leftBord;
    Controleur ctrl;
    public int i = 0;
    private String formeDemande = "Press start";
    private ListDemande listDemande = new ListDemande();
    public PaintView myCanvas;
    final String[] formes = {"Point", "Segment", "Triangle", "Carre", "Rond"};
    private RelativeLayout mRelativeLayout;
    private Context mContext;
    private int textCol;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawdetector);
        this.init();


        ctrl = new Controleur(this);
        //tilefono
        //Connexion connexion = new Connexion("http://192.168.0.101:10101",ctrl);
        Connexion connexion = new Connexion("http://192.168.0.101:10101", ctrl);
        connexion.seConnecter();


    }


    public static Bitmap viewToBitmap(View view,int width,int height){
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        return bitmap;
    }


    private void init() {
        effacer = findViewById(R.id.effacer);
        couleur = findViewById(R.id.color);
        btnJouer = findViewById(R.id.valider);
        start = findViewById(R.id.start1);
        titre = findViewById(R.id.textView6);
        fdem = findViewById(R.id.textView4);
        randform = findViewById(R.id.textView3);
        tutoriel = findViewById(R.id.tutoriel);
        score = findViewById(R.id.score);
        click =  findViewById(R.id.click);
        rightBord = findViewById(R.id.rightBord);
        leftBord = findViewById(R.id.leftBord);




        mContext = getApplicationContext();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.container);
        myCanvas = (PaintView) findViewById(R.id.myCanvas);

        effacer.setOnClickListener(this);
        couleur.setOnClickListener(this);
        start.setOnClickListener(this);
        btnJouer.setOnClickListener(this);
        tutoriel.setOnClickListener(this);

    }

    public void back_home(View v){
        finish();    // retourner page home
    }

    private void changeTheme(int choix) {
        if(choix == Color.MAGENTA || choix ==Color.YELLOW || choix==Color.LTGRAY)
            { textCol = Color.WHITE;}
        else
            { textCol = Color.DKGRAY;}
        if(choix == Color.BLUE)
        { textCol = Color.WHITE;}
        couleur.setTextColor(textCol);
        start.setTextColor(textCol);
        //btnJouer.setBackgroundColor(choix);
        btnJouer.setTextColor(textCol);
        //effacer.setBackgroundColor(choix);
        effacer.setTextColor(textCol);
        titre.setBackgroundColor(choix);
        titre.setTextColor(textCol);
        fdem.setBackgroundColor(choix);
        fdem.setTextColor(textCol);
        randform.setBackgroundColor(choix);
        randform.setTextColor(textCol);
        //tutoriel.setBackgroundColor(choix);
        tutoriel.setTextColor(textCol);
        score.setBackgroundColor(choix);
        score.setTextColor(textCol);
        click.setBackgroundColor(choix);
        click.setTextColor(textCol);
        rightBord.setBackgroundColor(choix);
        rightBord.setTextColor(textCol);
        leftBord.setBackgroundColor(choix);
        leftBord.setTextColor(textCol);
        mRelativeLayout.setBackgroundColor(choix);





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

    private int getColorEffacer() {
        switch (this.i - 1) {
            case 0:
                changeTheme(Color.LTGRAY);
                return Color.LTGRAY;
            case 1:
                changeTheme(Color.MAGENTA);
                return Color.MAGENTA;
            case 2:
                changeTheme(Color.YELLOW);
                return Color.YELLOW;
            case 3:
                changeTheme(Color.BLUE);
                return Color.BLUE;
            case 4:
                changeTheme(Color.BLACK);
                return Color.BLACK;
        }
        return Color.WHITE;
    }

    private void afficherTick() {
        click.setText(myCanvas.countTicks+"");
    }

    public void newForme(){
        Random rand = new Random();
        int rando = rand.nextInt(5);
        String formeRandom = formes[rando];
        randform.setText(formeRandom);
        formeDemande = formeRandom;
        listDemande.setForme(formes[rando]);
        listDemande.setTicks(0);
    }
    public void onClick(View v){
        Button press = (Button)findViewById(v.getId());
        switch (v.getId()){
            case R.id.effacer:
                myCanvas.clear();
                ctrl.msgReset();
                myCanvas.countTicks = 0;
                listDemande.setTicks(0);
                afficherTick();
                Log.d("Button Pressed : ",press.getText() + "");
                break;
            case R.id.start1:
                leftBord.setBackgroundColor(mRelativeLayout.getSolidColor());
                rightBord.setBackgroundColor(mRelativeLayout.getSolidColor());
                newForme();
                ctrl.msgStart();
                Log.d("Button Pressed : ",press.getText() + "");
                myCanvas.countTicks = 0;
                afficherTick();
                break;
            case R.id.color:
                myCanvas.setBackgroundColor(Color.BLACK);
                myCanvas.mPaint.setColor(getColor());
                ctrl.msgColor();
                Log.d("Button Pressed : ", press.getText() + "");
                break;
            case R.id.valider:
                ctrl.msgValider(formeDemande +"," + myCanvas.getTicks());
                Log.d("Button Pressed : ",press.getText() + "");
                afficherTick();
                myCanvas.countTicks = 0;
                break;
            case R.id.tutoriel:
                ctrl.msgTutoriel();


        }
    }




    public void timeGameScor(Integer score,Integer tentative){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    public void listTimeGame(List<String> listFormeDem, List<String> listFormeRec) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void majScor(final boolean b){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b){
                    String txt = ("Juste");
                    score.setText(txt);
                    leftBord.setBackgroundColor(Color.GREEN);
                }
                else {
                    String txt = ("Faux");
                    score.setText(txt);
                    rightBord.setBackgroundColor(Color.RED);


                }
            }
        });

    }
 }


